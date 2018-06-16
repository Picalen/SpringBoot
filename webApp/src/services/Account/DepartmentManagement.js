import config from '../../utils/config';
import { request } from '../../utils/request';

/**
 * 初始化调用
 * 下级以children字段表示
 * @return {
 * data: array{ id, managementName, children: array{同上级}}
 * }
 */
export async function fetch() {
  return request(`${config.URL}/department/getDepartmentList`, {
    method: 'POST',
  });
}

/**
 * 新增部门
 * @param { parentId: 上级部门(string), managementName: 部门名称(string) }
 * @return {
 * data: array{ id, managementName, children: array{同上级}}
 * }
 */
export async function add(params) {
  return request(`${config.URL}/department/addDepartInfo`, {
    method: 'POST',
    body: params,
  });
}

/**
 * 编辑部门
 * @param { managementName: 部门名称(string) }
 * @return {
 * data: array{ id, managementName, children: array{同上级}}
 * }

 */
export async function edit(params) {
  return request(`${config.URL}/department/updateDepartInfo`, {
    method: 'POST',
    body: params,
  });
}

/**
 * 删除部门
 * @param { id: 部门id(string) }
 * @return {
 * data: array{ id, managementName, children: array{同上级}}
 * }

 */
export async function remove(params) {
  return request(`${config.URL}/department/delDepartInfo`, {
    method: 'POST',
    body: params,
  });
}

/*
export async function fetch(params) {
  return request('/api/departmentManagement/fetch', {
    method: 'POST',
    body: params,
  });
}

export async function add(params) {
  return request('/api/departmentManagement/add', {
    method: 'POST',
    body: params,
  });
}

export async function edit(params) {
  return request('/api/departmentManagement/edit', {
    method: 'POST',
    body: params,
  });
}

export async function remove(params) {
  return request('/api/departmentManagement/remove', {
    method: 'POST',
    body: params,
  });
}
*/
