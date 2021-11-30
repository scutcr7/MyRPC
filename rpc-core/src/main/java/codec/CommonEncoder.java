package codec;

import entity.RpcRequest;
import enumeration.PackageType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import serializer.CommonSerializer;

/**
 * @author Ed
 * @create 2021-11-30 17:18
 */
public class CommonEncoder extends MessageToByteEncoder {
    /**
     * @Magic Number        标识一个协议包
     * @Package Type        标明请求还是响应
     * @Serializer Type     表示序列化器
     * @Data Length         表示包的长度避免拆包粘包
     * +---------------+---------------+-----------------+-------------+
     * |  Magic Number |  Package Type | Serializer Type | Data Length |
     * |    4 bytes    |    4 bytes    |     4 bytes     |   4 bytes   |
     * +---------------+---------------+-----------------+-------------+
     * |                          Data Bytes                           |
     * |                   Length: ${Data Length}                      |
     * +---------------------------------------------------------------+
     */

    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    private final CommonSerializer serializer;

    public CommonEncoder(CommonSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        out.writeInt(MAGIC_NUMBER);
        if(msg instanceof RpcRequest){
            out.writeInt(PackageType.REQUEST_PACK);
        }else{
            out.writeInt(PackageType.RESPONSE_PACK);
        }
        out.writeInt(serializer.getCode()); // 序列化器的hashcode
        byte[] bytes = serializer.serialize(msg);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);

    }
}
