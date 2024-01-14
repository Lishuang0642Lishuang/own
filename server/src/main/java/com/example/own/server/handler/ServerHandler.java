package com.example.own.server.handler;

import com.example.own.server.bean.*;
import com.example.own.server.serializer.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        ByteBuf requestByteBuf = (ByteBuf) msg;

        Packet packet = new PacketCodec().decode(requestByteBuf);

        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();

            if(valid(loginRequestPacket)) {
                //校验成功
                System.out.println("ls登录校验成功");
                loginResponsePacket.setSuccess(true);
            } else {
                //校验失败
                System.out.println("登录校验失败");
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("账号密码校验失败");
            }

            ByteBuf responseByteBuf = new PacketCodec().encode(loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        } else if (packet instanceof MessageRequestPacket) {
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            System.out.println(new Date() + ":收到客户端消息" + messageRequestPacket.getMessage());

            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("服务端回复Ls【" + messageRequestPacket.getMessage() + "】");
            ByteBuf responseByteBuf = new PacketCodec().encode(messageResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }

        super.channelRead(ctx, msg);

    }

    private boolean valid(LoginRequestPacket requestPacket) {
        return true;
    }
}
