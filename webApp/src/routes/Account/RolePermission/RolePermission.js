import React, { PureComponent } from 'react';
import { connect } from 'dva';
import { Button, Card, Divider, Form, Input, message, Modal, Popconfirm, Spin, Table, Tree } from 'antd';

const EditableCell = ({ editable, value, onChange }) => (
  <div>
    {
      editable
        ?
          <Input value={value} onChange={e => onChange(e.target.value)} />
        :
          value
    }
  </div>
);

@connect(state => ({
  rolePermission: state.rolePermission,
}))
@Form.create()
export default class RolePermission extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
      id: undefined,
      checkedKeys: [],
      addVisible: false,
      permission: false,
    };
    this.cacheData = [];
  }

  componentDidMount() {
    this.props.dispatch({
      type: 'rolePermission/fetch',
    }).then(() => {
      const { rolePermission: { flag, info, data: { data } } } = this.props;
      if (flag) {
        this.cacheData = data.map(item => ({ ...item }));
        this.setState({ data });
      } else {
        message.error(info);
      }
    });
  }

  componentWillUnmount() {
    this.props.dispatch({
      type: 'rolePermission/clear',
    });
  }

  onClick = () => {
    this.setState({ addVisible: true });
  }

  onClickEdit = (id) => {
    this.setState({ id });
    this.props.dispatch({
      type: 'rolePermission/getPermission',
      payload: { id },
    }).then(() => {
      const { rolePermission: { flag, info, data: { checkedKeys } } } = this.props;
      if (flag) {
        this.setState({ checkedKeys, permission: true });
      } else {
        message.error(info);
      }
    });
  }

  onRemove = (id) => {
    Modal.confirm({
      title: '确定要删除这个角色?',
      content: '删除角色后无法撤销',
      okText: '确定',
      okType: 'danger',
      cancelText: '取消',
      onOk: () => {
        return new Promise((resolve, reject) => {
          this.props.dispatch({
            type: 'rolePermission/remove',
            payload: { id },
          }).then(() => {
            const { rolePermission: { flag, info } } = this.props;
            if (flag) {
              message.success(info);
              this.setState({ checkedKeys: [] });
              resolve();
              this.query();
            } else {
              message.error(info);
              reject();
            }
          });
        });
      },
    });
  }

  onCheck =(checkedKeys) => {
    this.setState({ checkedKeys });
  }

  onSelect =(selectKeys) => {
    if (selectKeys.length === 1) {
      const { checkedKeys } = this.state;
      const key = checkedKeys.filter(item => item === selectKeys[0]);
      const target = [];
      for (let i = 0; i < checkedKeys.length; i += 1) {
        target.push(checkedKeys[i]);
      }
      if (key.length === 0) {
        target.push(selectKeys[0]);
        this.setState({ checkedKeys: target });
      } else {
        for (let i = 0; i < target.length; i += 1) {
          if (selectKeys[0] === target[i]) {
            delete target[i];
            this.setState({ checkedKeys: target });
          }
        }
      }
    }
  }

  save = (key) => {
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
      const { id, name } = target;
      this.props.dispatch({
        type: 'rolePermission/edit',
        payload: { id, name },
      }).then(() => {
        const { rolePermission: { flag, info } } = this.props;
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

  edit = (id) => {
    const data = [...this.state.data];
    const target = data.filter(item => id === item.id)[0];
    if (target) {
      target.editable = true;
      this.setState({ data });
    }
  }

  cancel = (id) => {
    const data = [...this.state.data];
    const target = data.filter(item => id === item.id)[0];
    if (target) {
      Object.assign(target, this.cacheData.filter(item => id === item.id)[0]);
      delete target.editable;
      this.setState({ data });
    }
  }

  query = () => {
    this.props.dispatch({
      type: 'rolePermission/query',
    }).then(() => {
      const { rolePermission: { flag, info, data: { data } } } = this.props;
      if (flag) {
        this.cacheData = data.map(item => ({ ...item }));
        this.setState({ data });
      } else {
        message.error(info);
      }
    });
  }

  handleChange(value, id, column) {
    const data = [...this.state.data];
    const target = data.filter(item => id === item.id)[0];
    if (target) {
      target[column] = value;
      this.setState({ data });
    }
  }

  handleOkAdd = () => {
    this.props.form.validateFields((err, value) => {
      if (!err) {
        this.props.dispatch({
          type: 'rolePermission/add',
          payload: { ...value },
        }).then(() => {
          const { rolePermission: { flag, info } } = this.props;
          if (flag) {
            message.success(info);
            this.setState({ addVisible: false });
            this.props.form.resetFields();
            this.query();
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

  handleOkPermission = () => {
    const { id, checkedKeys } = this.state;
    this.setState({ permission: false });
    this.props.dispatch({
      type: 'rolePermission/editRole',
      payload: { id, checkedKeys },
    }).then(() => {
      const { rolePermission: { flag, info } } = this.props;
      if (flag) {
        this.setState({ checkedKeys: [] });
        message.success(info);
      } else {
        message.error(info);
      }
    });
  }

  handleCancelPermission = () => {
    this.setState({ permission: false });
  }

  renderColumns(text, record, columns) {
    return (
      <EditableCell
        editable={record.editable}
        value={text}
        onChange={value => this.handleChange(value, record.id, columns)}
      />
    );
  }

  renderTreeNodes = (data) => {
    if (data.length > 0) {
      return data.map((item) => {
        if (item.children) {
          return (
            <Tree.TreeNode title={item.title} key={item.key}>
              {this.renderTreeNodes(item.children)}
            </Tree.TreeNode>
          );
        }
        return <Tree.TreeNode {...item} />;
      });
    }
  }

  render() {
    const { data, checkedKeys, addVisible, permission } = this.state;
    const {
      form: {
        getFieldDecorator,
      },
      rolePermission: {
        data: {
          treeData,
        },
        other: {
          tableLoading,
          addLoading,
          editLoading,
          treeLoading,
          roleLoading,
        },
      },
    } = this.props;

    const columns = [{
      title: '角色名',
      dataIndex: 'name',
      width: '80%',
      render: (text, record) => this.renderColumns(text, record, 'name'),
    }, {
      title: '操作',
      dataIndex: 'operation',
      width: '20%',
      render: (text, record) => {
        const { editable } = record;
        return (
          <div className="editable-row-operations">
            {
              editable
                ?
                  <Spin spinning={editLoading}>
                    <a onClick={() => this.save(record.id)}>保存</a>
                    <Divider type="vertical" />
                    <Popconfirm title="确定取消?" onConfirm={() => this.cancel(record.id)}>
                      <a>取消</a>
                    </Popconfirm>
                  </Spin>
                :
                  <div>
                    <a onClick={() => this.onRemove(record.id)}>删除</a>
                    <Divider type="vertical" />
                    <a onClick={() => this.edit(record.id)}>编辑</a>
                    <Divider type="vertical" />
                    <a onClick={() => this.onClickEdit(record.id)}>查看/分配权限</a>
                  </div>
            }
          </div>
        );
      },
    }];

    return (
      <Card bordered={false}>
        <Button
          type="dashed"
          icon="plus"
          style={{ width: '100%', marginBottom: 24 }}
          onClick={this.onClick}
        >
          添加
        </Button>
        <Modal
          title="添加"
          visible={addVisible}
          confirmLoading={addLoading}
          onOk={this.handleOkAdd}
          onCancel={this.handleCancelAdd}
        >
          <Form.Item
            labelCol={{ span: 4 }}
            wrapperCol={{ span: 20 }}
            label="角色名"
            hasFeedback
          >
            {getFieldDecorator('name', {
              rules: [{
                max: 32, message: '角色名长度不得超过32位',
              }, {
                required: true, message: '角色名不能为空',
              }],
            })(
              <Input placeholder="请输入角色名" />
            )}
          </Form.Item>
        </Modal>
        <Modal
          title="查看/分配权限"
          visible={permission}
          confirmLoading={roleLoading}
          onOk={this.handleOkPermission}
          onCancel={this.handleCancelPermission}
        >
          <Spin spinning={treeLoading}>
            <Tree
              showLine
              checkable
              defaultExpandAll // 默认展开所有树节点
              checkedKeys={checkedKeys} // 默认选中复选框的树节点String[]
              onCheck={this.onCheck}
              onSelect={this.onSelect}
            >
              {this.renderTreeNodes(treeData)}
            </Tree>
          </Spin>
        </Modal>
        <Table
          rowKey={record => record.id}
          loading={tableLoading}
          columns={columns}
          dataSource={data}
        />
      </Card>
    );
  }
}
