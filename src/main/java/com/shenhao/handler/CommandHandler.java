package com.shenhao.handler;

import com.shenhao.ChannelHolder;
import com.shenhao.model.CommandDTO;

/**
 * <p> @date: 2022-07-25 17:41</p>
 *
 * @author 何嘉豪
 */
public class CommandHandler {

    public void handleCommand(String channelShortId, CommandDTO<?> commandDTO) {
        if (commandDTO == null) {
            return;
        }
        int address = commandDTO.getAddress();
        int funCode = commandDTO.getFunCode();
        if (address == 30 && funCode == 1) {
            String seqId = commandDTO.getSeqId();
            CommandDTO<Object> command = CommandDTO.builder().address(30).funCode(1).responseType(0).seqId(seqId).build();
            ChannelHolder.sendMessageTo(channelShortId, command);
        }
    }
}
