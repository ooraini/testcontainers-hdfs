package omaraloraini.testcontainers.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.MiniDFSCluster;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.*;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
        final String hdfsData = System.getenv("HDFS_DATA");
        final String hdfsConfig = System.getenv("HDFS_CONFIG");
        Objects.requireNonNull(hdfsData);
        Objects.requireNonNull(hdfsConfig);

        File dataFile = new File(hdfsData);
        Path configPath = Paths.get(hdfsConfig, "core-site.xml");

        final Configuration conf = new Configuration();
        conf.set("dfs.permissions.enabled", "false");
        final MiniDFSCluster cluster = new MiniDFSCluster.Builder(conf, dataFile)
            .manageNameDfsDirs(true)
            .manageDataDfsDirs(true)
            .format(true)
            .build();

        try (OutputStream outputStream = Files.newOutputStream(
            configPath,
            StandardOpenOption.TRUNCATE_EXISTING,
            StandardOpenOption.CREATE)) {

            cluster.getConfiguration(0).writeXml(outputStream);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(cluster::shutdown));
    }
}
