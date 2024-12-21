package com.bezkoder.springjwt.service.audit;

import com.bezkoder.springjwt.entity.UserAuditRequest;

public interface AuditLogService {
    public void saveLog(UserAuditRequest userAuditRequest);
}