package com.bezkoder.springjwt.config;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

import java.io.*;

public class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {
    private byte[] cachedBody;

    public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        InputStream inputStream = request.getInputStream();
        this.cachedBody = StreamUtils.copyToByteArray(inputStream);
    }

    @Override
    public ServletInputStream getInputStream() {
        return new CachedBodyServletInputStream(cachedBody);
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    public byte[] getBody() {
        return cachedBody;
    }
}

class CachedBodyServletInputStream extends ServletInputStream {
    private ByteArrayInputStream inputStream;

    public CachedBodyServletInputStream(byte[] cachedBody) {
        this.inputStream = new ByteArrayInputStream(cachedBody);
    }

    @Override
    public boolean isFinished() {
        return inputStream.available() == 0;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }
}


//package com.bezkoder.springjwt.config;
//
//import jakarta.servlet.ReadListener;
//import jakarta.servlet.ServletInputStream;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletRequestWrapper;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.nio.charset.StandardCharsets;
//
//public class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {
//    private final byte[] cachedBody;
//
//    public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
//        super(request);
//        try (ServletInputStream inputStream = request.getInputStream()) {
//            this.cachedBody = inputStream.readAllBytes();
//        }
//    }
//
//    @Override
//    public ServletInputStream getInputStream() {
//        return new CachedBodyServletInputStream(cachedBody);
//    }
//
//    @Override
//    public BufferedReader getReader() {
//        return new BufferedReader(new InputStreamReader(getInputStream(), StandardCharsets.UTF_8));
//    }
//
//    public String getBody() {
//        return new String(cachedBody, StandardCharsets.UTF_8);
//    }
//
//    private static class CachedBodyServletInputStream extends ServletInputStream {
//        private final byte[] body;
//        private int index = 0;
//
//        public CachedBodyServletInputStream(byte[] body) {
//            this.body = body;
//        }
//
//        @Override
//        public boolean isFinished() {
//            return index >= body.length;
//        }
//
//        @Override
//        public boolean isReady() {
//            return true;
//        }
//
//        @Override
//        public void setReadListener(ReadListener readListener) {
//            throw new UnsupportedOperationException();
//        }
//
//        @Override
//        public int read() {
//            return (index < body.length) ? (body[index++] & 0xff) : -1;
//        }
//    }
//}