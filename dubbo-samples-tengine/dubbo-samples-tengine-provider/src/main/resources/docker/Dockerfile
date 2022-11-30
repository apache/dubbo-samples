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

FROM ubuntu:18.04

COPY sources.list /etc/apt/sources.list

RUN apt-get update && apt-get install -y build-essential wget

ENV TENGINE_VERSION 2.3.2

ADD https://github.com/alibaba/tengine/archive/${TENGINE_VERSION}.tar.gz tengine.tar.gz

RUN tar xvf tengine.tar.gz \
    && cd ./tengine-${TENGINE_VERSION} \
    && wget https://ftp.pcre.org/pub/pcre/pcre-8.43.tar.gz \
    && tar xvf pcre-8.43.tar.gz \
    && wget https://www.openssl.org/source/openssl-1.0.2s.tar.gz \
    && tar xvf openssl-1.0.2s.tar.gz \
    && wget http://www.zlib.net/zlib-1.2.11.tar.gz \
    && tar xvf zlib-1.2.11.tar.gz \
    && ./configure --add-module=./modules/mod_dubbo --add-module=./modules/ngx_multi_upstream_module --add-module=./modules/mod_config --with-pcre=./pcre-8.43/ --with-openssl=./openssl-1.0.2s/ --with-zlib=./zlib-1.2.11 \
    && make \
    && make install

COPY tengine/nginx.conf /usr/local/nginx/conf/nginx.conf

EXPOSE 80 443 8080

STOPSIGNAL SIGTERM

CMD ["/usr/local/nginx/sbin/nginx", "-g", "daemon off;"]