package com.yuhuayuan.core.component.filter;

import com.yuhuayuan.core.RequestWrapper;
import com.yuhuayuan.core.ResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by cl on 2017/3/14.
 */
public class JsonBodyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final ResponseWrapper wrapperedResponse = new ResponseWrapper((HttpServletResponse) response);
        chain.doFilter(new RequestWrapper((HttpServletRequest) request), wrapperedResponse);
        final String responseBody = wrapperedResponse.getBodyString();
        final String filteredContent = contentFilter(responseBody);

        response.setContentLength(-1);
        response.setCharacterEncoding(request.getCharacterEncoding());
        PrintWriter printWriter = response.getWriter();
        printWriter.write(filteredContent);
        printWriter.flush();
        printWriter.close();
    }

    @Override
    public void destroy() {

    }

    private String contentFilter(final String content) {
        return content;
//        if (StringUtils.isBlank(content)) {
//            return content;
//        }
//
//        final char[] charArray = StringUtils.trimToEmpty(content).toCharArray();
//        final StringBuilder sb = new StringBuilder();
//        for (final char c : charArray) {
//            sb.append(htmlEncode(c));
//        }
//
//        return sb.toString();
    }

    private String htmlEncode(final char c) {
        switch (c) {
            case '<':
                return "&lt;";
            case '>':
                return "&gt;";
            default:
                return String.valueOf(c);
        }
    }


}
