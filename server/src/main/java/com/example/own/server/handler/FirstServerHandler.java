package com.example.own.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + ": 服务端读到数据 ->" + byteBuf.toString(StandardCharsets.UTF_8));

        System.out.println("服务端写出数据");

        ByteBuf out = getByteBuf(ctx);
        ctx.channel().writeAndFlush(out);
    }



    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "hello return".getBytes(StandardCharsets.UTF_8);

        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }
}
