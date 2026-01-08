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

# This script filters modules based on dubbo.version configuration
# in case-versions.conf files. The logic is consistent with Java VersionMatcher.
#
# Usage: ./filter-build-modules.sh <dubbo_version>
# Output: Maven -pl exclude list (e.g., "-pl !module1,!module2")
#
# Supported dubbo.version formats (same as Java VersionMatcher):
#   dubbo.version=2.7*, 3.*           -> wildcard match
#   dubbo.version=[ >= 3.3.6 ]        -> range match
#   dubbo.version=3.3.*               -> wildcard match
#   dubbo.version=!2.7.8*             -> exclusion
#   dubbo.version=>=2.7.7 <3.0        -> combined range

set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
BASE_DIR="$( cd "$SCRIPT_DIR/../.." && pwd )"

DUBBO_VERSION=$1

if [ -z "$DUBBO_VERSION" ]; then
    echo "Usage: $0 <dubbo_version>" >&2
    echo "Example: $0 3.2.6" >&2
    exit 1
fi

# Trim version suffix like '-SNAPSHOT'
trim_version() {
    local version=$1
    echo "$version" | sed 's/-.*$//'
}

# Convert version string to comparable array (e.g., "3.2.6" -> "3 2 6 0")
# Returns space-separated integers for comparison
version_to_ints() {
    local version=$1
    version=$(trim_version "$version")
    
    local IFS='.'
    read -ra parts <<< "$version"
    
    local result=""
    for i in 0 1 2 3; do
        if [ $i -lt ${#parts[@]} ]; then
            # Extract numeric part only
            local num=$(echo "${parts[$i]}" | grep -oE '^[0-9]+' || echo "0")
            result="$result $num"
        else
            result="$result 0"
        fi
    done
    echo $result
}

# Compare two versions: returns 0 if v1 > v2, 1 if v1 < v2, 2 if equal
compare_versions() {
    local v1_ints=($1)
    local v2_ints=($2)
    
    for i in 0 1 2 3; do
        if [ "${v1_ints[$i]}" -gt "${v2_ints[$i]}" ]; then
            return 0  # v1 > v2
        elif [ "${v1_ints[$i]}" -lt "${v2_ints[$i]}" ]; then
            return 1  # v1 < v2
        fi
    done
    return 2  # equal
}

# Check if version matches a wildcard pattern (e.g., "3.*", "2.7*")
match_wildcard() {
    local version=$1
    local pattern=$2
    
    # Convert wildcard pattern to regex
    # "3.*" -> "^3\..*$", "2.7*" -> "^2\.7.*$"
    local regex=$(echo "$pattern" | sed 's/\./\\./g' | sed 's/\*/.*/g')
    regex="^${regex}$"
    
    if [[ "$version" =~ $regex ]]; then
        return 0  # match
    else
        return 1  # no match
    fi
}

# Check if version satisfies a range condition (e.g., ">=3.3.6", "<3.0")
match_range() {
    local version=$1
    local operator=$2
    local target=$3
    
    local v_ints=$(version_to_ints "$version")
    local t_ints=$(version_to_ints "$target")
    
    compare_versions "$v_ints" "$t_ints"
    local cmp=$?
    
    case "$operator" in
        ">=")
            [ $cmp -eq 0 ] || [ $cmp -eq 2 ]
            return $?
            ;;
        ">")
            [ $cmp -eq 0 ]
            return $?
            ;;
        "<=")
            [ $cmp -eq 1 ] || [ $cmp -eq 2 ]
            return $?
            ;;
        "<")
            [ $cmp -eq 1 ]
            return $?
            ;;
    esac
    return 1
}

# Parse and check a single rule against a version
# Returns: 0=included, 1=excluded, 2=no match
check_rule() {
    local version=$1
    local rule=$2
    local excluded=0
    
    # Trim leading/trailing whitespace first
    rule=$(echo "$rule" | xargs)
    
    # Check for exclusion prefix
    if [[ "$rule" == !* ]]; then
        excluded=1
        rule="${rule:1}"
        rule=$(echo "$rule" | xargs)  # trim again after removing !
    fi
    
    # Check for range operators (>=, >, <=, <)
    if [[ "$rule" =~ ^(>=|>|<=|<)[[:space:]]*([0-9]+\.[0-9]+(\.[0-9]+)?) ]]; then
        local operator="${BASH_REMATCH[1]}"
        local target="${BASH_REMATCH[2]}"
        
        if match_range "$version" "$operator" "$target"; then
            [ $excluded -eq 1 ] && return 1 || return 0
        fi
        return 2
    fi
    
    # Check for wildcard
    if [[ "$rule" == *"*"* ]]; then
        if match_wildcard "$version" "$rule"; then
            [ $excluded -eq 1 ] && return 1 || return 0
        fi
        return 2
    fi
    
    # Plain version match
    local trimmed_version=$(trim_version "$version")
    if [ "$trimmed_version" == "$rule" ]; then
        [ $excluded -eq 1 ] && return 1 || return 0
    fi
    
    return 2
}

# Check if a version matches a set of rules (from dubbo.version line)
# Returns: 0 if version is included, 1 if excluded or no match
check_version_rules() {
    local version=$1
    local rules_str=$2
    
    # Trim leading/trailing whitespace
    rules_str=$(echo "$rules_str" | xargs)
    
    # Remove brackets [ ]
    rules_str=$(echo "$rules_str" | sed 's/^\[//' | sed 's/\]$//' | xargs)
    
    # Handle combined range rules like ">=2.7.7 <3.0" (space-separated without comma)
    # First, check if it's a combined range (contains space but no comma between operators)
    if [[ "$rules_str" =~ ^[[:space:]]*(\>=|\>|\<=|\<)[^,]+[[:space:]]+(\>=|\>|\<=|\<) ]]; then
        # Combined range rule - all conditions must match
        local all_match=true
        while [[ "$rules_str" =~ (\>=|\>|\<=|\<)([^[:space:]\>=\<]+) ]]; do
            local operator="${BASH_REMATCH[1]}"
            local target="${BASH_REMATCH[2]}"
            # Remove quotes
            target=$(echo "$target" | tr -d "\"'")
            
            if ! match_range "$version" "$operator" "$target"; then
                all_match=false
                break
            fi
            # Remove matched part
            rules_str="${rules_str#*${BASH_REMATCH[0]}}"
        done
        
        if $all_match; then
            return 0
        fi
        return 1
    fi
    
    # Split by comma for multiple rules
    local included=false
    
    IFS=',' read -ra rules <<< "$rules_str"
    for rule in "${rules[@]}"; do
        # Remove quotes
        rule=$(echo "$rule" | tr -d "\"'" | xargs)
        
        # Skip empty rules
        [ -z "$rule" ] && continue
        
        # Check for combined range within a single rule (e.g., ">=2.7.7 <3.0")
        if [[ "$rule" =~ ^[[:space:]]*(\>=|\>|\<=|\<).+[[:space:]]+(\>=|\>|\<=|\<) ]]; then
            local all_match=true
            local temp_rule="$rule"
            while [[ "$temp_rule" =~ (\>=|\>|\<=|\<)([^[:space:]\>=\<]+) ]]; do
                local operator="${BASH_REMATCH[1]}"
                local target="${BASH_REMATCH[2]}"
                
                if ! match_range "$version" "$operator" "$target"; then
                    all_match=false
                    break
                fi
                temp_rule="${temp_rule#*${BASH_REMATCH[0]}}"
            done
            
            if $all_match; then
                included=true
            fi
            continue
        fi
        
        check_rule "$version" "$rule"
        local result=$?
        
        if [ $result -eq 1 ]; then
            # Excluded - immediate return
            return 1
        elif [ $result -eq 0 ]; then
            included=true
        fi
    done
    
    if $included; then
        return 0
    fi
    return 1
}

# Get the trimmed version for matching
CURRENT_VERSION=$(trim_version "$DUBBO_VERSION")

# Find all case-versions.conf files and check dubbo.version
EXCLUDED_MODULES=""

while IFS= read -r conf_file; do
    # Read dubbo.version from the config file
    RAW_VERSION=$(grep -E "^dubbo\.version\s*=" "$conf_file" 2>/dev/null | sed 's/^dubbo\.version\s*=\s*//' || echo "")
    
    if [ -n "$RAW_VERSION" ]; then
        # Check if current version matches the rules
        if ! check_version_rules "$CURRENT_VERSION" "$RAW_VERSION"; then
            # Version does not match - exclude this module
            MODULE_DIR=$(dirname "$conf_file")
            RELATIVE_PATH="${MODULE_DIR#$BASE_DIR/}"
            
            if [ -n "$EXCLUDED_MODULES" ]; then
                EXCLUDED_MODULES="$EXCLUDED_MODULES,$RELATIVE_PATH"
            else
                EXCLUDED_MODULES="$RELATIVE_PATH"
            fi
            
            echo "Excluding module: $RELATIVE_PATH (dubbo.version=$RAW_VERSION, current: $CURRENT_VERSION)" >&2
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
