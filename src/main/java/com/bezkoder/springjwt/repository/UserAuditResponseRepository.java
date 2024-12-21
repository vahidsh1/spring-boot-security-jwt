package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.entity.UserAuditRequest;
import com.bezkoder.springjwt.entity.UserAuditResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserAuditResponseRepository extends JpaRepository<UserAuditResponse, Long> {
}