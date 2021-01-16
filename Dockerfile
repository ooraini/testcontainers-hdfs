FROM openjdk:8u275-slim-buster
RUN mkdir /data
RUN mkdir /config
RUN mkdir /app
COPY docker/build/install /app
ENTRYPOINT /app/docker/bin/docker
