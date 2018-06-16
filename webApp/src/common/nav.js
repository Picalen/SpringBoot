import BasicLayout from '../layouts/BasicLayout';
import UserLayout from '../layouts/UserLayout';

import Home from '../routes/Home/Home';

import Exception403 from '../routes/Exception/403';
import Exception404 from '../routes/Exception/404';
import Exception500 from '../routes/Exception/500';

import Login from '../routes/User/Login/Login';
import Register from '../routes/User/Register/Register';
import RegisterResult from '../routes/User/RegisterResult/RegisterResult';


import ChildrenAccount from '../routes/Account/ChildrenAccount/ChildrenAccount';
import RolePermission from '../routes/Account/RolePermission/RolePermission';
import DepartmentManagement from '../routes/Account/DepartmentManagement/DepartmentManagement';



const data = [{
  component: BasicLayout,
  layout: 'BasicLayout',
  name: '', // for breadcrumb
  path: '',
  children: [{
    name: '首页',
    path: 'index',
    icon: 'desktop',
    component: Home,
  },   {
    name: '账户管理',
    path: 'account',
    icon: 'profile',
    children: [{
      name: '子账户管理',
      path: 'children-account',
      component: ChildrenAccount,
    }, {
      name: '角色与权限管理',
      path: 'role-permission',
      component: RolePermission,
    }, {
      name: '部门管理',
      path: 'department-management',
      component: DepartmentManagement,
    }],
  },   {
    name: '异常',
    path: 'exception',
    icon: 'warning',
    children: [{
      name: '403',
      path: '403',
      component: Exception403,
    }, {
      name: '404',
      path: '404',
      component: Exception404,
    }, {
      name: '500',
      path: '500',
      component: Exception500,
    }],
  }],
}, {
  component: UserLayout,
  layout: 'UserLayout',
  children: [{
    name: '帐户',
    icon: 'user',
    path: 'user',
    children: [{
      name: '登录',
      path: 'login',
      component: Login,
    }, {
      name: '注册',
      path: 'register',
      component: Register,
    }, {
      name: '注册结果',
      path: 'register-result',
      component: RegisterResult,
    }],
  }],
}];

export function getNavData() {
  return data;
}

export default data;
