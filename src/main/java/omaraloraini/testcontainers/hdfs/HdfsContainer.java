package omaraloraini.testcontainers.hdfs;

import com.github.dockerjava.api.command.InspectContainerResponse;
import org.apache.hadoop.conf.Configuration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HdfsContainer extends GenericContainer<HdfsContainer> {
    private static final String IMAGE = "omaraloraini/testcontainers-hdfs:3.0.3-1.1";
    private final Path configPath;

    public HdfsContainer() {
        super(DockerImageName.parse(IMAGE));
        setWaitStrategy(
            Wait.forLogMessage(Main.READY_MESSAGE + "\\n", 1)
        );
        setNetworkMode("host");
        final String envPath = System.getenv("HDFS_CONFIG");
        configPath = envPath == null ? Paths.get("/config") : Paths.get(envPath);
    }

    private final Configuration hadoopConf = new Configuration();
    public Configuration hadoopConf() {
        return hadoopConf;
    }

    @Override
    protected void containerIsStarted(InspectContainerResponse containerInfo) {
        try {
            String xml = execInContainer("cat", configPath.resolve("core-site.xml").toString()).getStdout()
                .replace("\n","").replace("\t","");

            hadoopConf.addResource(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
