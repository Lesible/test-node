package com.shenhao.runnable;

import com.shenhao.model.CommandDTO;
import com.shenhao.model.RobotRealInfo;
import com.shenhao.netty.ChannelHolder;

/**
 * <p> @date: 2022-07-28 17:08</p>
 *
 * @author 何嘉豪
 */
public class RobotInfoSender implements Runnable {

    private final String channelShortId;

    public RobotInfoSender(String channelShortId) {
        this.channelShortId = channelShortId;
    }

    @Override
    public void run() {
        CommandDTO<RobotRealInfo> command = new CommandDTO<>();
        command.setAddress(103);
        command.setFunCode(4);
        command.setData(RobotRealInfo.getInstance());
        ChannelHolder.sendMessageTo(channelShortId, command);
    }
}
