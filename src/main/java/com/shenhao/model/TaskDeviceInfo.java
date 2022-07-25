package com.shenhao.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TaskDeviceInfo {

    @JsonProperty("VisibleVideoZoom")
    private Double visibleVideoZoom;

    @JsonProperty("PanTiltAngleH1")
    private Double panTiltAngleH1;

    @JsonProperty("TaskID")
    private String taskID;

    @JsonProperty("NavigationPoint")
    private Integer navigationPoint;

    @JsonProperty("PanTiltAngleH")
    private Double panTiltAngleH;

    @JsonProperty("VisibleVideoZoom1")
    private Double visibleVideoZoom1;

    @JsonProperty("VisibleVideoFocus1")
    private Integer visibleVideoFocus1;

    @JsonProperty("VisibleVideoFocus")
    private Integer visibleVideoFocus;

    @JsonProperty("PanTiltAngleV")
    private Double panTiltAngleV;

    @JsonProperty("ScriptPath")
    private String scriptPath;

    @JsonProperty("PatrolType")
    private Integer patrolType;

    @JsonProperty("PanTiltAngleV1")
    private Double panTiltAngleV1;

    @JsonProperty("VisiblePicPath")
    private String visiblePicPath;

    @JsonProperty("infraredTemperature")
    private Integer infraredTemperature;

    @JsonProperty("correctInIPC")
    private Integer correctInIPC;

    @JsonProperty("DeviceName")
    private String deviceName;
}