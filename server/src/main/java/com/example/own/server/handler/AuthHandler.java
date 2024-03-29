package com.example.own.server.handler;

import com.example.own.server.utils.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!LoginUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            //一行代码实现逻辑删除
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        if (LoginUtil.hasLogin(ctx.channel())) {
            System.out.println("当前连接登录验证完毕，无须再次验证，AuthHandler被移除");
        } else {
            System.out.println("无须验证，强制关闭连接");
        }

    }
}
