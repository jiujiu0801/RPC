package com.hhzclass;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @author hhz
 * @version 2023/10/12
 */
public class MyChannelHandLer2 extends ChannelInboundHandlerAdapter {

 @Override
 public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
  ByteBuf byteBuf = (ByteBuf) msg;
  System.out.println("客户端收到消息:"+ byteBuf.toString(StandardCharsets.UTF_8));

 }
}
