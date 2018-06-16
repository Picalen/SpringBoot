import config from '../..//utils/config';
import { request } from '../../utils/request';

/**
 * 账号密码登录
 * @param {
 * username: string 账号
 * password: 密码
 * }
 * @return {Promise<Object>}
 */
export async function accountLogin(params) {
  return request(`${config.URL}/user/login`, {
    method: 'POST',
    body: params,
  });
}

/**
 * 手机号验证码登录--本期不实现
 * @param {
 * mobile: string 手机号
 * captcha: string 验证码
 * }
 * @return {Promise<Object>}
 */
export async function mobileLogin(params) {
  return request(`${config.URL}/user/login/mobile`, {
    method: 'POST',
    body: params,
  });
}

/**
 * 登出
 * @return {Promise<Object>}
 */
export async function logout() {
  return request(`${config.URL}/user/logout`, {
    method: 'POST',
  });
}
