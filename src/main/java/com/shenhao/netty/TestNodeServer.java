package com.shenhao.netty;

import com.shenhao.mqtt.ReceivedMqttMes;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * <p>  </p>
 * <p> created at 2022-07-20 13:48 by lesible </p>
 *
 * @author 何嘉豪
 */
public class TestNodeServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(5);
        EventLoopGroup workerGroup = new NioEventLoopGroup(60);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
//                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new WebSocketServerInitializer());
            Channel channel = bootstrap.bind(33532).sync().channel();
            new Thread(() -> {
                try {
                    ReceivedMqttMes.start();
                } catch (MqttException e) {
                    throw new RuntimeException(e);
                }
            }, "mqtt").start();
            channel.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
