import api.HelloService;
import registry.DefaultServiceRegistry;
import registry.ServiceRegistry;
import socket.server.SocketServer;

/**
 * @author Ed
 * @create 2021-11-16 16:30
 */
public class TestServer {

    public static void main(String[] args) {
//        // 启动RPC本地服务
//        core.RpcServer rpcServer = new core.RpcServer();
//        // 注册HelloService调用服务
//        HelloService helloService = new HelloServiceImpl();
//        rpcServer.register(helloService, 9000);
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        HelloService helloService = new HelloServiceImpl();
//        serviceRegistry.register(HelloService.class);
        serviceRegistry.register(helloService);
        SocketServer rpcServer = new SocketServer(serviceRegistry);
        rpcServer.start(9000);
    }
}
