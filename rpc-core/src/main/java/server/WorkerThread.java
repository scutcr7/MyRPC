package server;

import entity.RpcRequest;
import entity.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author Ed
 * @create 2021-11-16 16:29
 */
public class WorkerThread implements Runnable{

    private Socket socket;
    private Object service;

    public WorkerThread(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())){
            RpcRequest rpcRequest = (RpcRequest) ois.readObject();
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            Object res = method.invoke(service, rpcRequest.getParameters());
            oos.writeObject(RpcResponse.success(res));
            oos.flush();
        }catch (IOException | ClassNotFoundException |NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            System.out.println("调用或发送时有错误发生：" + e);
        }
    }
}
