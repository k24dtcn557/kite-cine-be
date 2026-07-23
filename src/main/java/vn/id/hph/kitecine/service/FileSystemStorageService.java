package vn.id.hph.kitecine.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.configuration.StorageProperties;
import vn.id.hph.kitecine.exception.AppException;
import vn.id.hph.kitecine.exception.ErrorCode;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileSystemStorageService implements StorageService {

    private final StorageProperties properties;

    @Override
    public void init() {}

    @Override
    public String store(MultipartFile file, Path path) {
        try {
            if (file.isEmpty()) {
                log.info("[Store] Failed to store empty file " + path.getFileName());
            }

            if (path.getFileName().toString().contains("..")) {
                // This is a security check
                log.info(
                        "[Store] Cannot store file with relative path outside current directory " + path.getFileName());
            }

            try (InputStream inputStream = file.getInputStream()) {
                log.info("PATH: {}", path.getParent().toString());
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            log.info("[Store] Failed to store file " + path.getFileName(), e);
        }

        return path.getFileName().toString();
    }

    @Override
    public Resource loadAsResource(Path path) {
        try {
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                log.info("[LoadAsResource] Could not read file: " + path.getFileName());
            }
        } catch (MalformedURLException e) {
            log.error("[LoadAsResource] Could not read file: " + path.getFileName(), e);
        }
        return null;
    }

    @Override
    public void delete(File file) {
        if (!file.exists()) {
            log.info("[Delete] The file or dir isn't exist");
            return;
        }

        log.info("[Delete] File is dir: " + file.isDirectory());
        if (file.isDirectory()) {
            try {
                Files.walk(file.toPath())
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException e) {
                log.error("[Delete] Delete directory error occurred", e);
                throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
            }
        } else {
            if (!file.delete()) {
                log.info("[Delete] Delete file error occurred");
                throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
            }
        }
    }

    @Override
    public void rename(Path oldName, String newName) {
        try {
            Files.move(oldName, oldName.resolveSibling(newName));
        } catch (IOException e) {
            log.error("[Rename] Rename file or directory error occurred", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @Override
    public boolean isExists(File file) {
        return file.exists();
    }

    @Override
    public void createDirectory(File file) {
        if (isExists(file)) {
            log.info("[CreateDirectory] Directory is exist: " + file.getName());
            return;
        }

        try {
            Files.createDirectories(file.toPath());
        } catch (IOException e) {
            log.error("[CreateDirectory] Create directory error occurred", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
