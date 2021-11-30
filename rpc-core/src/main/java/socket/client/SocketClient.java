package socket.client;

import core.RpcClient;
import entity.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Ed
 * @create 2021-11-16 16:28
 */
public class SocketClient implements RpcClient {

    private String host;
    private int port;

    public SocketClient(String host, int port){
        this.host = host;
        this.port = port;
    }


    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
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
