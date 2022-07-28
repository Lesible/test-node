package com.shenhao.handler;

import com.shenhao.netty.ChannelHolder;
import com.shenhao.model.CommandDTO;
import com.shenhao.model.TaskDeviceInfo;
import com.shenhao.util.JsonUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * <p> @date: 2022-07-25 17:41</p>
 *
 * @author 何嘉豪
 */
public class CommandHandler {

    private static final Logger log = LoggerFactory.getLogger(CommandHandler.class);

    public void handleCommand(String channelShortId, CommandDTO<?> commandDTO) {
        if (commandDTO == null) {
            return;
        }
        int address = commandDTO.getAddress();
        int funCode = commandDTO.getFunCode();
        if (address == 30 && funCode == 1) {
            receiveSingleTaskDevice(channelShortId, commandDTO);
        }
    }

    private void receiveSingleTaskDevice(String channelShortId, CommandDTO<?> commandDTO) {
        String seqId = commandDTO.getSeqId();
        CommandDTO<TaskDeviceInfo> taskDeviceCommand = JsonUtil.deepClone(commandDTO, CommandDTO.class, TaskDeviceInfo.class);
        CommandDTO<?> command = CommandDTO.builder().address(30).funCode(1).responseType(0).seqId(seqId).build();
        ChannelHolder.sendMessageTo(channelShortId, command);
        TaskDeviceInfo data = taskDeviceCommand.getData();
        // 最后一次通知
        if (data == null) {
            return;
        }
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ignored) {
        }
        command.setResponseType(1);
        command.setCode("000000");
        int patrolType = data.getPatrolType();
        Class<?> clazz = getClass();
        String deviceName = data.getDeviceName();
        // 模拟拍照
        // 声音
        if (patrolType == 5) {
            String radioPath = data.getRadioPath();
            try (InputStream is = clazz.getResourceAsStream("/radio" + File.separator + deviceName + ".mp4")) {
                if (is == null) {
                    command.setMessage("照片文件不存在");
                    command.setCode("000001");
                    ChannelHolder.sendMessageTo(channelShortId, command);
                    return;
                }
                FileUtils.copyToFile(is, new File(radioPath));
            } catch (IOException e) {
                log.error("关闭文件失败", e);
                command.setCode("000001");
                command.setMessage("照片文件不存在");
            }
        } // 局放不处理
        else if (patrolType != 6) {
            // 红外
            String visible = "/visible" + File.separator + deviceName + ".jpg";
            if (patrolType == 1 || patrolType == 8) {
                visible = "/infrared" + File.separator + deviceName + "_V.jpg";
                String infrared = "/infrared" + File.separator + deviceName + "_I.jpg";
                try (InputStream is = clazz.getResourceAsStream(infrared)) {
                    if (is == null) {
                        command.setMessage("照片文件不存在");
                        command.setCode("000001");
                        ChannelHolder.sendMessageTo(channelShortId, command);
                        return;
                    }
                    FileUtils.copyToFile(is, new File(data.getInfraredPicPath()));
                } catch (IOException e) {
                    log.error("关闭文件失败", e);
                    command.setMessage("照片文件不存在");
                    command.setCode("000001");
                }
            }
            try (InputStream is = clazz.getResourceAsStream(visible)) {
                if (is == null) {
                    command.setMessage("照片文件不存在");
                    command.setCode("000001");
                    ChannelHolder.sendMessageTo(channelShortId, command);
                    return;
                }
                FileUtils.copyToFile(is, new File(data.getVisiblePicPath()));
            } catch (IOException e) {
                log.error("关闭文件失败", e);
                command.setCode("000001");
                command.setMessage("照片文件不存在");
            }
        }
        ChannelHolder.sendMessageTo(channelShortId, command);
    }

}
