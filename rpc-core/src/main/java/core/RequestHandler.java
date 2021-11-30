package core;

import entity.RpcRequest;
import entity.RpcResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Ed
 * @create 2021-11-17 21:04
 */
public class RequestHandler {

    public Object handle(RpcRequest rpcRequest, Object service){
        Object result = null;
        try {
            result = invokeTargetMethod(rpcRequest, service);
            System.out.println("服务: " + rpcRequest.getInterfaceName() + "成功调用方法: " + rpcRequest.getMethodName());
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println("有错误发生");
        }
        return result;
    }

    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) throws IllegalAccessException, InvocationTargetException {
        Method method;
        try {
            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
        } catch (NoSuchMethodException e) {
            return RpcResponse.fail();
        }
        return method.invoke(service, rpcRequest.getParameters());
    }
}
