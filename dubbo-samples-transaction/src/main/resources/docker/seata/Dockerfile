FROM openjdk:8-jre-alpine

RUN mkdir -p /seata
WORKDIR /seata

RUN wget -U "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36" https://github.com/seata/seata/releases/download/0.5.0/seata-server-0.5.0.zip \
&& unzip seata-server-0.5.0.zip

COPY ./conf/ /seata/conf

EXPOSE 8091
CMD ["/bin/sh", "/seata/bin/seata-server.sh"]
