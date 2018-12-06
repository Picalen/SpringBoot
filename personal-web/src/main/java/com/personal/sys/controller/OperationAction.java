package com.personal.sys.controller;

import com.personal.model.SysUser;
import com.personal.service.inter.OperationService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 操作类controll层
 *
 * @author sunchao
 * @date 2018/12/6
 */

@RestController
@RequestMapping(value = "/operation")
public class OperationAction {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OperationService operationService;


    /**
     * 测试Aop
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/operationAop", method = RequestMethod.POST)
    public JSONObject login(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        operationService.printObjectName();

        jsonObject.put("info","success");
        return jsonObject;
    }
}
