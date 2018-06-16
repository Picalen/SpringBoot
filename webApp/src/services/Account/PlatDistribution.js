import config from '../../utils/config';
import { request } from '../../utils/request';

/**
 * 初始化调用
 * @param params
 * @return {Promise<Object>}
 */
export async function fetch() {
  return request(`${config.URL}/userPlat/getUserPlatList`, {
    method: 'POST',
  });
}

/**
 * 初始化调用
 * @param {
 * username: string 用户名
 * nickname: string 姓名
 * }
 * @return {Promise<Object>}
 */
export async function query(params) {
  return request(`${config.URL}/userPlat/getUserPlatListByUsername`, {
    method: 'POST',
    body: params,
  });
}

export async function getRoles(params) {
  return request(`${config.URL}/userPlat/getUserPlatListByUserId`, {
    method: 'POST',
    body: params,
  });
}

export async function setRoles(params) {
  return request(`${config.URL}/userPlat/updateUserPlat`, {
    method: 'POST',
    body: params,
  });
}
