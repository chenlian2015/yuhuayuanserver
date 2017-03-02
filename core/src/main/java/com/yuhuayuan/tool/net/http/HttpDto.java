package com.yuhuayuan.tool.net.http;


import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;

public class HttpDto {
    private Map<String, String> headers = new HashMap();
    private String resString;

    public HttpDto(Header[] headers, String parseString) {
        Header[] var3 = headers;
        int var4 = headers.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Header header = var3[var5];
            this.headers.put(header.getName(), header.getValue());
        }

        this.resString = parseString;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public String getResString() {
        return this.resString;
    }
}
