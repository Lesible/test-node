package com.shenhao.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommandDTO<T> {

    @JsonProperty("Address")
    private Integer address;

    @JsonProperty("FunCode")
    private Integer funCode;

    @JsonProperty("Data")
    private T data;

    @JsonProperty("ResponseType")
    private Integer responseType;

    @JsonProperty("SeqId")
    private String seqId;
}