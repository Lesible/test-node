package com.shenhao;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * <p>  </p>
 * <p> created at 2022-07-21 09:37 by lesible </p>
 *
 * @author 何嘉豪
 */
public class ChannelHolder {

    private static final ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

}
