package com.shenhao.mqtt;

import com.fasterxml.jackson.databind.JavaType;
import com.shenhao.model.CommandDTO;
import com.shenhao.model.PatrolDeviceInfo;
import com.shenhao.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;

/**
 * <p>  </p>
 * <p> created at 2022-10-25 15:05 by lesible </p>
 *
 * @author 何嘉豪
 */
@Slf4j
public class AlgorithmCallback implements MqttCallback {

    private final MqttClient mqttClient;

    public AlgorithmCallback(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String json = new String(message.getPayload(), StandardCharsets.UTF_8);
        if ("close".equals(json)) {
            return;
        }
        log.info("topic:{},message:{}", topic, json);
        JavaType type = JsonUtil.constructType(CommandDTO.class, PatrolDeviceInfo.class);
        CommandDTO<PatrolDeviceInfo> command = JsonUtil.parseJson(json, type);
        if (command != null) {
            PatrolDeviceInfo data = command.getData();
            data.setStatus(0);
            data.setResultStr("测试结果");
            data.setData1(1.0D);
            CommandDTO<PatrolDeviceInfo> result = CommandDTO
                    .<PatrolDeviceInfo>builder().address(101).funCode(2).data(data).build();
            String resultJson = JsonUtil.jsonValue(result);
            log.info("发送 json: {}", resultJson);
            mqttClient.publish("RecognitionModuleServerReceived", resultJson
                    .getBytes(StandardCharsets.UTF_8), 2, false);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}

