
# testcontainers-hdfs
[![maven](https://maven-badges.herokuapp.com/maven-central/io.github.omaraloraini/testcontainers-hdfs/badge.svg?gav=true)](https://maven-badges.herokuapp.com/maven-central/io.github.omaraloraini/testcontainers-hdfs)

Docker container image for Hadoop HDFS mini cluster, and a testcontainers libray API for using it

## Usage
```
@Container
HdfsContainer hdfsContainer = new HdfsContainer();

org.apache.hadoop.conf.Configuration conf = hdfsContainer.hadoopConf();
```
