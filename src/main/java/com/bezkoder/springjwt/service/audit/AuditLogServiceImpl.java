package com.bezkoder.springjwt.service.audit;

import com.bezkoder.springjwt.entity.UserAudit;
import com.bezkoder.springjwt.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AuditLogServiceImpl implements AuditLogService {
    @Autowired
    AuditLogRepository auditLogRepository;
    @Override
    public void saveLog(UserAudit userAudit) {
        auditLogRepository.save(userAudit);
    }
}
