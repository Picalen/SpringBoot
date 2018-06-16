import * as ChildrenAccount from '../../services/Account/ChildrenAccount';

export default {
  namespace: 'childrenAccount',

  state: {
    flag: false,
    info: '',
    data: {
      departments: [],
      data: [],
    },
    other: {
      tableLoading: false,
      addLoading: false,
      editLoading: false,
      removeLoading: false,
      initLoading: false,
      roleSpinning: false,
      roleLoading: false,
    },
  },

  effects: {
    * fetch({ payload }, { call, put }) {
      yield put({
        type: 'loading',
        payload: { tableLoading: true },
      });
      const response = yield call(ChildrenAccount.fetch, payload);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { tableLoading: false },
      });
    },

    * add({ payload }, { call, put }) {
      yield put({
        type: 'loading',
        payload: { addLoading: true },
      });
      const response = yield call(ChildrenAccount.add, payload);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { addLoading: false },
      });
    },

    * edit({ payload }, { call, put }) {
      yield put({
        type: 'loading',
        payload: { editLoading: true },
      });
      const response = yield call(ChildrenAccount.edit, payload);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { editLoading: false },
      });
    },

    * init({ payload }, { call, put }) {
      yield put({
        type: 'loading',
        payload: { initLoading: true },
      });
      const response = yield call(ChildrenAccount.init, payload);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { initLoading: false },
      });
    },

    * remove({ payload }, { call, put }) {
      yield put({
        type: 'loading',
        payload: { removeLoading: true },
      });
      const response = yield call(ChildrenAccount.remove, payload);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { removeLoading: false },
      });
    },

    * getRoles({ payload }, { call, put }) {
      yield put({
        type: 'loading',
        payload: { roleSpinning: true },
      });
      const response = yield call(ChildrenAccount.getRoles, payload);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { roleSpinning: false },
      });
    },

    * setRoles({ payload }, { call, put }) {
      yield put({
        type: 'loading',
        payload: { roleLoading: true },
      });
      const response = yield call(ChildrenAccount.setRoles, payload);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { roleLoading: false },
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
          departments: [],
          data: [],
        },
        other: {
          tableLoading: false,
          addLoading: false,
          editLoading: false,
          removeLoading: false,
          initLoading: false,
          roleSpinning: false,
          roleLoading: false,
        },
      };
    },
  },
};
