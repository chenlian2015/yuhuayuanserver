package com.yuhuayuan.tool;

import com.alibaba.fastjson.JSON;
import com.yuhuayuan.common.Constant;
import com.yuhuayuan.tool.json.JsonUtils;
import com.yuhuayuan.tool.returngson.GsonResult;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;

public class ResponseWriter {

    public static boolean writeToResponse(HttpServletResponse response, Object obj) {
        writeResponseHeader(response);
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            String ret = JSON.toJSONString(obj);

            byte[] bytes = ret.getBytes(Charset.forName(Constant.DEFAULT_CHAR_SET));
            response.setContentLength(bytes.length);
            out.write(bytes);
            out.flush();
            return true;
        } catch (Exception e) {
           
            return false;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                  
                }
            }
        }
    }

    public static void writeErrResponse(HttpServletResponse response, int code, String msg) {
        GsonResult errorModel = new GsonResult("", ""+code, msg);
        writeToResponse(response, errorModel);
    }

    public static void writeResponseHeader(HttpServletResponse response) {
        response.setContentType(Constant.JSON_CONTENT_TYPE);
        response.setCharacterEncoding(Constant.DEFAULT_CHAR_SET);
    }

    public static void writeJsonResponse(ServletResponse response, String responseJson) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(responseJson);
        writer.flush();
        writer.close();
    }

    public static <T> void writeJsonResponse(ServletResponse response, T responseObject) throws IOException {
        String jsonStr = JsonUtils.toJsonString(responseObject);
        writeJsonResponse(response, jsonStr);
    }
}
