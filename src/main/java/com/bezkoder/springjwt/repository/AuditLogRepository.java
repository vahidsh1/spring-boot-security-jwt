package com.bezkoder.springjwt.repository;


import com.bezkoder.springjwt.entity.UserAuditRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<UserAuditRequest, Long> {

}