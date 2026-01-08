#!/bin/bash
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# This script filters modules based on build.dubbo.version configuration
# in case-versions.conf files.
#
# Usage: ./filter-build-modules.sh <dubbo_version>
# Output: Maven -pl exclude list (e.g., "-pl !module1,!module2")
#
# Configuration in case-versions.conf:
#   build.dubbo.version=3.3
#   - Means this module requires Dubbo >= 3.3 to compile

set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
BASE_DIR="$( cd "$SCRIPT_DIR/../.." && pwd )"

DUBBO_VERSION=$1

if [ -z "$DUBBO_VERSION" ]; then
    echo "Usage: $0 <dubbo_version>" >&2
    echo "Example: $0 3.2.6" >&2
    exit 1
fi

# Extract major.minor from version (e.g., "3.2.6-SNAPSHOT" -> "3.2")
extract_major_minor() {
    local version=$1
    echo "$version" | sed -E 's/^([0-9]+\.[0-9]+).*/\1/'
}

# Compare two major.minor versions
# Returns: 0 if v1 >= v2, 1 otherwise
version_ge() {
    local v1=$1
    local v2=$2
    
    local v1_major=$(echo "$v1" | cut -d. -f1)
    local v1_minor=$(echo "$v1" | cut -d. -f2)
    local v2_major=$(echo "$v2" | cut -d. -f1)
    local v2_minor=$(echo "$v2" | cut -d. -f2)
    
    if [ "$v1_major" -gt "$v2_major" ]; then
        return 0
    elif [ "$v1_major" -lt "$v2_major" ]; then
        return 1
    else
        # major versions are equal, compare minor
        if [ "$v1_minor" -ge "$v2_minor" ]; then
            return 0
        else
            return 1
        fi
    fi
}

# Get the current dubbo version's major.minor
CURRENT_VERSION=$(extract_major_minor "$DUBBO_VERSION")

# Find all case-versions.conf files and check build.dubbo.version
EXCLUDED_MODULES=""

while IFS= read -r conf_file; do
    # Read build.dubbo.version from the config file
    BUILD_VERSION=$(grep -E "^build\.dubbo\.version\s*=" "$conf_file" 2>/dev/null | sed 's/.*=\s*//' | tr -d '[:space:]' || echo "")
    
    if [ -n "$BUILD_VERSION" ]; then
        # Check if current version >= required version
        if ! version_ge "$CURRENT_VERSION" "$BUILD_VERSION"; then
            # Get the module path relative to BASE_DIR
            MODULE_DIR=$(dirname "$conf_file")
            RELATIVE_PATH="${MODULE_DIR#$BASE_DIR/}"
            
            if [ -n "$EXCLUDED_MODULES" ]; then
                EXCLUDED_MODULES="$EXCLUDED_MODULES,$RELATIVE_PATH"
            else
                EXCLUDED_MODULES="$RELATIVE_PATH"
            fi
            
            echo "Excluding module: $RELATIVE_PATH (requires Dubbo >= $BUILD_VERSION, current: $CURRENT_VERSION)" >&2
        fi
    fi
done < <(find "$BASE_DIR" -name "case-versions.conf" -type f)

# Output the Maven exclude parameter
if [ -n "$EXCLUDED_MODULES" ]; then
    # Format: -pl !module1,!module2
    echo "-pl !${EXCLUDED_MODULES//,/,!}"
else
    echo ""
fi
