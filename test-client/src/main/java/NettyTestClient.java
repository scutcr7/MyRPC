import api.HelloObject;
import api.HelloService;
import core.RpcClient;
import core.RpcClientProxy;
import netty.client.NettyClient;

/**
 * @author Ed
 * @create 2021-11-30 21:28
 */
public class NettyTestClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient("127.0.0.1", 9999);
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject obj = new HelloObject(777, "777777");
        String res = helloService.hello(obj);
        System.out.println(res);
    }
}
