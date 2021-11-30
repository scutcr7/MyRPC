package core;

import entity.RpcRequest;
import entity.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Ed
 * @create 2021-11-30 15:30
 */
public class RpcClientProxy implements InvocationHandler {

    private String host;
    private int port;
    private RpcClient client;


    public RpcClientProxy(RpcClient client){
        this.client = client;
    }

    public <T> T getProxy(Class<T> clazz){
        // 不要用getInterFace方法，因为本身传进来的就是一个接口的Class对象了
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameters(args)
                .paramTypes(method.getParameterTypes())
                .build();
        return ((RpcResponse) client.sendRequest(rpcRequest)).getData();
    }
}
