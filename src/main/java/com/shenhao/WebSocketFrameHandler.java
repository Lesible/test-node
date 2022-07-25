package com.shenhao;

import com.shenhao.handler.CommandHandler;
import com.shenhao.model.CommandDTO;
import com.shenhao.util.JsonUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Echoes uppercase content of text frames.
 */

public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static final Logger log = LoggerFactory.getLogger(WebSocketFrameHandler.class);
    private final CommandHandler commandHandler;

    public WebSocketFrameHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelHolder.addChannel(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ChannelHolder.removeChannel(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        if (frame instanceof TextWebSocketFrame) {
            String jsonRequest = ((TextWebSocketFrame) frame).text();
            log.info("jsonRequest: {}", jsonRequest);
            String channelShortId = ctx.channel().id().asShortText();
            CommandDTO<?> commandDTO = JsonUtil.parseJson(jsonRequest, CommandDTO.class);
            commandHandler.handleCommand(channelShortId, commandDTO);
        } else {
            String message = "unsupported frame type: " + frame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }
}