package com.shenhao.handler;

import com.shenhao.ChannelHolder;
import com.shenhao.model.CommandDTO;
import com.shenhao.model.TaskDeviceInfo;
import com.shenhao.util.JsonUtil;

import java.util.concurrent.TimeUnit;

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
            CommandDTO<TaskDeviceInfo> taskDeviceCommand = JsonUtil.deepClone(commandDTO, CommandDTO.class, TaskDeviceInfo.class);
            CommandDTO<Object> command = CommandDTO.builder().address(30).funCode(1).responseType(0).seqId(seqId).build();
            ChannelHolder.sendMessageTo(channelShortId, command);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException ignored){}
            TaskDeviceInfo data = taskDeviceCommand.getData();
            int patrolType = data.getPatrolType();
            String deviceName = data.getDeviceName();
            // 模拟拍照
            // 声音
            if (patrolType == 5) {
            }
            // 红外
            if (patrolType == 1 || patrolType == 8) {

            }
            command.setResponseType(1);
            ChannelHolder.sendMessageTo(channelShortId, command);
        }
    }
}
