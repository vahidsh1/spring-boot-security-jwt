package com.bezkoder.springjwt.service.audit;

import com.bezkoder.springjwt.entity.UserAudit;
import com.bezkoder.springjwt.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface AuditLogService {
    public void saveLog(UserAudit userAudit);
}