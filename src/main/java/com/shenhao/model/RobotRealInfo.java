package com.shenhao.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RobotRealInfo {

    private static RobotRealInfo realInfo;
    @JsonProperty("StopButton")
    private Integer stopButton;
    @JsonProperty("RobotBoardVer")
    private Double robotBoardVer;
    @JsonProperty("BatteryAmp")
    private Double batteryAmp;
    @JsonProperty("InfraredConnectState")
    private Integer infraredConnectState;
    @JsonProperty("LaserObstaclAlarm")
    private Integer laserObstacleAlarm;
    @JsonProperty("PanTiltH")
    private Double panTiltH;
    @JsonProperty("PDConnectState")
    private Integer pDConnectState;
    @JsonProperty("UltrasonicState")
    private Integer ultrasonicState;
    @JsonProperty("DriveState")
    private Integer driveState;
    @JsonProperty("Temp")
    private Double temp;
    @JsonProperty("VisibleConnectState")
    private Integer visibleConnectState;
    @JsonProperty("MainBoardState")
    private Integer mainBoardState;
    @JsonProperty("RunMail")
    private Double runMail;
    @JsonProperty("PanTiltV")
    private Double panTiltV;
    @JsonProperty("Hum")
    private Double hum;
    @JsonProperty("PTZConnectState")
    private Integer pTZConnectState;
    @JsonProperty("BatteryPower")
    private Double batteryPower;
    @JsonProperty("SpeedZ")
    private Double speedZ;
    @JsonProperty("SpeedY")
    private Double speedY;
    @JsonProperty("SpeedX")
    private Double speedX;
    @JsonProperty("VisibleCameraZoom")
    private Double visibleCameraZoom;
    @JsonProperty("DetectTemp")
    private Double detectTemp;
    @JsonProperty("LaserState")
    private Integer laserState;
    @JsonProperty("PoseAlarm")
    private Integer poseAlarm;
    @JsonProperty("UltrasonicObstaclAlarm")
    private Integer ultrasonicObstacleAlarm;
    @JsonProperty("BatteryVol")
    private Double batteryVol;
    @JsonProperty("IsCharging")
    private Integer isCharging;

    public static synchronized RobotRealInfo getInstance() {
        if (realInfo == null) {
            realInfo = new RobotRealInfo();
            realInfo.hum = 27D;
            realInfo.temp = 35D;
            realInfo.batteryAmp = 1.5D;
            realInfo.batteryPower = 98D;
            realInfo.detectTemp = 35D;
            realInfo.batteryVol = 27D;
            realInfo.driveState = 0;
            realInfo.infraredConnectState = 0;
            realInfo.mainBoardState = 0;
            realInfo.isCharging = 0;
            realInfo.laserObstacleAlarm = 0;
            realInfo.laserState = 0;
            realInfo.panTiltH = 0D;
            realInfo.panTiltV = 0D;
            realInfo.visibleCameraZoom = 1D;
            realInfo.visibleConnectState = 0;
            realInfo.runMail = 3D;
            realInfo.speedX = 0D;
            realInfo.speedY = 0D;
            realInfo.speedZ = 0D;
        }
        return realInfo;
    }
}