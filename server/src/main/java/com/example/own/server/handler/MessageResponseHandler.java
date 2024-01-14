package com.example.own.server.handler;

import com.example.own.server.bean.MessageResponsePacket;
import com.example.own.server.bean.Session;
import com.example.own.server.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {

       Session session = SessionUtil.getSession(ctx.channel());
        System.out.println(("session 中的user:"+ session.getUserId()+":"+session.getUserName()));

        String fromUserId = msg.getFromUserId();
        String fromUserName = msg.getFromUserName();

        System.out.println(fromUserId + ":" + fromUserName + "->" + msg.getMessage());
    }
}
