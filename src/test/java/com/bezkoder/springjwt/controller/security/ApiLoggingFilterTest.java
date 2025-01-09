package com.bezkoder.springjwt.controller.security;

import com.bezkoder.springjwt.filter.ApiLoggingFilter;
import com.bezkoder.springjwt.repository.UserAuditRequestRepository;
import com.bezkoder.springjwt.repository.UserAuditResponseRepository;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApiLoggingFilterTest {

    @Mock
    private UserAuditRequestRepository userAuditRequestRepository;

    @Mock
    private UserAuditResponseRepository userAuditResponseRepository;

    @InjectMocks
    private ApiLoggingFilter apiLoggingFilter;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = mock(FilterChain.class);
    }

    @Test
    void testRequestLogging() throws Exception {
        request.setRequestURI("/api/test");
        apiLoggingFilter.doFilterInternal(request, response, filterChain);

        verify(userAuditRequestRepository, atLeastOnce()).save(any());
        verify(userAuditResponseRepository, atLeastOnce()).save(any());
    }
}
