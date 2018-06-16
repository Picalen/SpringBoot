import * as PlatDistribution from '../../services/Account/PlatDistribution';

export default {
  namespace: 'platDistribution',

  state: {
    flag: false,
    info: [],
    data: {
      data: [],
      roleData: [],
      targetKeys: [],
    },
    other: {
      loading: false,
      spinning: false,
      confirmLoading: false,
    },
  },

  effects: {
    * fetch(_, { call, put }) {
      yield put({
        type: 'loading',
        payload: { loading: true },
      });
      const response = yield call(PlatDistribution.fetch);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { loading: false },
      });
    },

    * query({ payload }, { call, put }) {
      yield put({
        type: 'loading',
        payload: { loading: true },
      });
      const response = yield call(PlatDistribution.query, payload);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { loading: false },
      });
    },

    * getRoles({ payload }, { call, put }) {
      yield put({
        type: 'loading',
        payload: { spinning: true },
      });
      const response = yield call(PlatDistribution.getRoles, payload);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { spinning: false },
      });
    },

    * setRoles({ payload }, { call, put }) {
      yield put({
        type: 'loading',
        payload: { confirmLoading: true },
      });
      const response = yield call(PlatDistribution.setRoles, payload);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { confirmLoading: false },
      });
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
        info: [],
        data: {
          data: [],
          roleData: [],
          targetKeys: [],
        },
        other: {
          loading: false,
          spinning: false,
          confirmLoading: false,
        },
      };
    },
  },
};
