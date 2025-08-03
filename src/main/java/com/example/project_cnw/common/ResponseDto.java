package com.example.project_cnw.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {
    private boolean success;
    private String message;
    private Object data;

    public static ResponseDto success(String message, Object data) {
        ResponseDto response = new ResponseDto();
        response.success = true;
        response.message = message;
        response.data = data;
        return response;
    }

    public static ResponseDto success(String message){
        ResponseDto response = new ResponseDto();
        response.success = true;
        response.message = message;
        response.data = null;
        return response;
    }

    public static ResponseDto failure(String message){
        ResponseDto response = new ResponseDto();
        response.success = false;
        response.message = message;
        response.data = null;
        return response;
    }

    public static ResponseDto failure(String message, Object errorData) {
        ResponseDto response = new ResponseDto();
        response.success = false;
        response.message = message;
        response.data = errorData;
        return response;
    }
}
