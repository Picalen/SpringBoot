import config from '../utils/config';
import { request } from '../utils/request';

export async function query() {
  return request('/api/users');
}

export async function queryCurrent() {
  return request(`${config.URL}/user/getCurrentUser`);
}
