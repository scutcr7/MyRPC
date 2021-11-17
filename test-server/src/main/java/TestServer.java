import api.HelloService;
import registry.DefaultServiceRegistry;
import registry.ServiceRegistry;
import server.RpcServer;

/**
 * @author Ed
 * @create 2021-11-16 16:30
 */
public class TestServer {

    public static void main(String[] args) {
//        // 启动RPC本地服务
//        RpcServer rpcServer = new RpcServer();
//        // 注册HelloService调用服务
//        HelloService helloService = new HelloServiceImpl();
//        rpcServer.register(helloService, 9000);
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        HelloService helloService = new HelloServiceImpl();
//        serviceRegistry.register(HelloService.class);
        serviceRegistry.register(helloService);
        RpcServer rpcServer = new RpcServer(serviceRegistry);
        rpcServer.start(9000);
    }
}
