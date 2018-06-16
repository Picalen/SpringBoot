import * as DepartmentManagement from '../../services/Account/DepartmentManagement';

export default {
  namespace: 'departmentManagement',

  state: {
    flag: false,
    info: '',
    data: {
      data: [],
      security: false,
    },
    other: {
      loading: false,
      addLoading: false,
      editLoading: false,
    },
  },

  effects: {
    * fetch(_, { call, put }) {
      yield put({
        type: 'loading',
        payload: { loading: true },
      });
      const response = yield call(DepartmentManagement.fetch);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { loading: false },
      });
    },

    * add({ payload }, { call, put }) {
      yield put({
        type: 'loading',
        payload: { addLoading: true },
      });
      const response = yield call(DepartmentManagement.add, payload);
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
      const response = yield call(DepartmentManagement.edit, payload);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { editLoading: false },
      });
    },

    * remove({ payload }, { call, put }) {
      yield put({
        type: 'loading',
        payload: { removeLoading: true },
      });
      const response = yield call(DepartmentManagement.remove, payload);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { removeLoading: false },
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
          data: [],
          security: false,
        },
        other: {
          loading: false,
          addLoading: false,
          editLoading: false,
        },
      };
    },
  },
};
