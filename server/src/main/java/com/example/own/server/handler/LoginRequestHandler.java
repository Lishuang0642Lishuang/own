package com.example.own.server.handler;

import com.example.own.server.bean.LoginRequestPacket;
import com.example.own.server.bean.LoginResponsePacket;
import com.example.own.server.bean.Packet;
import com.example.own.server.bean.Session;
import com.example.own.server.serializer.PacketCodec;
import com.example.own.server.utils.SessionUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Random;

public class LoginRequestHandler extends ChannelInboundHandlerAdapter {


    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        ByteBuf requestByteBuf = (ByteBuf) msg;

        Packet packet = new PacketCodec().decode(requestByteBuf);

        if(packet instanceof LoginRequestPacket) {

            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            System.out.println("loginRequest");
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginRequestPacket.setUsername(loginRequestPacket.getUsername());
            SessionUtil.bindSession(new Session(loginRequestPacket.getUserId(), loginRequestPacket.getUsername()), ctx.channel());

            ctx.channel().writeAndFlush(loginResponsePacket);
        }

    }


    public void channelInactive(ChannelHandlerContext ctx) {
        SessionUtil.unBindSession(ctx.channel());
    }









    /**
     * 获取随机的用户id*
     * @return
     */
    private String randomUserId() {

        String userId = "userId" + new Random().nextInt();
        return userId;
    }
}
