package com.lmwis.datachecker.pratice;

import com.lmwis.datachecker.pratice.proxy.handler.TestHttpHandler;
import com.lmwis.datachecker.pratice.proxy.handler.TestHttpsHandler;
import com.lmwis.datachecker.pratice.setting.JvmHookSetting;
import com.lmwis.datachecker.pratice.setting.ProxySetCommand;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.io.IOException;

/**
 * @Description: netty test
 * @Author: lmwis
 * @Data: 2022/1/27 4:19 下午
 * @Version: 1.0
 */
public class NettyTestServer {
    private final static int PORT = 8888;
    public static void main(String[] args) throws InterruptedException, IOException {

        // 为网络设置代理
        ProxySetCommand.setProxy(PORT);
        // 注册jvm关闭回调
        JvmHookSetting.registerShutdownHook();

        new NettyTestServer().start();
    }
    public void start() throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        serverBootstrap.group(nioEventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        System.out.println("init channel:"+socketChannel);
                        socketChannel.pipeline()
                                .addLast("decoder",new HttpRequestDecoder())
                                .addLast("encoder",new HttpResponseEncoder())
                                .addLast("aggregator",new HttpObjectAggregator(512*1024))
                                .addLast("httpHandler",new TestHttpHandler())
                                .addLast("httpsHandler", new TestHttpsHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG,128)
                .childOption(ChannelOption.SO_KEEPALIVE,Boolean.TRUE);
        serverBootstrap.bind(PORT).sync();
    }
}
