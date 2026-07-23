package vn.id.hph.kitecine.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.id.hph.kitecine.enums.FileType;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "file_mgmt",
        indexes = {@Index(name = "name_index", columnList = "name")})
public class FileMgmt extends AbstractAuditEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name", length = 200, nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private FileType type;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "path", length = 2000, nullable = false)
    private String path;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "size")
    private Long size;
}
