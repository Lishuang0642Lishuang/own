package com.example.own.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {


    /**
     * 此方法会在客户端连接建立成功之后被调用*
     * 和传统的Socket编程不同的是，Netty里的数据是以ByteBuf为单位的，所有需要写出的数据都必须放到一个ByteBuf中。数据的写出如此，数据的读取亦如此*
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws InterruptedException {
        System.out.println(new Date() + ": 客户端写出数据");

        //1、获取数据
        for (int i = 0; i < 100; i++) {
            ByteBuf buffer = getByteBuf(ctx);
            ctx.channel().writeAndFlush(buffer);
            Thread.sleep(50L);
        }
    }


    //netty数据处理
    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        // 获取到一个ByteBuf的内存管理器，作用是分配一个ByteBuf,然后把字符串的二进制数据填充到ByteBuf,这样就获取到netty需要的数据格式
        ByteBuf buffer = ctx.alloc().buffer();
        byte[] bytes = "你好，欢迎关注我的微信公众号，微信公众号博客".getBytes(StandardCharsets.UTF_8);
        buffer.writeBytes(bytes);

        return buffer;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(new Date() + "客户端读到数据 ->" + byteBuf.toString(StandardCharsets.UTF_8));

    }
}
