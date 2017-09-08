package com.yt.portal.controller;

import com.yt.demo.core.dao.SPUDao;
import com.yt.demo.core.entity.SPUEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * web-api
 * Created by jackdeng on 2017/9/5.
 */
@Controller
@RequestMapping(value = "/api")
public class ApiController {

    @Autowired
    private SPUDao spuDao;

    @ApiOperation("获取指定spu信息")
    @RequestMapping(value = "post", method = RequestMethod.POST)
    @ResponseBody
    public List<SPUEntity> post(@RequestBody SpuRequest request)  {
        return spuDao.findTop60ByModelId(Long.valueOf(request.getCode()));
    }
}
