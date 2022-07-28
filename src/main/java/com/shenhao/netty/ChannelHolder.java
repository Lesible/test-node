package com.shenhao.netty;

import com.shenhao.util.JsonUtil;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>  </p>
 * <p> created at 2022-07-21 09:37 by lesible </p>
 *
 * @author 何嘉豪
 */
public class ChannelHolder {

    private static final Logger log = LoggerFactory.getLogger(ChannelHolder.class);
    private static final ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static final Map<String, Channel> CHANNEL_MAPPING = new ConcurrentHashMap<>(4);

    public static void addChannel(Channel channel) {
        CHANNEL_GROUP.add(channel);
        String id = channel.id().asShortText();
        CHANNEL_MAPPING.put(id, channel);
    }

    public static Channel removeChannel(Channel channel) {
        String id = channel.id().asShortText();
        CHANNEL_GROUP.remove(channel);
        return CHANNEL_MAPPING.remove(id);
    }

    public static void sendMessage(String text) {
        boolean empty = CHANNEL_GROUP.isEmpty();
        if (empty) {
            return;
        }
        log.info("发送 websocket 消息:{}", text);
        CHANNEL_GROUP.writeAndFlush(new TextWebSocketFrame(text));
    }

    public static void sendMessageTo(String shortId, Object data) {
        Channel channel = CHANNEL_MAPPING.get(shortId);
        if (channel == null) {
            return;
        }
        String jsonMessage = JsonUtil.jsonValue(data);
        log.info("发送 websocket 消息:{}", jsonMessage);
        channel.writeAndFlush(new TextWebSocketFrame(jsonMessage));
    }

}
