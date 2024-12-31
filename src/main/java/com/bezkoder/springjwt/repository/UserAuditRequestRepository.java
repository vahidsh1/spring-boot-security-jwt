package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.entity.UserAuditRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserAuditRequestRepository extends JpaRepository<UserAuditRequest, Long> {

}