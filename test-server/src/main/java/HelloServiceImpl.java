import api.HelloObject;
import api.HelloService;

/**
 * @author Ed
 * @create 2021-11-16 16:30
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(HelloObject obj) {
//        System.out.println(obj.toString());
        return obj.toString();
    }
}
