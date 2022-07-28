package com.shenhao.netty;

import com.shenhao.handler.CommandHandler;
import com.shenhao.model.CommandDTO;
import com.shenhao.runnable.RobotInfoSender;
import com.shenhao.util.JsonUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Echoes uppercase content of text frames.
 */

public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static final Logger log = LoggerFactory.getLogger(WebSocketFrameHandler.class);

    private static final ScheduledThreadPoolExecutor POOL_EXECUTOR = new ScheduledThreadPoolExecutor(30);

    private static final Map<String, List<Future<?>>> TASK_MAPPING = new ConcurrentHashMap<>(4);

    private final CommandHandler commandHandler;

    public WebSocketFrameHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        ChannelHolder.addChannel(channel);
        String shortChannelId = channel.id().asShortText();
        ScheduledFuture<?> future = POOL_EXECUTOR.scheduleAtFixedRate(new RobotInfoSender(shortChannelId), 5L, 10L, TimeUnit.SECONDS);
        List<Future<?>> futures = TASK_MAPPING.computeIfAbsent(shortChannelId, k -> new ArrayList<>());
        futures.add(future);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        ChannelHolder.removeChannel(channel);
        String shortChannelId = channel.id().asShortText();
        List<Future<?>> futures = TASK_MAPPING.get(shortChannelId);
        for (Future<?> future : futures) {
            future.cancel(true);
        }
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