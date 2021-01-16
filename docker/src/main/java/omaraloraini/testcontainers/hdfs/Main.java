package omaraloraini.testcontainers.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.MiniDFSCluster;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

class Main {
    static final String READY_MESSAGE = "omaraloraini.testcontainers.hdfs-ready";
    public static void main(String[] args) throws IOException {
        final String hdfsData = "/data";
        final String hdfsConfig = "/config";

        File dataFile = new File(hdfsData);
        Path configPath = Paths.get(hdfsConfig, "core-site.xml");

        final Configuration conf = new Configuration();
        conf.set("dfs.permissions.enabled", "false");
        final MiniDFSCluster cluster = new MiniDFSCluster.Builder(conf, dataFile)
            .manageNameDfsDirs(true)
            .manageDataDfsDirs(true)
            .format(true)
            .build();

        Runtime.getRuntime().addShutdownHook(new Thread(cluster::shutdown));

        cluster.waitActive();

        try (OutputStream outputStream = Files.newOutputStream(
            configPath,
            StandardOpenOption.TRUNCATE_EXISTING,
            StandardOpenOption.CREATE)) {

            cluster.getConfiguration(0).writeXml(outputStream);
        }

        System.out.println(READY_MESSAGE);
    }
}
