package serializer;

/**
 * @author Ed
 * @create 2021-11-30 19:50
 */
public interface CommonSerializer {

    /**
     * 序列化对象为二进制码
     * @param obj
     * @return
     */
    byte[] serialize(Object obj);

    /**
     * 反序列化
     * @param bytes
     * @param clazz
     * @return
     */
    Object deserialize(byte[] bytes, Class<?> clazz);

    /**
     * 获取序列化器的hashcode
     * @return
     */
    int getCode();

    static CommonSerializer getByCode(int code) {
        switch(code){
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }


}
