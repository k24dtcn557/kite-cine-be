package vn.id.hph.kitecine.service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.entity.FileMgmt;
import vn.id.hph.kitecine.enums.FileType;
import vn.id.hph.kitecine.facade.dto.FileMgmtDto;
import vn.id.hph.kitecine.repository.FileMgmtRepository;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileService {
    FileMgmtRepository fileMgmtRepository;
    StorageService storageService;

    public FileMgmt uploadFileToDir(MultipartFile file, String userId, FileMgmtDto uploadDir) throws IOException {
        String fileId = UUID.randomUUID().toString();
        String filename = fileId + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        Path path = Paths.get(uploadDir.path()).resolve(filename);
        storageService.store(file, path);

        FileMgmt fileMgmt = FileMgmt.builder()
                .id(fileId)
                .contentType(file.getContentType())
                .type(FileType.FILE)
                .name(filename)
                .path(path.toString())
                .parentId(uploadDir.id())
                .size(file.getSize())
                .build();

        return fileMgmtRepository.save(fileMgmt);
    }
}
