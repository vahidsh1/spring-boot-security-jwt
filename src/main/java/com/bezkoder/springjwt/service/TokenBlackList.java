package com.bezkoder.springjwt.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

public interface TokenBlackList {
    void addToBlacklist(String token);
    boolean isBlacklisted(String token);
}

