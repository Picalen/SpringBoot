import * as RolePermission from '../../services/Account/RolePermission';

export default {
  namespace: 'rolePermission',

  state: {
    flag: false,
    info: '',
    data: {
      data: [],
      treeData: [],
      checkedKeys: [],
    },
    other: {
      tableLoading: false,
      addLoading: false,
      editLoading: false,
      treeLoading: false,
      roleLoading: false,
    },
  },

  effects: {
    * fetch(_, { call, put }) {
      yield put({
        type: 'loading',
        payload: { tableLoading: true },
      });
      const response = yield call(RolePermission.fetch);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { tableLoading: false },
      });
    },

    * query({ payload }, { call, put }) {
      yield put({
        type: 'loading',
        payload: { tableLoading: true },
      });
      const response = yield call(RolePermission.query, payload);
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
      const response = yield call(RolePermission.add, payload);
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
      const response = yield call(RolePermission.edit, payload);
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
      const response = yield call(RolePermission.remove, payload);
      yield put({
        type: 'save',
        payload: response,
      });
    },

    * getPermission({ payload }, { call, put }) {
      yield put({
        type: 'loading',
        payload: { treeLoading: true },
      });
      const response = yield call(RolePermission.getPermission, payload);
      yield put({
        type: 'save',
        payload: response,
      });
      yield put({
        type: 'loading',
        payload: { treeLoading: false },
      });
    },

    * editRole({ payload }, { call, put }) {
      yield put({
        type: 'loading',
        payload: { roleLoading: true },
      });
      const response = yield call(RolePermission.editRole, payload);
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
          data: [],
          treeData: [],
          checkedKeys: [],
        },
        other: {
          tableLoading: false,
          addLoading: false,
          editLoading: false,
          treeLoading: false,
          roleLoading: false,
        },
      };
    },
  },
};
