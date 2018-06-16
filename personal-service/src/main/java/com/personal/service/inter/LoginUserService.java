package com.personal.service.inter;

import net.sf.json.JSONObject;


/**
 * @author  daila
 * @date 2017/12/21.
 */
public interface LoginUserService {
    /**
     * 获取loginUserInfoList数据
     * @param userName
     * @return
     * @throws Exception
     */
    JSONObject findLoginUserInfoList(String userName) throws Exception;
}
