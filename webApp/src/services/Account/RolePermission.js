import config from '../../utils/config';
import { request } from '../../utils/request';

export async function fetch() {
  return request(`${config.URL}/rolePer/findRolePerList`, {
    method: 'POST',
  });
}

export async function query(params) {
  return request(`${config.URL}/rolePer/findRolePerAfterHandle`, {
    method: 'POST',
    body: params,
  });
}

export async function add(params) {
  return request(`${config.URL}/rolePer/saveRoleInfo`, {
    method: 'POST',
    body: params,
  });
}

export async function edit(params) {
  return request(`${config.URL}/rolePer/updateRoleName`, {
    method: 'POST',
    body: params,
  });
}

export async function remove(params) {
  return request(`${config.URL}/rolePer/deleteRoleInfo`, {
    method: 'POST',
    body: params,
  });
}

export async function getPermission(params) {
  return request(`${config.URL}/rolePer/getSelectRolePer`, {
    method: 'POST',
    body: params,
  });
}

export async function editRole(params) {
  return request(`${config.URL}/rolePer/saveRoleInfo`, {
    method: 'POST',
    body: params,
  });
}
/*

export async function fetch(params) {
  return request('/api/RolePermission/fetch', {
    method: 'POST',
    body: params,
  });
}

export async function add(params) {
  return request('/api/RolePermission/add', {
    method: 'POST',
    body: params,
  });
}

export async function edit(params) {
  return request('/api/RolePermission/edit', {
    method: 'POST',
    body: params,
  });
}

export async function remove(params) {
  return request('/api/RolePermission/remove', {
    method: 'POST',
    body: params,
  });
}

export async function getPermission(params) {
  return request('/api/RolePermission/getPermission', {
    method: 'POST',
    body: params,
  });
}
*/
