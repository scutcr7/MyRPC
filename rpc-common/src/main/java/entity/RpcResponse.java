package entity;

import java.io.Serializable;

/**
 * @author Ed
 * @create 2021-11-16 16:25
 */
public class RpcResponse<T> implements Serializable {

    /**
     *  响应状态码
     */
    private Integer statusCode;

    /**
     *  响应状态补充信息
     */
    private String message;

    /**
     *  相应数据
     */
    private T data;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> RpcResponse<T> success(T data){
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(200);
        response.setData(data);
        return response;
    }

    public static <T> RpcResponse<T> fail(){
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(500);
        response.setMessage("wrong");
        return response;
    }

    public T getData() {
        return data;
    }
}
