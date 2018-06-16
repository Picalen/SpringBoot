import React, { PureComponent } from 'react';
import { connect } from 'dva';
import { Button, Card, Divider, Form, Input, message, Modal, Popconfirm, Radio, Select, Spin, Table, Transfer } from 'antd';

import Lock from './components/Lock';

const EditableCell = ({ editable, value, onChange, component, departments, target }) => (
  <div>
    {
      editable
        ?
        component === 'select'
        ?
          <Select
            placeholder="请选择所属部门"
            style={{ width: '100%' }}
            onChange={onChange}
            defaultValue={target}
          >
            {
              departments.map(({ id, department }) => (
                <Select.Option key={id}>{department}</Select.Option>
              ))
            }
          </Select>
          :
          <Input style={{ margin: '-5px 0' }} value={value} onChange={e => onChange(e.target.value)} />
        :
          value
    }
  </div>
);

@connect(state => ({
  childrenAccount: state.childrenAccount,
}))
@Form.create()
export default class ChildrenAccount extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
      // 添加窗口--显示/隐藏
      addVisible: false,
      // 重置密码窗口--显示/隐藏
      initId: -1,
      initVisible: false,
      // 角色窗口--显示/隐藏
      roleVisible: false,
      roleId: -1,
      roleData: [],
      targetKeys: [],
    };
    this.cacheData = [];
  }

  componentDidMount() {
    this.fetch();
  }

  componentWillUnmount() {
    this.props.dispatch({
      type: 'childrenAccount/clear',
    });
  }

  onRemove = (id) => {
    this.props.dispatch({
      type: 'childrenAccount/remove',
      payload: { id },
    }).then(() => {
      const { childrenAccount: { flag, info } } = this.props;
      if (flag) {
        message.success(info);
        const data = [...this.state.data];
        const list = data.filter(item => id !== item.id);
        this.cacheData = list;
        this.setState({ data: list });
      } else {
        message.error(info);
      }
    });
  }

  onChange = (id, lock) => {
    const data = [...this.state.data];
    const target = data.filter(item => id === item.id)[0];
    target.lock = lock;
    this.setState({ data });
  }

  onClickAdd = () => {
    this.setState({ addVisible: true });
  }

  onClickEdit =(initId) => {
    this.setState({ initId, initVisible: true });
  }

  onClickRole = (roleId) => {
    this.setState({ roleVisible: true });
    this.props.dispatch({
      type: 'childrenAccount/getRoles',
      payload: { id: roleId },
    }).then(() => {
      const { childrenAccount: { flag, info, data: { roleData, targetKeys } } } = this.props;
      if (flag) {
        this.setState({ roleId, roleData, targetKeys });
      } else {
        message.error(info);
      }
    });
  }

  fetch = () => {
    this.props.dispatch({
      type: 'childrenAccount/fetch',
    }).then(() => {
      const { childrenAccount: { flag, info, data: { data } } } = this.props;
      if (flag) {
        this.cacheData = data.map(item => ({ ...item }));
        this.setState({ data });
      } else {
        message.error(info);
      }
    });
  }

  save(key) {
    const data = [...this.state.data];
    const target = data.filter(item => key === item.id)[0];
    let bool = false;
    Object.keys(target).forEach((item) => {
      if (target[item] === '') {
        bool = true;
      }
    });
    if (bool) {
      message.warning('参数不能为空');
      return;
    }
    if (target) {
      const { id, name, username, phone, email, departmentId } = target;
      this.props.dispatch({
        type: 'childrenAccount/edit',
        payload: { id, name, username, phone, email, departmentId },
      }).then(() => {
        const { childrenAccount: { flag, info } } = this.props;
        if (flag) {
          message.success(info);
          delete target.editable;
          this.cacheData = data.map(item => ({ ...item }));
          this.setState({ data });
        } else {
          message.error(info);
        }
      });
    }
  }

  cancel(key) {
    const data = [...this.state.data];
    const target = data.filter(item => key === item.id)[0];
    if (target) {
      Object.assign(target, this.cacheData.filter(item => key === item.id)[0]);
      delete target.editable;
      this.setState({ data });
    }
  }

  edit(key) {
    const data = [...this.state.data];
    const target = data.filter(item => key === item.id)[0];
    if (target) {
      target.editable = true;
      this.setState({ data });
    }
  }

  handleSelect(id, key, column) {
    const data = [...this.state.data];
    const target = data.filter(item => key === item.id)[0];
    if (target) {
      const { childrenAccount: { data: { departments } } } = this.props;
      const val = { ...departments.filter(item => id === `${item.id}`)[0] };
      target[column] = val.department;
      target.departmentId = val.id;
      this.setState({ data });
    }
  }

  handleChange(value, key, column) {
    const data = [...this.state.data];
    const target = data.filter(item => key === item.id)[0];
    if (target) {
      target[column] = value;
      this.setState({ data });
    }
  }

  handleOkAdd = () => {
    this.props.form.validateFields((err, value) => {
      if (!err) {
        console.log(value);
        this.props.dispatch({
          type: 'childrenAccount/add',
          payload: { ...value },
        }).then(() => {
          const { childrenAccount: { flag, info } } = this.props;
          if (flag) {
            message.success(info);
            this.setState({ addVisible: false });
            this.props.form.resetFields();
            this.fetch();
          } else {
            message.error(info);
          }
        });
      }
    });
  }

  handleCancelAdd = () => {
    this.setState({ addVisible: false });
    this.props.form.resetFields();
  }

  handleOkEditPassword = () => {
    this.props.form.validateFields((err, value) => {
      if (!err) {
        const { initId } = this.state;
        this.props.dispatch({
          type: 'childrenAccount/init',
          payload: { id: initId, ...value },
        }).then(() => {
          const { childrenAccount: { flag, info } } = this.props;
          if (flag) {
            message.success(info);
            this.setState({ initVisible: false });
            this.props.form.resetFields();
          } else {
            message.error(info);
          }
        });
      }
    });
  }

  handleCancelEditPassword = () => {
    this.setState({ initVisible: false });
    this.props.form.resetFields();
  }

  handleOkEditRoles = () => {
    const { roleId, targetKeys } = this.state;
    this.props.dispatch({
      type: 'childrenAccount/setRoles',
      payload: { id: roleId, roles: targetKeys },
    }).then(() => {
      const { childrenAccount: { flag, info } } = this.props;
      if (flag) {
        message.success(info);
        this.setState({ roleId: -1, roleData: [], targetKeys: [], roleVisible: false });
      } else {
        message.error(info);
      }
    });
  }

  handleCancelEditRoles = () => {
    this.setState({ roleId: -1, roleData: [], targetKeys: [], roleVisible: false });
  }

  handleChangeRoles = (targetKeys) => {
    this.setState({ targetKeys });
  }

  renderColumns(text, record, column, component = 'input') {
    const { childrenAccount: { data: { departments } } } = this.props;
    if (component === 'select') {
      const target = { ...departments.filter(item => text === item.department)[0] };
      return (
        <EditableCell
          editable={record.editable}
          component={component}
          departments={departments}
          value={text}
          target={target.department}
          onChange={value => this.handleSelect(value, record.id, column)}
        />
      );
    } else {
      return (
        <EditableCell
          editable={record.editable}
          value={text}
          onChange={value => this.handleChange(value, record.id, column)}
        />
      );
    }
  }

  render() {
    const {
      form: {
        getFieldDecorator,
      },
      childrenAccount: {
        data: {
          departments,
        },
        other: {
          tableLoading,
          addLoading,
          editLoading,
          removeLoading,
          initLoading,
          roleSpinning,
          roleLoading,
        },
      },
    } = this.props;
    const {
      data,
      roleData,
      targetKeys,
      addVisible,
      initVisible,
      roleVisible,
    } = this.state;

    const columns = [{
      title: '用户名',
      dataIndex: 'username',
      width: '15%',
      render: (text, record) => this.renderColumns(text, record, 'username'),
    }, {
      title: '姓名',
      dataIndex: 'name',
      width: '15%',
      render: (text, record) => this.renderColumns(text, record, 'name'),
    }, {
      title: '部门',
      dataIndex: 'department',
      width: '15%',
      render: (text, record) => this.renderColumns(text, record, 'department', 'select'),
    }, {
      title: '手机号',
      dataIndex: 'phone',
      width: '10%',
      render: (text, record) => this.renderColumns(text, record, 'phone'),
    }, {
      title: '邮箱',
      dataIndex: 'email',
      width: '15%',
      render: (text, record) => this.renderColumns(text, record, 'email'),
    }, {
      title: '锁定',
      dataIndex: 'lock',
      width: '5%',
      render: (text, record) => (
        <Lock
          id={record.id}
          check={text}
          changeLock={(id, lock) => this.onChange(id, lock)}
        />
      ),
    }, {
      title: '操作',
      dataIndex: 'operation',
      width: '25%',
      render: (text, record) => {
        const { editable } = record;
        return (
          <div className="editable-row-operations">
            {
              editable ?
                <Spin spinning={editLoading}>
                  <a onClick={() => this.save(record.id)}>保存</a>
                  <Divider type="vertical" />
                  <Popconfirm title="确定取消?" onConfirm={() => this.cancel(record.id)}>
                    <a>取消</a>
                  </Popconfirm>
                </Spin>
                :
                <Spin spinning={removeLoading}>
                  <Popconfirm title="确定删除?" onConfirm={() => this.onRemove(record.id)}>
                    <a>删除</a>
                  </Popconfirm>
                  <Divider type="vertical" />
                  <a onClick={() => this.edit(record.id)}>编辑</a>
                  <Divider type="vertical" />
                  <a onClick={() => this.onClickEdit(record.id)}>重置密码</a>
                  <Divider type="vertical" />
                  <a onClick={() => this.onClickRole(record.id)}>查看/分配角色</a>
                </Spin>
            }
          </div>
        );
      },
    }];

    const formItemLayout = {
      labelCol: { span: 4 },
      wrapperCol: { span: 20 },
    };

    const formItem = (
      <Form>
        {
         addVisible
           ?
             <div>
               <Form.Item {...formItemLayout} label="用户名" hasFeedback>
                 {getFieldDecorator('username', {
                   rules: [
                     { max: 100, message: '用户名长度不得超过100位' },
                     { required: true, message: '用户名不能为空' },
                     { whitespace: true, message: '用户名格式错误' },
                     ],
                 })(
                   <Input placeholder="请输入用户名" />
                 )}
               </Form.Item>
               <Form.Item {...formItemLayout} label="所属部门">
                 {getFieldDecorator('department', {
                   rules: [
                     { required: true, message: '所属部门不能为空' },
                   ],
                 })(
                   <Select placeholder="请选择" style={{ width: '100%' }}>
                     {
                       departments.map(({ id, department }) => (
                         <Select.Option key={id}>{department}</Select.Option>
                       ))
                     }
                   </Select>
                 )}
               </Form.Item>
               <Form.Item {...formItemLayout} label="姓名" hasFeedback>
                 {getFieldDecorator('name', {
                   rules: [
                     { max: 20, message: '姓名长度不得超过20位' },
                     { required: true, message: '姓名不能为空' },
                     { whitespace: true, message: '姓名格式错误' },
                   ],
                 })(
                   <Input placeholder="请输入姓名" />
                 )}
               </Form.Item>
               <Form.Item {...formItemLayout} label="手机号" hasFeedback>
                 {getFieldDecorator('phone', {
                   rules: [
                     { len: 11, message: '请输入正确的手机号' },
                     { required: true, message: '手机号不能为空' },
                     { whitespace: true, message: '手机号格式错误' },
                   ],
                 })(
                   <Input placeholder="请输入手机号" />
                 )}
               </Form.Item>
               <Form.Item {...formItemLayout} label="邮箱" hasFeedback>
                 {getFieldDecorator('email', {
                   rules: [
                     { type: 'email', message: '请输入正确的邮箱地址' },
                     { required: true, message: '邮箱不能为空' },
                     { whitespace: true, message: '邮箱格式错误' },
                   ],
                 })(
                   <Input placeholder="请输入邮箱" />
                 )}
               </Form.Item>
               <Form.Item {...formItemLayout} label="密码">
                 {getFieldDecorator('password', {
                   initialValue: 1,
                   rules: [{
                     required: true, message: '密码不能为空',
                   }],
                 })(
                   <Radio.Group>
                     <Radio value={1}>发送至邮箱</Radio>
                     <Radio value={2}>发送至手机</Radio>
                   </Radio.Group>
                 )}
               </Form.Item>
             </div>
           :
             <Form.Item {...formItemLayout} label="密码">
               {getFieldDecorator('password', {
                 initialValue: 1,
                 rules: [{
                   required: true, message: '密码不能为空',
                 }],
               })(
                 <Radio.Group>
                   <Radio value={1}>发送至邮箱</Radio>
                   <Radio value={2}>发送至手机</Radio>
                 </Radio.Group>
               )}
             </Form.Item>
        }
      </Form>
    );

    return (
      <Card bordered={false}>
        <Button
          type="dashed"
          icon="plus"
          style={{ width: '100%', marginBottom: 24 }}
          onClick={this.onClickAdd}
        >
          添加
        </Button>

        <Table
          rowKey={record => record.id}
          loading={tableLoading}
          columns={columns}
          dataSource={data}
        />

        <Modal
          title="添加"
          confirmLoading={addLoading}
          visible={addVisible}
          onOk={this.handleOkAdd}
          onCancel={this.handleCancelAdd}
        >
          {formItem}
        </Modal>

        <Modal
          title="重置密码"
          confirmLoading={initLoading}
          visible={initVisible}
          onOk={this.handleOkEditPassword}
          onCancel={this.handleCancelEditPassword}
        >
          {formItem}
        </Modal>

        <Modal
          title="查看/分配角色"
          visible={roleVisible}
          confirmLoading={roleLoading}
          onOk={this.handleOkEditRoles}
          onCancel={this.handleCancelEditRoles}
          width={700}
        >
          <Spin spinning={roleSpinning}>
            <Transfer
              showSearch
              searchPlaceholder="请输入搜索内容"
              listStyle={{ width: 300, height: 500 }}
              render={item => item.title}
              titles={['可分配角色', '已分配角色']}
              dataSource={roleData}
              targetKeys={targetKeys}
              onChange={this.handleChangeRoles}
            />
          </Spin>
        </Modal>
      </Card>
    );
  }
}
