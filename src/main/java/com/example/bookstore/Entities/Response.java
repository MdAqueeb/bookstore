package com.example.bookstore.Entities;


public class Response<T> {
    private int respStatus;
    private String respDesc;
    private T data;

    public Response(int respStatus,String respDesc,T data){
        this.respDesc = respDesc;
        this.respStatus = respStatus;
        this.data = data;
    }

    public Response(int respStatus,String respDesc){
        this.respDesc = respDesc;
        this.respStatus = respStatus;
        this.data = null;
    }
    public int get_respStatus(){
        return respStatus;
    }

    public String get_respDesc(){
        return respDesc;
    }

    public T get_data(){
        return data;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public void setRespStatus(int respStatus) {
        this.respStatus = respStatus;
    }

    public void setData(T data) {
        this.data = data;
    }
}
