package vn.id.hph.kitecine.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.id.hph.kitecine.controller.reponse.ApiResponse;
import vn.id.hph.kitecine.facade.FileFacade;
import vn.id.hph.kitecine.facade.dto.FileDto;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/media")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MediaFileController {
    FileFacade fileFacade;

    vn.id.hph.kitecine.service.QrService qrService;

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws IOException {
        var fileResource = fileFacade.downloadFile(filename);
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_TYPE,
                        StringUtils.hasText(fileResource.contentType())
                                ? fileResource.contentType()
                                : "application/octet-stream")
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + fileResource.resource().getFilename() + "\"")
                .header(HttpHeaders.CACHE_CONTROL, "public, max-age=2592000")
                .header(HttpHeaders.PRAGMA, "")
                .body(fileResource.resource());
    }

    @GetMapping("/qrcode/{qrCode}")
    public ResponseEntity<Resource> getQrCode(@PathVariable String qrCode) throws IOException {
        byte[] png = qrService.generatePng(qrCode, 300, 300);
        var resource = new org.springframework.core.io.ByteArrayResource(png);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/png")
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"qrcode.png\"")
                .header(HttpHeaders.CACHE_CONTROL, "public, max-age=2592000")
                .header(HttpHeaders.PRAGMA, "")
                .body(resource);
    }


    @PostMapping("/upload")
    public ApiResponse<FileDto> uploadFile(@RequestPart("file") MultipartFile file) throws IOException {
        var fileDto = fileFacade.uploadFile(file);
        log.info("File uploaded: {}", file.getOriginalFilename());
        return ApiResponse.<FileDto>builder().result(fileDto).build();
    }
}
