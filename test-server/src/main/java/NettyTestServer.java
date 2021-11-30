import api.HelloService;
import netty.server.NettyServer;
import registry.DefaultServiceRegistry;
import registry.ServiceRegistry;

/**
 * @author Ed
 * @create 2021-11-30 21:06
 */
public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server = new NettyServer();
        server.start(9999);
    }
}
