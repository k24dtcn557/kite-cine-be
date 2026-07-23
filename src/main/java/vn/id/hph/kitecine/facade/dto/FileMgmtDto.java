package vn.id.hph.kitecine.facade.dto;

import vn.id.hph.kitecine.enums.FileType;

import java.time.Instant;

public record FileMgmtDto(
        String id,
        Instant createdAt,
        Instant updatedAt,
        String name,
        FileType type,
        String parentId,
        String path,
        Long size) {}
