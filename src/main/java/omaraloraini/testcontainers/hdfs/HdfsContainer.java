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
    static final String READY_MESSAGE = "omaraloraini.testcontainers.hdfs-ready";
    private static final String IMAGE = "omaraloraini/testcontainers-hdfs:3.0.3-1.1";
    private final Path configPath;

    public HdfsContainer() {
        super(DockerImageName.parse(IMAGE));
        setWaitStrategy(
            Wait.forLogMessage(READY_MESSAGE + "\\n", 1)
        );
        setNetworkMode("host");
        configPath = Paths.get("/config/core-site.xml");
    }

    private final Configuration hadoopConf = new Configuration();
    public Configuration hadoopConf() {
        return hadoopConf;
    }

    @Override
    protected void containerIsStarted(InspectContainerResponse containerInfo) {
        try {
            String xml = execInContainer("cat", configPath.toString()).getStdout()
                .replace("\n","").replace("\t","");

            hadoopConf.addResource(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
