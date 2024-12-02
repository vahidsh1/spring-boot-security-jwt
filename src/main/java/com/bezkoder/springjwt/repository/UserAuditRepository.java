package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.entity.UserAudit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuditRepository extends JpaRepository<UserAudit, Long> {
}