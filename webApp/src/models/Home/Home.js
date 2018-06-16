import * as Home from '../../services/Home/Home';

export default {
  namespace: 'home',

  state: {
    flag: false,
    info: [],
    data: {
      depositBalance: {},
      monthTransaction: {},
      yearTransaction: {},
    },
    other: {
      depositLoading: false,
      monthLoading: false,
      yearLoading: false,
    },
  },

  effects: {
    * fetch({ payload }, { call, put }) {
      yield put({
        type: 'loading',
        payload: { depositLoading: true, monthLoading: true, yearLoading: true },
      });
      const response = yield call(Home.fetch, payload);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { depositLoading: false, monthLoading: false, yearLoading: false },
      });
    },

    * deposit({ payload }, { call, put }) {
      yield put({
        type: 'loading',
        payload: { depositLoading: true },
      });
      const response = yield call(Home.deposit, payload);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { depositLoading: false },
      });
    },

    * month({ payload }, { call, put }) {
      yield put({
        type: 'loading',
        payload: { monthLoading: true },
      });
      const response = yield call(Home.month, payload);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { monthLoading: false },
      });
    },

    * year({ payload }, { call, put }) {
      yield put({
        type: 'loading',
        payload: { yearLoading: true },
      });
      const response = yield call(Home.year, payload);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { yearLoading: false },
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
          depositBalance: {},
          monthTransaction: {},
          yearTransaction: {},
        },
        other: {
          depositLoading: false,
          monthLoading: false,
          yearLoading: false,
        },
      };
    },
  },
};
