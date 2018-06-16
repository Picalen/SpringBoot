import { routerRedux } from 'dva/router';
import cookie from 'js-cookie';
import { message } from 'antd';
import * as Login from '../../services/User/Login';

export default {
  namespace: 'login',

  state: {
    flag: false,
    info: '',
    data: {
      type: '',
    },
    other: {
      submitting: false,
    },
  },

  effects: {
    * accountSubmit({ payload }, { call, put }) {
      yield put({
        type: 'loading',
        payload: { submitting: true },
      });
      const response = yield call(Login.accountLogin, payload);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { submitting: false },
      });
    },
    * mobileSubmit(_, { call, put }) {
      yield put({
        type: 'loading',
        payload: { submitting: true },
      });
      const response = yield call(Login.mobileLogin);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { submitting: false },
      });
    },
    * logout(_, { call, put }) {
      yield call(Login.logout);
      yield put({
        type: 'clear',
      });
      cookie.remove('jdBjId');
      cookie.remove('aMjedNuS');
      yield put(routerRedux.push('/user/login'));
    },
  },

  reducers: {
    loading(state, { payload }) {
      const resolve = { ...state };
      resolve.other = { ...resolve.other, ...payload };
      return {
        ...resolve,
      };
    },
    save(state, { payload }) {
      const { flag, info, data } = payload;
      if (data) {
        const resolve = { ...state };
        resolve.flag = flag;
        resolve.info = info;
        resolve.data = { ...resolve.data, ...data };
        return {
          ...resolve,
        };
      } else {
        return {
          ...state,
          flag,
          info,
        };
      }
    },
    clear() {
      return {
        flag: false,
        info: '',
        data: {
          type: '',
        },
        other: {
          submitting: false,
        },
      };
    },
  },

  subscriptions: {
    setup({ dispatch, history }) {
      return history.listen(({ pathname }) => {
        if (cookie.get('jdBjId')) { // 已登录
          if (pathname === '/user/login' || pathname === '/') {
            dispatch(routerRedux.push('/index'));
          }
        } else { // 未登录
          if (pathname === '/') {
            dispatch(routerRedux.push('/user/login'));
          } else if (pathname !== '/user/login') {
            message.error('尚未登录，请先登录');
            dispatch(routerRedux.push('/user/login'));
          }
        }
      });
    },
  },
};
