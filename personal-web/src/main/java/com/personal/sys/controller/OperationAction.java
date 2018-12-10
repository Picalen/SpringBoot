package com.personal.sys.controller;

import com.personal.model.SysUser;
import com.personal.model.User;
import com.personal.service.inter.OperationService;
import com.personal.service.inter.UserRedisService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

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

    @Autowired
    @Qualifier("userRedisImpl")
    private UserRedisService userRedisService;

    /**
     * 测试Aop
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/operationAop", method = RequestMethod.POST)
    public JSONObject operationAop(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        operationService.printObjectName();

        jsonObject.put("info","success");
        return jsonObject;
    }

    /**
     * 测试Redis
     *
     * @param map
     * @return
     */
    @PostMapping(value = "/findById")
    public JSONObject findById(@RequestParam  Map<String,Object> map) {
        JSONObject jsonObject = new JSONObject();
        User testUser = new User();
        testUser.setId(Integer.parseInt(map.get("id1").toString()));
        User user1 = userRedisService.findById(testUser);
        testUser.setId(Integer.parseInt(map.get("id2").toString()));
        User user2 = userRedisService.findById(testUser);

        jsonObject.put("user1",user1);
        jsonObject.put("user2",user2);
        jsonObject.put("info","success");
        return jsonObject;
    }
}
