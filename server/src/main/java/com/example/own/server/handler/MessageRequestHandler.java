package com.example.own.server.handler;

import com.example.own.server.bean.MessageRequestPacket;
import com.example.own.server.bean.MessageResponsePacket;
import com.example.own.server.bean.Session;
import com.example.own.server.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        System.out.println("接收到消息 MessageRequestHandler");
        //1、获得消息发送方的会话消息
        Session session = SessionUtil.getSession(ctx.channel());

        //2、通过消息发送方的会话消息构造要发送的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage("回复->" + msg.getMessage());
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());

        //3、获得消息接收方的channel
        Channel toUserChannel = SessionUtil.getChannel(msg.getToUserId());

        //4、将消息发给消息接收方
        if(toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.out.println("【" + msg.getToUserId() + "】不在线，发送失败");
        }
    }
}
