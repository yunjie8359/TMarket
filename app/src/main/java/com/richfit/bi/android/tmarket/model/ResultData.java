package com.richfit.bi.android.tmarket.model;

public class ResultData {

    private String stat;
    private String msg;
    private Object data;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{\"stat\":\"" + this.stat + "\",\"msg\":\"" + this.msg + "\",\"data\":\"" + this.data + "\"}";
    }

}
