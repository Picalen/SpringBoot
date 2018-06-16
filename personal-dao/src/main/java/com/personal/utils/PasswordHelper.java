package com.personal.utils;

import com.personal.model.SysUser;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author 王嘉
 * @date 2017/12/20
 * 密码加密
 */
public class PasswordHelper {
    private String algorithmName = "md5";
    private int hashIterations = 2;

    public void encryptPassword(SysUser user) {
        String newPassword = new SimpleHash(algorithmName, user.getPassword(),  ByteSource.Util.bytes(user.getUsername()), hashIterations).toHex();
        user.setPassword(newPassword);

    }
    public static void main(String[] args) {
        PasswordHelper passwordHelper = new PasswordHelper();
        SysUser user = new SysUser();
        user.setUsername("ceshi1");
        user.setPassword("888888");
        passwordHelper.encryptPassword(user);
        System.out.println(user.getPassword());
    }
}
