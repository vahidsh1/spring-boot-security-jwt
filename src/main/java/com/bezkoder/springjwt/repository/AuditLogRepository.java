package com.bezkoder.springjwt.repository;


import com.bezkoder.springjwt.entity.User;
import com.bezkoder.springjwt.entity.UserAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuditLogRepository extends JpaRepository<UserAudit, Long> {

}