package vn.id.hph.kitecine.facade;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.id.hph.kitecine.configuration.StorageProperties;
import vn.id.hph.kitecine.exception.AppException;
import vn.id.hph.kitecine.exception.ErrorCode;
import vn.id.hph.kitecine.facade.dto.FileDto;
import vn.id.hph.kitecine.facade.dto.FileMgmtDto;
import vn.id.hph.kitecine.facade.dto.FileResource;
import vn.id.hph.kitecine.service.FileMgmtService;
import vn.id.hph.kitecine.service.FileService;
import vn.id.hph.kitecine.service.StorageService;

import java.io.IOException;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileFacade {
    FileMgmtService fileMgmtService;
    FileService fileService;
    StorageService storageService;
    StorageProperties storageProperties;

    public FileDto uploadFile(MultipartFile file) throws IOException {
        return uploadFileToDir(file, FileMgmtService.PUBLIC_FOLDER);
    }

    public FileResource downloadFile(String fileName) {
        var fileMgmt = fileMgmtService.findByName(fileName);

        if (fileMgmt.isPresent()) {
            return new FileResource(
                    storageService.loadAsResource(Paths.get(fileMgmt.get().getPath())),
                    fileMgmt.get().getContentType());
        } else {
            throw new AppException(ErrorCode.FILE_NOT_FOUND);
        }
    }

    private FileDto uploadFileToDir(MultipartFile file, String uploadFolder) throws IOException {
        FileMgmtDto uploadDir = fileMgmtService.getUploadDirectory(uploadFolder);

        var fileMgmt = fileService.uploadFileToDir(file, null, uploadDir);

        return FileDto.builder()
                .name(fileMgmt.getName())
                .type(file.getContentType())
                .uri(storageProperties.getUrl() + "/download/" + fileMgmt.getName())
                .size(file.getSize())
                .originName(file.getOriginalFilename())
                .build();
    }
}
