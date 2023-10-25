#!/bin/bash

# Use mirror:
# DEBIAN_MIRROR=http://mirrors.aliyun.com ./build-test-image.sh

#bash /usr/local/bin/k3s/k3s-uninstall.sh
#bash /usr/local/bin/k3s-uninstall.sh
ls /usr/local/bin/ | grep k3s
kubectl get nodes
if [ -e ./k3s-install.sh ]; then
    echo "k3s-install.sh exists"
else
    wget -O k3s-install.sh https://get.k3s.io/
    if [ $? -eq 0 ]; then
        echo "k3s-install.sh download success"
        chmod +x ./k3s-install.sh
        bash -x ./k3s-install.sh --docker
        systemctl status k3s
        kubectl get nodes

      else
        echo "download k3s-install.sh fail"
      fi
fi

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

$DIR/dubbo-test-runner/build.sh

$DIR/build-nacos-image.sh
