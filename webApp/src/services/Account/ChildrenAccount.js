import config from '../../utils/config';
import { request } from '../../utils/request';

/**
 * 初始化查询
 * @param null
 * @return 见mock文件
 */
export async function fetch(params) {
  return request(`${config.URL}/children/getChildAccountList`, {
    method: 'POST',
    body: params,
  });
}

/**
 * 新增数据
 * @param {
 * username: string 用户名
 * department: number 所属部门
 * }
 * @return {
 * flag: boolean 成功 or 失败
 * }
 */
export async function add(params) {
  return request(`${config.URL}/children/saveChildAccount`, {
    method: 'POST',
    body: params,
  });
}

/**
 * 编辑数据
 * @param {
 * id: number 主键
 * username: string 用户名
 * name: string 姓名
 * department: string 部门
 * departmentId: number 部门主键
 * phone: string 手机
 * email: string 邮箱
 * lock: boolean 是否锁定
 * }
 * @return {
 * flag: boolean 成功 or 失败
 * }
 */
export async function edit(params) {
  return request(`${config.URL}/children/saveChildAccount`, {
    method: 'POST',
    body: params,
  });
}

/**
 * 重置密码
 * @param {
 * id: number 主键
 * password: number 密码发送方式
 * }
 * @return {
 * flag: boolean 成功 or 失败
 * }
 */
export async function remove(params) {
  return request(`${config.URL}/children/removeChildAccount`, {
    method: 'POST',
    body: params,
  });
}

/**
 * 锁定/解锁
 * @param {
 * id: number 主键
 * lock: boolean 锁定/解锁
 * }
 * @return {
 * flag: boolean 成功 or 失败
 * }
 */
export async function editLock(params) {
  return request(`${config.URL}/children/saveChildAccount`, {
    method: 'POST',
    body: params,
  });
}

/**
 * 删除数据
 * @param {
 * id: number 主键
 * }
 * @return {
 * flag: boolean 成功 or 失败
 * }
 */
export async function init(params) {
  return request(`${config.URL}/children/changePassword`, {
    method: 'POST',
    body: params,
  });
}

/**
 * 查看角色
 * @param {
 * id: number 主键
 * }
 * @return {
 * roleData: array 可分配角色
 * targetKeys: array 已分配角色
 * }
 */
export async function getRoles(params) {
  return request(`${config.URL}/children/getRoleList`, {
    method: 'POST',
    body: params,
  });
}

/**
 * 分配角色
 * @param {
 * id: number 主键
 * roles: array 分配角色
 * }
 * @return {
 * flag: boolean 成功 or 失败
 * }
 */
export async function setRoles(params) {
  return request(`${config.URL}/children/setUserRoles`, {
    method: 'POST',
    body: params,
  });
}

/*

/!**
 * 初始化查询
 * @param null
 * @return 见mock文件
 *!/
export async function fetch(params) {
  return request('/api/childrenAccount/fetch', {
    method: 'POST',
    body: params,
  });
}

/!**
 * 新增数据
 * @param {
 * username: string 用户名
 * department: number 所属部门
 * }
 * @return {
 * flag: boolean 成功 or 失败
 * }
 *!/
export async function add(params) {
  return request('/api/childrenAccount/add', {
    method: 'POST',
    body: params,
  });
}

/!**
 * 编辑数据
 * @param {
 * id: number 主键
 * username: string 用户名
 * name: string 姓名
 * department: string 部门
 * departmentId: number 部门主键
 * phone: string 手机
 * email: string 邮箱
 * lock: boolean 是否锁定
 * }
 * @return {
 * flag: boolean 成功 or 失败
 * }
 *!/
export async function edit(params) {
  return request('/api/childrenAccount/edit', {
    method: 'POST',
    body: params,
  });
}

/!**
 * 重置密码
 * @param {
 * id: number 主键
 * password: number 密码发送方式
 * }
 * @return {
 * flag: boolean 成功 or 失败
 * }
 *!/
export async function init(params) {
  return request('/api/childrenAccount/init', {
    method: 'POST',
    body: params,
  });
}

/!**
 * 锁定/解锁
 * @param {
 * id: number 主键
 * lock: boolean 锁定/解锁
 * }
 * @return {
 * flag: boolean 成功 or 失败
 * }
 *!/
export async function editLock(params) {
  return request('/api/childrenAccount/editLock', {
    method: 'POST',
    body: params,
  });
}

/!**
 * 删除数据
 * @param {
 * id: number 主键
 * }
 * @return {
 * flag: boolean 成功 or 失败
 * }
 *!/
export async function remove(params) {
  return request('/api/childrenAccount/remove', {
    method: 'POST',
    body: params,
  });
}

/!**
 * 查看角色
 * @param {
 * id: number 主键
 * }
 * @return {
 * roleData: array 可分配角色
 * targetKeys: array 已分配角色
 * }
 *!/
export async function getRoles(params) {
  return request('/api/childrenAccount/getRoles', {
    method: 'POST',
    body: params,
  });
}

/!**
 * 分配角色
 * @param {
 * id: number 主键
 * roles: array 分配角色
 * }
 * @return {
 * flag: boolean 成功 or 失败
 * }
 *!/
export async function setRoles(params) {
  return request('/api/childrenAccount/setRoles', {
    method: 'POST',
    body: params,
  });
}
*/
