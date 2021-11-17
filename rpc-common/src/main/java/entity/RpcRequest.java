package entity;

import java.io.Serializable;

/**
 * @author Ed
 * @create 2021-11-16 16:25
 */
public class RpcRequest implements Serializable {
    /**
     *  待调用接口名称
     */
    private String interfaceName;

    /**
     *  待调用方法名称
     */
    private String methodName;

    /**
     *  待调用方法参数
     */
    private Object[] parameters;

    /**
     *  待调用方法参数类型
     */
    private Class<?>[] paramTypes;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class<?>[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public static RpcRequest.Builder builder() {
        return new RpcRequest.Builder();
    }

    private RpcRequest(Builder builder) {
        interfaceName = builder.interfaceName;
        methodName = builder.methodName;
        parameters = builder.parameters;
        paramTypes = builder.paramTypes;
    }

    public static class Builder {

        private String interfaceName;

        private String methodName;

        private Object[] parameters;

        private Class<?>[] paramTypes;


        public Builder interfaceName(String interfaceName){
            this.interfaceName = interfaceName;
            return this;
        }
        public Builder methodName(String methodName){
            this.methodName = methodName;
            return this;
        }
        public Builder parameters(Object[] parameters){
            this.parameters = parameters;
            return this;
        }
        public Builder paramTypes(Class<?>[] paramTypes){
            this.paramTypes = paramTypes;
            return this;
        }

        public RpcRequest build() {
            // 将builder对象传入到学生构造函数
            return new RpcRequest(this);
        }
    }
}
