package com.shenhao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class PatrolDeviceInfo {

    private Integer num;

    private Integer sent;

    @JsonProperty("taskid")
    private String taskId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty("taskstarttime")
    private LocalDateTime taskStartTime;

    @JsonProperty("devicename")
    private String deviceName;

    @JsonProperty("deviceid")
    private String deviceId;

    @JsonProperty("devicePatrolTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime devicepatroltime;


    private Double data1;

    private Double data2;

    private Double data3;

    private String data1Bak;

    private String data2Bak;

    private Integer status;

    @JsonProperty("tasktype")
    private Integer taskType;

    @JsonProperty("checkid")
    private Integer checkId;

    @JsonProperty("defectfinished")
    private String defectFinished;

    @JsonProperty("defectstart")
    private String defectStart;

    @JsonProperty("exceptiontype")
    private Integer exceptionType;

    @JsonProperty("intervalname")
    private String intervalName;

    @JsonProperty("robotcode")
    private String robotCode;

    @JsonProperty("robottype")
    private String robotType;

    @JsonProperty("visiblevideoimgpath")
    private String visibleVideoImgPath;

    @JsonProperty("infraredvideoimgpath")
    private String infraredVideoImgPath;

    @JsonProperty("videopath")
    private String videoPath;

    @JsonProperty("radiopath")
    private String radioPath;

    @JsonProperty("resultstr")
    private String resultStr;

    private String identifyRadio;

    @JsonProperty("temdata")
    private Double temData;

    @JsonProperty("humdata")
    private Double humData;

    private String reviewer;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime reviewTime;

    private String suggest;

    private String sourceIp;

}