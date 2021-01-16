FROM openjdk:8u275-slim-buster
RUN mkdir /data
RUN mkdir /config
RUN mkdir /install
COPY build/install /install
ENTRYPOINT /install/testcontainers-hdfs/bin/testcontainers-hdfs
