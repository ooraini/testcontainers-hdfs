FROM openjdk:8u275-slim-buster
ENV HDFS_DATA /data
ENV HDFS_CONFIG /config
RUN mkdir $HDFS_DATA
RUN mkdir $HDFS_CONFIG
RUN mkdir /install
COPY build/install /install
ENTRYPOINT /install/testcontainers-hdfs/bin/testcontainers-hdfs
