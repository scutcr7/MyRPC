package socket.server;

import core.RequestHandler;
import entity.RpcRequest;
import entity.RpcResponse;
import registry.ServiceRegistry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Ed
 * @create 2021-11-17 21:05
 */
public class RequestHandlerThread implements Runnable{

    private Socket socket;
    private RequestHandler requestHandler;
    private ServiceRegistry serviceRegistry;

    public RequestHandlerThread(Socket socket, RequestHandler requestHandler, ServiceRegistry serviceRegistry) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public void run() {
        try(ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())){

            RpcRequest rpcRequest = (RpcRequest) ois.readObject();
            String interfaceName = rpcRequest.getInterfaceName();
            Object service = serviceRegistry.getService(interfaceName);
            Object handle = requestHandler.handle(rpcRequest, service);
            oos.writeObject(RpcResponse.success(handle));
            oos.flush();
        }catch (IOException | ClassNotFoundException e){
            System.out.println("有错误发生" + e);
        }
    }
}
