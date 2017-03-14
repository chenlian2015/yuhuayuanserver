package com.yuhuayuan.core;

import com.yuhuayuan.databinder.DataBinderManager;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by cl on 2017/3/14.
 */

public class RequestWrapper extends HttpServletRequestWrapper {
    private final String requestBody;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        if(this.isFileUpload(request)) {
            this.requestBody = "";
        } else {
            this.requestBody = IOUtils.toString(request.getInputStream(), this.getCharacterEncoding());
        }

        DataBinderManager.getDataBinder("request_body_binder").put(this.requestBody);
    }

    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(this.requestBody.getBytes());
        return new ServletInputStream() {
            public boolean isFinished() {
                return bais.available() == 0;
            }

            public boolean isReady() {
                return true;
            }

            public void setReadListener(ReadListener readListener) {
            }

            public int read() throws IOException {
                return bais.read();
            }
        };
    }

    private boolean isFileUpload(HttpServletRequest request) {
        boolean isPost = request.getMethod().equals("POST");
        String contentType = request.getContentType();
        return isPost && StringUtils.isNotBlank(contentType) && contentType.toLowerCase().contains("multipart/form-data");
    }
}

