package com.hhzclass;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.SctpChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author hhz
 * @version 2023/10/12
 */
public class AppServer {

     private int port;
     private AppServer(int port){
      this.port=port;
     }


     public void start(){
     //1.创建eventloop,老板只负责请求,之后会将请求分发至worker
          NioEventLoopGroup boss = new NioEventLoopGroup(2);
          NioEventLoopGroup worker = new NioEventLoopGroup(10);

          try {
               //2.需要一个服务器引导程序
               ServerBootstrap serverBootstrap = new ServerBootstrap();
               //3.配置服务器
               serverBootstrap  =serverBootstrap.group(boss,worker)
                       .channel(NioServerSocketChannel.class)
                       .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                 ch.pipeline().addLast(new MyChannelHandLer());
                            }
                       });
               //4.绑定端口
               ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
               channelFuture.channel().closeFuture().sync();
          }catch (InterruptedException e){
               e.printStackTrace();
          }finally {

               try {
                    worker.shutdownGracefully().sync();
                    boss.shutdownGracefully().sync();
               } catch (InterruptedException e) {
                    e.printStackTrace();
               }
          }

     }

     public static void main(String[] args) {
          new AppServer(8080).start();
     }
}
