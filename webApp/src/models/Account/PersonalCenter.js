import * as PersonalCenter from '../../services/Account/PersonalCenter';

export default{
  namespace: 'personalCenter',

  state: {
    flag: false,
    info: '',
    data: {
      baseInfo: {},
    },
    other: {
      loading: false,
      userInfoLoading: false,
      passwordLoading: false,
    },
  },

  effects: {
    * fetch(_, { call, put }) {
      yield put({
        type: 'loading',
        payload: { loading: true },
      });
      const response = yield call(PersonalCenter.fetch);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { loading: false },
      });
    },

    * updateUserInfo({ payload }, { call, put }) {
      yield put({
        type: 'loading',
        payload: { userInfoLoading: true },
      });
      const response = yield call(PersonalCenter.updateUserInfo, payload);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { userInfoLoading: false },
      });
    },

    * updatePassword({ payload }, { call, put }) {
      yield put({
        type: 'loading',
        payload: { passwordLoading: true },
      });
      const response = yield call(PersonalCenter.updatePassword, payload);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { passwordLoading: false },
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
        info: '',
        data: {
          baseInfo: {},
        },
        other: {
          loading: false,
          userInfoLoading: false,
          passwordLoading: false,
        },
      };
    },
  },
};
