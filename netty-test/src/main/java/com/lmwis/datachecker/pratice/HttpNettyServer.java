package com.lmwis.datachecker.pratice;

import com.lmwis.datachecker.pratice.proxy.handler.TestHttpHandler;
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
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * @Description: netty test
 * @Author: lmwis
 * @Data: 2022/1/27 4:19 下午
 * @Version: 1.0
 */
public class HttpNettyServer {

    public void start(int port) throws InterruptedException, NoSuchAlgorithmException, UnrecoverableKeyException, CertificateException, KeyStoreException, IOException, KeyManagementException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        serverBootstrap.group(nioEventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast("decoder",new HttpRequestDecoder())
                                .addLast("encoder",new HttpResponseEncoder())
                                .addLast("aggregator",new HttpObjectAggregator(512*1024))
                                .addLast("httpHandler",new TestHttpHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG,128)
                .childOption(ChannelOption.SO_KEEPALIVE,Boolean.TRUE);
        serverBootstrap.bind(port).sync();
    }
}
