package com.yuhuayuan.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cl on 2017/3/8.
 */
public class CommonResponse{
    public String code;
    public String message;

    private Object result;

    public CommonResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static CommonResponse simpleResponse(String code, String message) {
        return new CommonResponse(code, message);
    }


    private CommonResponse(String code, String message, Object result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public static CommonResponse.MapResultBuilder mapResultBuilder(String code, String message) {
        return new CommonResponse.MapResultBuilder(code, message);
    }


    public static CommonResponse objectResponse(String code, String message, Object object) {
        return new CommonResponse(code, message, object);
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public Object getResult() {
        return this.result;
    }

    public static final class MapResultBuilder {
        private final String retCode;
        private final String message;
        private final Map<String, Object> result;

        public MapResultBuilder(String retCode, String message) {
            this.retCode = retCode;
            this.message = message;
            this.result = new HashMap();
        }

        public CommonResponse.MapResultBuilder addParam(String key, Object value) {
            this.result.put(key, value);
            return this;
        }

        public CommonResponse.MapResultBuilder addParams(Map<String, Object> paramMap) {
            this.result.putAll(paramMap);
            return this;
        }

        public CommonResponse build() {
            return new CommonResponse(this.retCode, this.message, this.result);
        }
    }

}