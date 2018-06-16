import mockjs from 'mockjs';
import { getRule, postRule } from './mock/rule';
import {getActivities, getFakeList, getNotice } from './mock/api';
import { getFakeChartData } from './mock/chart';
import { getProfileAdvancedData, getProfileBasicData } from './mock/profile';
import { getNotices } from './mock/notices';
import { delay } from 'roadhog-api-doc';

import MonitorSet from './mock/MonitorSet/MonitorSet';

import Home from './mock/Home/Home';
import Deposit from './mock/Home/Deposit';
import Month from './mock/Home/Month';
import Year from './mock/Home/Year';

import Report from './mock/Statistical/Report';

import PlatformSummarize from './mock/PlatformDataAnalysis/PlatformSummarize/PlatformSummarize';
import PlatformSummarize2 from './mock/PlatformDataAnalysis/PlatformSummarize/PlatformSummarize2';

import EnterpriseSummarize from './mock/PlatformDataAnalysis/EnterpriseSummarize/EnterpriseSummarize';
import EnterpriseSummarize2 from './mock/PlatformDataAnalysis/EnterpriseSummarize/EnterpriseSummarize2';

import BankOrganization from './mock/BankAdministration/BankOrganization';
import BankAccount from './mock/BankAdministration/BankAccount';
import BankAccount2 from './mock/BankAdministration/BankAccount2';
import BankAccount3 from './mock/BankAdministration/BankAccount3';

import PlatFormConfigure from './mock/PlatForm/PlatFormConfigure';

import DepartmentManagement from './mock/Account/DepartmentManagement';

import ChildrenAccount from './mock/Account/ChildrenAccount';
import ChildrenAccount2 from './mock/Account/ChildrenAccount2';
import RolePermission from './mock/Account/RolePermission';
import RolePermission2 from './mock/Account/RolePermission2';
import PersonalCenter from './mock/Account/PersonalCenter/PersonalCenter';
import PlatDistribution from './mock/Account/PlatDistribution';
import PlatDistribution2 from './mock/Account/PlatDistribution2';

import RiskGeneralizations from './mock/RiskWarning/RiskGeneralizations';
import RiskGeneralizations2 from './mock/RiskWarning/RiskGeneralizations2';
import RiskGeneralizations3 from './mock/RiskWarning/RiskGeneralizations3';
import RiskDynamic from './mock/RiskWarning/RiskDynamic';
import WarningSet from './mock/RiskWarning/WarningSet';

import PublicOpinionDynamic from './mock/PublicOpinionDynamic/PublicOpinionDynamic';
import PublicOpinionDynamic2 from './mock/PublicOpinionDynamic/PublicOpinionDynamic2';
import PublicOpinionDynamic3 from './mock/PublicOpinionDynamic/PublicOpinionDynamic3';

// 是否禁用代理
const noProxy = process.env.NO_PROXY === 'true';

// 代码中会兼容本地 service mock 以及部署站点的静态数据
const proxy = {
  // 支持值为 Object 和 Array
  'GET /api/currentUser': {
    $desc: '获取当前用户接口',
    $params: {
      pageSize: {
        desc: '分页',
        exp: 2,
      },
    },
    $body: {
      name: 'Serati Ma',
      avatar: 'https://gw.alipayobjects.com/zos/rmsportal/dRFVcIqZOYPcSNrlJsqQ.png',
      userid: '00000001',
      notifyCount: 12,
    },
  },
  // GET POST 可省略
  'GET /api/users': [{
    key: '1',
    name: 'John Brown',
    age: 32,
    address: 'New York No. 1 Lake Park',
  }, {
    key: '2',
    name: 'Jim Green',
    age: 42,
    address: 'London No. 1 Lake Park',
  }, {
    key: '3',
    name: 'Joe Black',
    age: 32,
    address: 'Sidney No. 1 Lake Park',
  }],
  'GET /api/project/notice': getNotice,
  'GET /api/activities': getActivities,
  'GET /api/rule': getRule,
  'POST /api/rule': {
    $params: {
      pageSize: {
        desc: '分页',
        exp: 2,
      },
    },
    $body: postRule,
  },
  'POST /api/forms': (req, res) => {
    res.send('Ok');
  },
  'GET /api/tags': mockjs.mock({
    'list|100': [{ name: '@city', 'value|1-100': 150, 'type|0-2': 1 }],
  }),
  'GET /api/fake_list': getFakeList,
  'GET /api/fake_chart_data': getFakeChartData,
  'GET /api/profile/basic': getProfileBasicData,
  'GET /api/profile/advanced': getProfileAdvancedData,
  'POST /api/login/account': (req, res) => {
    res.send({ data: { type: 'account' }, flag: true, info: '登录info' });
  },
  'POST /api/login/mobile': (req, res) => {
    res.send({ status: 'ok', type: 'mobile' });
  },
  'POST /api/register': (req, res) => {
    res.send({ status: 'ok' });
  },
  'GET /api/notices': getNotices,
  'POST /api/home/fetch': { data: { ...Home }, flag: true, info: '模拟info成功' },
  'POST /api/home/deposit': { data: { ...Deposit }, flag: true, info: '模拟info成功' },
  'POST /api/home/month': { data: { ...Month }, flag: true, info: '模拟info成功' },
  'POST /api/home/year': { data: { ...Year }, flag: true, info: '模拟info成功' },
  'POST /api/report/fetch': { data: { ...Report }, flag: true, info: '模拟info成功' },
  'POST /api/pushSet/fetch': (request, response) => {
    response.send({
      flag: true,
      info: '模拟info',
      data: {
        pushLock: true,
        pushMode: [0, 2],
        pushModule: [0, 3, 5, 7, 9],
        pushTime: new Date(),
        pushRealTime: true,
        weekendPush: true,
      } });
  },
  'POST /api/pushSet/edit': (request, response) => {
    response.send({ flag: true, info: '模拟info1' });
  },
  // -----------------传说中的分割线-----------------
  'POST /api/bankOrganization/fetch': { data: { ...BankOrganization }, flag: true, info: '模拟info' },
  'POST /api/bankOrganization/add': (request, response) => {
    response.send({ flag: true, info: '模拟info2' });
  },
  'POST /api/bankOrganization/edit': (request, response) => {
    response.send({ flag: true, info: '模拟info3' });
  },
  'POST /api/bankOrganization/remove': (request, response) => {
    response.send({ flag: true, info: '模拟info4' });
  },
  // -----------------传说中的分割线-----------------
  'POST /api/bankAccount/fetch': { data: { ...BankAccount }, flag: true, info: '模拟info' },
  'POST /api/bankAccount/query': { data: { ...BankAccount2 }, flag: true, info: '模拟info' },
  'POST /api/bankAccount/add': (request, response) => {
    response.send({ flag: true, info: '模拟info5' });
  },
  'POST /api/bankAccount/edit': (request, response) => {
    response.send({ flag: true, info: '模拟info6' });
  },
  'POST /api/bankAccount/remove': (request, response) => {
    response.send({ flag: true, info: '模拟info7' });
  },
  'POST /api/bankAccount/editLock': (request, response) => {
    response.send({ flag: true, info: '模拟info8' });
  },
  'POST /api/bankAccount/getRoles': (request, response) => {
    response.send({ data: { ...BankAccount3 }, flag: true, info: '模拟info' });
  },
  'POST /api/bankAccount/setRoles': (request, response) => {
    response.send({ flag: true, info: '模拟info9' });
  },
  'POST /api/bankAccount/init': (request, response) => {
    response.send({ flag: true, info: '模拟info10' });
  },
  // -----------------传说中的分割线-----------------
  'POST /api/platFormConfigure/fetch': { data: { ...PlatFormConfigure }, flag: true, info: '模拟info' },
  'POST /api/platFormConfigure/query': { data: { ...PlatFormConfigure }, flag: true, info: '模拟info' },
  'POST /api/platFormConfigure/edit': (request, response) => {
    response.send({ flag: true, info: '模拟info' });
  },
  // -----------------传说中的分割线-----------------
  'POST /api/platformSummarize/fetch': { data: { ...PlatformSummarize }, flag: true, info: '模拟info' },
  'POST /api/platformSummarize/query': { data: { ...PlatformSummarize2 }, flag: true, info: '模拟info' },
  'POST /api/platformSummarize/search': { data: { ...PlatformSummarize }, flag: true, info: '模拟info' },
  // -----------------传说中的分割线-----------------
  'POST /api/enterpriseSummarize/fetch': { data: { ...EnterpriseSummarize }, flag: true, info: '模拟info' },
  'POST /api/enterpriseSummarize/query': { data: { ...EnterpriseSummarize2 }, flag: true, info: '模拟info' },
  'POST /api/enterpriseSummarize/search': { data: { ...EnterpriseSummarize }, flag: true, info: '模拟info' },
  // -----------------传说中的分割线----------------
  'POST /api/departmentManagement/fetch': { data: { ...DepartmentManagement }, flag: true, info: '模拟info' },
  'POST /api/departmentManagement/add': (request, response) => {
    response.send({ flag: true, info: '模拟info11' });
  },
  'POST /api/departmentManagement/edit': (request, response) => {
    response.send({ flag: true, info: '模拟info12' });
  },
  'POST /api/departmentManagement/remove': (request, response) => {
    response.send({ flag: true, info: '模拟info13' });
  },
  // -----------------传说中的分割线-----------------
  'POST /api/personalCenter/fetch': (request, response) => {
    response.send({ data: { ...PersonalCenter }, flag: true, info: '模拟info' });
  },
  'POST /api/personalCenter/updateUserInfo': (request, response) => {
    response.send({ flag: true, info: '模拟info' });
  },
  'POST /api/personalCenter/updatePassword': (request, response) => {
    response.send({ flag: true, info: '模拟info' });
  },
  // -----------------传说中的分割线-----------------
  'POST /api/childrenAccount/fetch': { data: { ...ChildrenAccount }, flag: true, info: '模拟info' },
  'POST /api/childrenAccount/add': (request, response) => {
    response.send({ flag: true, info: '模拟info14' });
  },
  'POST /api/childrenAccount/edit': (request, response) => {
    response.send({ flag: true, info: '模拟info15' });
  },
  'POST /api/childrenAccount/init': (request, response) => {
    response.send({ flag: true, info: '模拟info16' });
  },
  'POST /api/childrenAccount/editLock': (request, response) => {
    response.send({ flag: true, info: '模拟info17' });
  },
  'POST /api/childrenAccount/remove': (request, response) => {
    response.send({ flag: true, info: '模拟info18' });
  },
  'POST /api/childrenAccount/getRoles': (request, response) => {
    response.send({ data: { ...ChildrenAccount2 }, flag: true, info: '模拟info' });
  },
  'POST /api/childrenAccount/setRoles': (request, response) => {
    response.send({ flag: true, info: '模拟info19' });
  },
  // -----------------传说中的分割线-----------------
  'POST /api/riskGeneralizations/fetch': { data: { ...RiskGeneralizations }, flag: true, info: '模拟info' },
  'POST /api/riskGeneralizations/query': { data: { ...RiskGeneralizations2 }, flag: true, info: '模拟info' },
  'POST /api/riskGeneralizations/timeLine': { data: { ...RiskGeneralizations3 }, flag: true, info: '模拟info' },
  // -----------------传说中的分割线-----------------
  'POST /api/riskDynamic/fetch': { data: { ...RiskDynamic }, flag: true, info: '模拟info' },
  'POST /api/riskDynamic/query': { data: { ...RiskDynamic }, flag: true, info: '模拟info' },
  // -----------------传说中的分割线-----------------
  'POST /api/warningSet/fetch': { data: { ...WarningSet }, flag: true, info: '模拟info' },
  'POST /api/warningSet/edit': (request, response) => {
    response.send({ flag: true, info: '模拟info20' });
  },
  'POST /api/warningSet/switchEdit': (request, response) => {
    response.send({ flag: true, info: '模拟info21' });
  },
  // -----------------传说中的分割线-----------------
  'POST /api/warningPushSet/fetch': (request, response) => {
    response.send({
      flag: true,
      info: '模拟info',
      data: {
        pushLock: true,
        pushMode: [0, 2],
        pushTime: new Date(),
        pushRealTime: true,
        weekendPush: true,
      } });
  },
  'POST /api/warningPushSet/edit': (request, response) => {
    response.send({ flag: true, info: '模拟info22' });
  },
  // -----------------传说中的分割线-----------------
  'POST /api/publicOpinionPushSet/fetch': (request, response) => {
    response.send({
      flag: true,
      info: '模拟info',
      data: {
        pushLock: true,
        pushMode: [0, 2],
        pushTime: new Date(),
        pushRealTime: true,
        weekendPush: true,
      } });
  },
  'POST /api/publicOpinionPushSet/edit': (request, response) => {
    response.send({ flag: true, info: '模拟info23' });
  },
  // -----------------传说中的分割线-----------------
  'POST /api/RolePermission/fetch': { data: { ...RolePermission }, flag: true, info: '模拟info' },
  'POST /api/RolePermission/add': { flag: true, info: '模拟info' },
  'POST /api/RolePermission/edit': { flag: true, info: '模拟info' },
  'POST /api/RolePermission/remove': { flag: true, info: '模拟info' },
  'POST /api/RolePermission/getPermission': { data: { ...RolePermission2 }, flag: true, info: '模拟info' },
  // -----------------传说中的分割线-----------------
  'POST /api/publicOpinionDynamic/fetch': { data: { ...PublicOpinionDynamic }, flag: true, info: '模拟info' },
  'POST /api/publicOpinionDynamic/query': { data: { ...PublicOpinionDynamic2 }, flag: true, info: '模拟info' },
  'POST /api/publicOpinionDynamic/showMore': { data: { ...PublicOpinionDynamic3 }, flag: true, info: '模拟info' },
  // -----------------传说中的分割线-----------------
  'POST /api/monitorSet/fetch': { data: { ...MonitorSet }, flag: true, info: '模拟info' },
  'POST /api/monitorSet/editLock': { flag: true, info: '模拟info' },
  // -----------------传说中的分割线-----------------
  'POST /api/platDistribution/fetch': { data: { ...PlatDistribution }, flag: true, info: '模拟info' },
  'POST /api/platDistribution/getRoles': { data: { ...PlatDistribution2 }, flag: true, info: '模拟info' },
  'POST /api/platDistribution/setRoles': { flag: true, info: '模拟info' },
};

export default noProxy ? {} : delay(proxy, 1000);
