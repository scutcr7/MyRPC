import api.HelloObject;
import api.HelloService;
import client.RpcClientProxy;


/**
 * @author Ed
 * @create 2021-11-16 16:30
 */
public class TestClient {

    public static void main(String[] args) {
        // 相当于获取RPC代理服务器
        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 9000);
        // 传入想要调用的服务Class对象, 返回服务端给你的接口（实现类）
        HelloService helloService = proxy.getProxy(HelloService.class);
        // 生成请求参数
        HelloObject helloObject = new HelloObject();
        // 直接在本地调用远程接口服务
        String res = helloService.hello(helloObject);
        System.out.println(res);
    }

}
