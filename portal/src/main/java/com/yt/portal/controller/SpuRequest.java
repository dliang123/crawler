package com.yt.portal.controller;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by jackdeng on 2017/9/6.
 */
public class SpuRequest implements Serializable {

    @ApiModelProperty("代码")
    private Long code;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }
}
