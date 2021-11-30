package core;

import entity.RpcRequest;

/**
 * @author Ed
 * @create 2021-11-30 15:29
 */
public interface RpcClient {
    /**
     * 客户端类通用接口
     */
    Object sendRequest(RpcRequest rpcRequest);
}
