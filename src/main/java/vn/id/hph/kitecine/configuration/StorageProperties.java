package vn.id.hph.kitecine.configuration;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {

    private String location;
    private String url;

    public String getLevel1Folder(String folderName) {
        return location + File.separator + folderName;
    }
}
