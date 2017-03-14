package com.yuhuayuan.core;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

/**
 * Created by cl on 2017/3/14.
 */

public class ResponseWrapper extends HttpServletResponseWrapper {
    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private final PrintWriter printWriter;
    private final ServletOutputStream servletOutputStream = new ServletOutputStream() {
        public boolean isReady() {
            return false;
        }

        public void setWriteListener(WriteListener writeListener) {
        }

        public void write(int b) throws IOException {
            ResponseWrapper.this.buffer.write(b);
        }
    };

    public ResponseWrapper(HttpServletResponse response) throws UnsupportedEncodingException {
        super(response);
        this.printWriter = new PrintWriter(new OutputStreamWriter(this.buffer, this.getCharacterEncoding()));
    }

    public PrintWriter getWriter() throws IOException {
        return this.printWriter;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        return this.servletOutputStream;
    }

    public void flushBuffer() throws IOException {
        if(this.servletOutputStream != null) {
            this.servletOutputStream.flush();
        }

        if(this.printWriter != null) {
            this.printWriter.flush();
        }

    }

    public void reset() {
        this.buffer.reset();
    }

    public String getBodyString() throws IOException {
        this.flushBuffer();
        return this.buffer.toString(this.getCharacterEncoding());
    }
}
