package com.yuhuayuan.tool.returngson;

public class GsonResult<T> {
    public T o;
    private String status;
    private String msg;

    public GsonResult(T o, String status, String msgx)
    {
        this.o = o;
        this.status = status;
        this.msg =  msgx;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}