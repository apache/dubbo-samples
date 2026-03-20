#!/bin/bash

mkdir nacos-mysql

#curl -o nacos-mysql/Dockerfile https://raw.githubusercontent.com/nacos-group/nacos-docker/master/example/image/mysql/5.7/Dockerfile

#Since mysql-schema.sql is not found at https://raw.githubusercontent.com, we add it from github.com:
cat > nacos-mysql/Dockerfile << EOF
FROM mysql:5.7.40
ADD https://github.com/alibaba/nacos/blob/master/distribution/conf/mysql-schema.sql /docker-entrypoint-initdb.d/nacos-mysql.sql
RUN chown -R mysql:mysql /docker-entrypoint-initdb.d/nacos-mysql.sql
EXPOSE 3306
CMD ["mysqld", "--character-set-server=utf8mb4", "--collation-server=utf8mb4_unicode_ci"]
EOF

docker build -t nacos-mysql:5.7 nacos-mysql/
