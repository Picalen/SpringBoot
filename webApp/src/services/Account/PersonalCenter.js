import config from '../../utils/config';
import { request } from '../../utils/request';

/**
 * 初始化查询
 * @return 详见同名mock文件
 */
export async function fetch() {
  return request(`${config.URL}/user/findUserBaseInfo`, {
    method: 'POST',
  });
}

/**
 * 更新账户信息
 * @param {
 * nickname: string 用户名
 * phone: string 手机号码
 * email: string 电子邮箱
 * weChat: string 微信
 * }
 * @return 成功与否
 */
export async function updateUserInfo(params) {
  return request(`${config.URL}/user/updateUserInfo`, {
    method: 'POST',
    body: params,
  });
}

/**
 * 修改密码
 * @param {
 * oldPassword: string 旧密码
 * newPassword: string 新密码
 * }
 * @return 成功与否
 */
export async function updatePassword(params) {
  return request(`${config.URL}/user/updatePassword`, {
    method: 'POST',
    body: params,
  });
}
/*

/!**
 * 修改密码
 * @param {
 * old_pwd: string 旧密码
 * new_pwd: string 新密码
 * }
 * @return 详见mock文件
 *!/
export async function updatePassword(params) {
  return request(`${config.URL}/user/updatePwd`, {
    method: 'POST',
    body: params,
  });
}
*/
