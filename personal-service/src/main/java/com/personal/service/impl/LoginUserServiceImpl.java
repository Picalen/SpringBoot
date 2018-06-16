package com.personal.service.impl;

import com.personal.mapper.RolePermissionDao;
import com.personal.model.SysUser;
import com.personal.service.inter.LoginUserService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author  daila
 * @date 2017/12/21.
 */
@Service
public class LoginUserServiceImpl implements LoginUserService {
    private Logger logger =Logger.getLogger(this.getClass());

    @Autowired
    private RolePermissionDao rolePermissionDao;
    /**
     * 当前登入用户信息查询
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject findLoginUserInfoList(String userName) throws Exception {
        JSONObject json = new JSONObject();
        SysUser user = rolePermissionDao.findUserInfoByUsername(userName);
        json.put("uid",user.getUid());
        json.put("userName",user.getUsername());
        json.put("departId",user.getDepartId());
        return json;
    }
}
