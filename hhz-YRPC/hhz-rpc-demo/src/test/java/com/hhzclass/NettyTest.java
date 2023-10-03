package com.hhzclass;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

/**
 * @author hhz
 * @version 2023/10/3
 */
public class NettyTest {

    @Test
    public void testByteBuf(){
        ByteBuf header = Unpooled.buffer();
        ByteBuf body = Unpooled.buffer();

        //通过逻辑组装而不是物理拷贝,实现在jvm中的零拷贝
        CompositeByteBuf byteBuf =Unpooled.compositeBuffer();
        byteBuf.addComponents(header,body);
    }

    @Test
    public void testWrapper(){
        byte[] bytes1 = new byte[1024];
        byte[] bytes2 = new byte[1024];

        //共享byte数组的内容而不是拷贝,这也算零拷贝
        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes1, bytes2);

    }

    @Test
    public void testSlice(){
        byte[] bytes1 = new byte[1024];
        byte[] bytes2 = new byte[1024];
        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes1,bytes2);

        //同样可以将一个byteBuf,分割成多个,使用共享地址,而非拷贝.
        ByteBuf slice1 = byteBuf.slice(1,5);
        ByteBuf slice2 = byteBuf.slice(6,15);


    }
}
