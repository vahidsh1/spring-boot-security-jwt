package com.bezkoder.springjwt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class UserAuditRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String endpoint;
    private String method;
    private LocalDateTime timestamp;
    private String requestBody;
    // Relationship to User entity (one-to-many)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public UserAuditRequest( UserEntity user) {
        this.user = user;
    }


        @CreatedDate
        @Column(name = "CreatedOn")
        private LocalDateTime createdOn;

        @CreatedBy
        @Column(name = "CreatedBy", length = 50)
        private String createdBy;


        @LastModifiedDate
        @Column(name = "UpdatedOn")
        private LocalDateTime updatedOn;

        @LastModifiedBy
        @Column(name = "UpdatedBy", length = 50)
        private String updatedBy;

        @Column(name = "DeletedOn")
        private LocalDateTime deletedOn;

        @Column(name = "DeletedBy", length = 50)
        private String deletedBy;

        @Column(name = "isDeleted", length = 50)
        private Boolean isDeleted = false;

        @PreUpdate
        @PrePersist
        public void beforeAnyUpdate() {
            if (isDeleted != null && isDeleted) {

                if (deletedBy == null) {
                    deletedBy = UserEntityHelper.userId().toString();
                }

                if (getDeletedOn() == null) {
                    deletedOn = LocalDateTime.now();
                }
            }
        }


}