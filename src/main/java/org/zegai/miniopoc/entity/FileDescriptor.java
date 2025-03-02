package org.zegai.miniopoc.entity;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class FileDescriptor {

    @Id
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "name")
    private String name;

    @Column(name = "extension", length = 20)
    private String extension;

    @Column(name = "size")
    private Long size;
}
