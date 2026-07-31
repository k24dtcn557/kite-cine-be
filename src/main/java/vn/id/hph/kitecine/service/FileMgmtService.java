package vn.id.hph.kitecine.service;

import java.io.File;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import vn.id.hph.kitecine.configuration.StorageProperties;
import vn.id.hph.kitecine.entity.FileMgmt;
import vn.id.hph.kitecine.enums.FileType;
import vn.id.hph.kitecine.facade.dto.FileMgmtDto;
import vn.id.hph.kitecine.mapper.FileMgmtMapper;
import vn.id.hph.kitecine.repository.FileMgmtRepository;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileMgmtService {

    FileMgmtRepository fileMgmtRepository;
    FileMgmtMapper fileMgmtMapper;
    StorageService storageService;
    StorageProperties storageProperties;

    @NonFinal
    public static final String PUBLIC_FOLDER = "public";

    private static String generateShortUUID() {
        return UUID.randomUUID().toString();
    }

    @Transactional
    public FileMgmtDto getUploadDirectory(String folderName) {
        FileMgmt level1Folder = getLevel1Folder(folderName);
        return fileMgmtMapper.toFileMgmtDto(level1Folder);
    }

    private FileMgmt getUploadFolder(FileMgmt parent) {
        LocalDate today = LocalDate.now();
        String monthPath = String.format("%04d-%02d", today.getYear(), today.getMonthValue());
        String fullPath = parent.getName() + File.separator + monthPath;
        FileMgmt uploadFolder = fileMgmtRepository.findFolder(fullPath).orElse(null);

        if (uploadFolder != null) {
            return uploadFolder;
        }

        String path = storageProperties.getLevel1Folder(parent.getName()) + File.separator + monthPath;
        uploadFolder = FileMgmt.builder()
                .parentId(parent.getId())
                .id(generateShortUUID())
                .type(FileType.DIR)
                .name(fullPath)
                .path(path)
                .build();

        storageService.createDirectory(new File(path));
        return fileMgmtRepository.save(uploadFolder);
    }

    private FileMgmt getLevel1Folder(String folderName) {
        FileMgmt fileMgmt = fileMgmtRepository.findLevel1Folder(folderName).orElse(null);

        if (fileMgmt != null) {
            return fileMgmt;
        }

        String path = storageProperties.getLevel1Folder(folderName);
        fileMgmt = FileMgmt.builder()
                .id(generateShortUUID())
                .type(FileType.DIR)
                .name(folderName)
                .path(path)
                .build();

        storageService.createDirectory(new File(path));

        return fileMgmtRepository.saveAndFlush(fileMgmt);
    }

    public Optional<FileMgmt> findByName(String fileName) {
        return fileMgmtRepository.findByName(fileName);
    }
}
