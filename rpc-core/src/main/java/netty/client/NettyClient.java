package netty.client;

import codec.CommonDecoder;
import codec.CommonEncoder;
import core.RpcClient;
import entity.RpcRequest;
import entity.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serializer.JsonSerializer;


/**
 * @author Ed
 * @create 2021-11-30 16:25
 */
public class NettyClient implements RpcClient {
    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private String host;
    private int port;
    private static final Bootstrap bootstrap;

    public NettyClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    static {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new CommonDecoder());
                        pipeline.addLast(new CommonEncoder(new JsonSerializer()));
                        pipeline.addLast(new NettyClientHandler());

                    }
                });
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        try {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            Channel channel = future.channel();
            if(channel != null){
                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if(future1.isSuccess()){
                        logger.info(String.format("客户端发送消息： %s", rpcRequest.toString()));
                    } else{
                        logger.error("发送消息时有错误产生： " + future1.cause());
                    }
                });
                channel.closeFuture().sync();
                // 阻塞获得结果
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
                RpcResponse rpcResponse = channel.attr(key).get();
                return rpcResponse;
            }
        } catch (InterruptedException e){
            logger.error("发送消息时有错误发生: ", e);
        }
        return null;
    }
}
