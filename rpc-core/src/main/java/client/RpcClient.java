package client;

import entity.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Ed
 * @create 2021-11-16 16:28
 */
public class RpcClient {

    public Object sendRequest(RpcRequest rpcRequest, String host, int port){
        try(Socket socket = new Socket(host, port)){
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            oos.writeObject(rpcRequest);
            oos.flush();
            return ois.readObject();
        }catch (IOException | ClassNotFoundException e){
            System.out.println(e);
            return null;
        }
    }
}
