package com.shenhao.mqtt;

import com.fasterxml.jackson.databind.JavaType;
import com.shenhao.model.CommandDTO;
import com.shenhao.model.PatrolDeviceInfo;
import com.shenhao.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;

/**
 * <p>  </p>
 * <p> created at 2022-10-25 14:39 by lesible </p>
 *
 * @author 何嘉豪
 */
@Slf4j
public class ReceivedMqttMes {
    public static MqttClient client = null;

    public ReceivedMqttMes() {
    }


    public static void start() throws MqttException {
        if (client == null) {
            client = new MqttClient("tcp://172.16.141.2:1883", "test-node", new MemoryPersistence());
            client.setCallback(new AlgorithmCallback(client));
        }
        // MQTT的连接设置
        MqttConnectOptions options = new MqttConnectOptions();
        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，设置为true表示每次连接到服务器都以新的身份连接
        options.setCleanSession(false);
        // 设置连接的用户名
        options.setUserName("public");
        // 设置连接的密码
        options.setPassword("admin".toCharArray());
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(10);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        options.setKeepAliveInterval(20);
        //设置断开后重新连接
        options.setAutomaticReconnect(true);

        //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
        if (client.isConnected()) {
            client.disconnect();
        }
        client.connect(options);
        //订阅消息
        client.subscribe("RecognitionModuleServerSend", 2);
    }

    public static void main(String[] args) throws Exception {
        start();
    }

    public void stop() {
        try {
            // 断开连接
            client.disconnect();
            // 关闭客户端
            client.close();
        } catch (MqttException e) {
            log.error("", e);
        }
    }


}
