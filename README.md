# Dubbo Samples

Samples for Apache Dubbo

[![Build Status](https://travis-ci.org/apache/dubbo-samples.svg?branch=master)](https://travis-ci.org/apache/dubbo-samples) 
[![codecov](https://codecov.io/gh/apache/dubbo-samples/branch/master/graph/badge.svg)](https://codecov.io/gh/apache/dubbo-samples)
[![Gitter](https://badges.gitter.im/alibaba/dubbo.svg)](https://gitter.im/alibaba/dubbo?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
![license](https://img.shields.io/github/license/apache/dubbo-samples.svg)

## This repository has  two responsibilities:
 Samples and integration test cases

### 1. Samples :  various usages of Dubbo

This repository contains a number of projects to illustrate various usages of Dubbo from basic to advanced, check README in each individual sub projects. 

Pls. cross reference to [Dubbo User Manual](http://dubbo.apache.org/en-us/docs/user/quick-start.html) for the details.

### 2. Integration test cases of Dubbo Project

#### what test cases  to write here ?

The purpose of this project is to verify issues in module integration and process invocation of Dubbo. The samples in this repository are good integration test cases.  

This project, together with Dubbo unit testing (please see [unit test guide](http://dubbo.apache.org/en-us/docs/developers/contributor-guide/test-coverage-guide_dev.html)), constitutes the functional testing of Dubbo. For those issues that are not easy to verify in Dubbo unit test , please submit here.

#### How to write integration test cases ?

This repository uses [docker](https://www.docker.com/) as a cross-process testing tool.  If you submit a PR  , the test cases runs in the docker container on the `Travis ci`machine. 
`Travis CI`,`junit5`,`testcontainers` can be used . 

When you run the test cases on your local pc,  please follow the steps :

* 1 . install and start `docker` on your pc.  https://docs.docker.com/get-started/ is docker help website. 

* 2. run mvn command in the terminal

```shell 
cd dubbo-samples
mvn clean verify -Pdubbo-integration-test

```
  Then you can run the integration test case , feel free to add new test cases 
