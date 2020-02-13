## nacos

You should install the Docker before you run `docker-compose up` to start the nacos, prometheus and grafana.

And the directory you run `docker-compose up` could be bind mounted into containers(If you are macOS user, take care of it).

And then you should create the file which will be mounted into container manually, including ./init.d/custom.properties
and ./prometheus/prometheus-standalone.yaml. In fact, all files(not directory) mentioned in docker-compose.yml should be created manually. 