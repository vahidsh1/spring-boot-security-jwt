package com.bezkoder.springjwt.config;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {
    private StringWriter responseWriter = new StringWriter();
    private PrintWriter writer = new PrintWriter(responseWriter);

    public CachedBodyHttpServletResponse(HttpServletResponse response) {
        super(response);
    }

    @Override
    public PrintWriter getWriter() {
        return writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        writer.flush();
        super.flushBuffer();
    }

    public String getResponseBody() {
        return responseWriter.toString();
    }
}