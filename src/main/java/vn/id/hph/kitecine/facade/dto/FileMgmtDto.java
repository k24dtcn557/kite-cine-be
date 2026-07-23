package vn.id.hph.kitecine.facade.dto;

import java.time.Instant;

import vn.id.hph.kitecine.enums.FileType;

public record FileMgmtDto(
        String id,
        Instant createdAt,
        Instant updatedAt,
        String name,
        FileType type,
        String parentId,
        String path,
        Long size) {}
