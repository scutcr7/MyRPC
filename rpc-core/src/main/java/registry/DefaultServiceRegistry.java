package registry;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Ed
 * @create 2021-11-17 20:34
 */
public class DefaultServiceRegistry implements ServiceRegistry{

    private final Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    private final Set<String> registeredService = ConcurrentHashMap.newKeySet();

    @Override
    public synchronized <T> void register(T service) {
        // 获取类的规范名， String 是 java.lang.String
        String serviceName = service.getClass().getCanonicalName();
        if(registeredService.contains(serviceName)){
            return;
        }
        registeredService.add(serviceName);

        Class<?>[] interfaces = service.getClass().getInterfaces();
        if(interfaces.length == 0){
//            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);

        }
        for(Class<?> clazz : interfaces){
            System.out.println(clazz.toString());
            serviceMap.put(clazz.getCanonicalName(), service);
        }
        System.out.println("向接口: " + interfaces + "注册服务: " + serviceName);
    }

    @Override
    public synchronized Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if(service == null){

        }
        return service;
    }
}
