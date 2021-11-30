package codec;

import entity.RpcRequest;
import entity.RpcResponse;
import enumeration.PackageType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serializer.CommonSerializer;

import java.util.List;

/**
 * @author Ed
 * @create 2021-11-30 17:18
 */
public class CommonDecoder extends ReplayingDecoder {

    private static final Logger logger = LoggerFactory.getLogger(CommonDecoder.class);

    private static final int MAGIC_NUMBER = 0xCAFEBABE;


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int magic = in.readInt();
        if(magic != MAGIC_NUMBER) {
            logger.error("不识别的协议包: {}", magic);
            throw new Exception();
        }

        int packageCode = in.readInt();
        Class<?> packageClass;
        if(packageCode == PackageType.REQUEST_PACK) {
            packageClass = RpcRequest.class;
        } else if(packageCode == PackageType.RESPONSE_PACK) {
            packageClass = RpcResponse.class;
        } else {
            logger.error("不识别的数据包: {}", packageCode);
            throw new Exception();
        }
        int serializerCode = in.readInt();
        CommonSerializer serializer = CommonSerializer.getByCode(serializerCode);
        if(serializer == null) {
            logger.error("不识别的反序列化器: {}", serializerCode);
            throw new Exception();
        }
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        Object obj = serializer.deserialize(bytes, packageClass);
        out.add(obj);

    }
}