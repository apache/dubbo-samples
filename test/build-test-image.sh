#!/bin/bash

# Use mirror:
# DEBIAN_MIRROR=http://mirrors.aliyun.com ./build-test-image.sh

if [ -f k3s-install.sh ]; then
  echo "k3s-install.sh exists"
else
  # 下载 k3s-install.sh 文件
  wget -O k3s-install.sh https://get.k3s.io/

  # 检查下载是否成功
  if [ $? -eq 0 ]; then
    echo "k3s-install.sh download success"
    # 授予执行权限
    chmod +x k3s-install.sh
    ./k3s-install.sh --docker
  else
    echo "download k3s-install.sh fail"
  fi
fi

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

$DIR/dubbo-test-runner/build.sh

$DIR/build-nacos-image.sh
