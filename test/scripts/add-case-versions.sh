#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

all_projects=`find $DIR/../.. -name pom.xml`
project_count=`echo $all_projects | tr ' ' '\n' | grep -c ""`
echo "Total projects: $project_count"

case_config_template1=$DIR/case-versions.conf.1
case_config_template2=$DIR/case-versions.conf.2
case_config_template3=$DIR/case-versions.conf.3

while read project
do
#    echo "project: $project"
    project_dir=`dirname $project`
    if [ ! -f $project_dir/case-configuration.yml ]; then
      continue
    fi

    grep "<spring.version>" $project_dir/pom.xml > /dev/null
    result=$?
    if [ $result -eq 0 ]; then
      case_config_template=$case_config_template1
    fi

    grep "<spring-boot.version>1" $project_dir/pom.xml > /dev/null
    result=$?
    if [ $result -eq 0 ]; then
      case_config_template=$case_config_template2
    fi

    grep "<spring-boot.version>2" $project_dir/pom.xml > /dev/null
    result=$?
    if [ $result -eq 0 ]; then
      case_config_template=$case_config_template3
    fi

    if [ -z $case_config_template ]; then
      echo "not match: $project_dir"
      continue
    fi

    if [ -f $project_dir/case-versions.conf ];then
      continue
    fi

    echo "copy $case_config_template to $project_dir"
    cp -n $case_config_template $project_dir/case-versions.conf
done <<< "$all_projects"