package vn.id.hph.kitecine.facade.dto;

import org.springframework.core.io.Resource;

public record FileResource(Resource resource, String contentType) {}
