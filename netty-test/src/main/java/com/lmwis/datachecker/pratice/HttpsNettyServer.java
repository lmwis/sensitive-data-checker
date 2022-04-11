package com.lmwis.datachecker.pratice;

import com.lmwis.datachecker.pratice.proxy.SSLSupport;
import com.lmwis.datachecker.pratice.proxy.handler.TestHttpsHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.OptionalSslHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLEngine;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/4/10 5:49 下午
 * @Version: 1.0
 */
public class HttpsNettyServer {

    public void start(int port) throws InterruptedException, NoSuchAlgorithmException, UnrecoverableKeyException, CertificateException, KeyStoreException, IOException, KeyManagementException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        final SSLEngine engine = SSLSupport.getServerContext().createSSLEngine();
        engine.setUseClientMode(false);
        SelfSignedCertificate ssc=new SelfSignedCertificate();
        SslContext sslCtx= SslContextBuilder.forServer(ssc.certificate(),ssc.privateKey()).build();

        serverBootstrap.group(nioEventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addFirst("ssl",new OptionalSslHandler(sslCtx))
//                                .addLast("ssl",new SslHandler(engine))
                                .addLast("decoder",new HttpRequestDecoder())
                                .addLast("encoder",new HttpResponseEncoder())
                                .addLast("aggregator",new HttpObjectAggregator(512*1024))
                                .addLast("httpsHandler", new TestHttpsHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG,128)
                .childOption(ChannelOption.SO_KEEPALIVE,Boolean.TRUE);
        serverBootstrap.bind(port).sync();
    }
}
