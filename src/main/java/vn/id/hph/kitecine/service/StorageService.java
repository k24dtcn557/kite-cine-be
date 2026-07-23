package vn.id.hph.kitecine.service;

import java.io.File;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void init();

    String store(MultipartFile file, Path path);

    Resource loadAsResource(Path path);

    void delete(File file);

    boolean isExists(File file);

    void createDirectory(File file);

    void rename(Path oldName, String newName);
}
