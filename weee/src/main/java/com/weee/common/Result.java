package com.weee.common;


public class Result {
    private int code;//编码 200/400
    private String msg;//成功/失败

    private Long total;//总记录数
    private Object data;//数据


    //成功返回
    public static Result success(){
        return result(200,"成功",0L,null);
    }
    public static Result success(Object data) {
        return result(200,"成功",0L,data);
    }
    public static Result success(Object data,Long total) {
        return result(200,"成功",total,data);
    }
    //失败返回
    public static Result fail() {
        return  result(400,"失败",0L,null );
    }

    private static Result result(int code, String msg, Long total, Object data) {
        Result res=new Result();
        res.setCode(code);
        res.setMsg(msg);
        res.setTotal(total);
        res.setData(data);
        return res;

    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
