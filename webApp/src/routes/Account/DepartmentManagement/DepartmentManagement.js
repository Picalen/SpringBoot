import React, { PureComponent } from 'react';
import { connect } from 'dva';
import { Button, Card, Divider, Form, Input, message, Modal, Popconfirm, Spin, Table } from 'antd';

const EditableCell = ({ editable, value, onChange }) => (
  editable
    ?
      <Input value={value} style={{ width: '90%' }} onChange={e => onChange(e.target.value)} />
    :
    value
);

@connect(state => ({
  departmentManagement: state.departmentManagement,
}))
@Form.create()
export default class DepartmentManagement extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
      row: {},
      expandedRowKeys: [],
      addVisible: false,
    };
    this.cacheData = [];
  }

  componentDidMount() {
    this.fetch();
  }

  componentWillUnmount() {
    this.props.dispatch({
      type: 'departmentManagement/clear',
    });
  }

  onClick = (row) => {
    this.setState({ row, addVisible: true });
  }

  onRemove = (id) => {
    Modal.confirm({
      title: '确定要删除这个部门?',
      content: '删除部门后将无法撤销',
      okText: '确定',
      okType: 'danger',
      cancelText: '取消',
      onOk: () => {
        return new Promise((resolve, reject) => {
          this.props.dispatch({
            type: 'departmentManagement/remove',
            payload: { id },
          }).then(() => {
            const { departmentManagement: { flag, info } } = this.props;
            if (flag) {
              message.success(info);
              resolve();
              this.fetch();
            } else {
              message.error(info);
              reject();
            }
          });
        });
      },
    });
  }

  onExpand = (expand, record) => {
    const { expandedRowKeys } = this.state;
    if (expand) {
      this.setState({ expandedRowKeys: expandedRowKeys.concat(record.id) });
    } else {
      this.setState({ expandedRowKeys: expandedRowKeys.filter(item => item !== record.id) });
    }
  }

  fetch = () => {
    this.props.dispatch({
      type: 'departmentManagement/fetch',
    }).then(() => {
      const { departmentManagement: { flag, info, data: { data } } } = this.props;
      if (flag) {
        const list1 = [];
        const loop = (item) => {
          for (let i = 0; i < item.length; i += 1) {
            if (item[i].children) {
              loop(item[i].children);
              list1.push(item[i].id);
            } else {
              list1.push(item[i].id);
            }
          }
        };
        loop(data);
        const caches = [];
        const circle = (item) => {
          for (let i = 0; i < item.length; i += 1) {
            const val = {};
            Object.keys(item[i]).forEach((key) => {
              if (key === 'children') {
                val[key] = circulate(item[i][key]);
              } else {
                val[key] = item[i][key];
              }
            });
            caches.push(val);
          }
        };
        const circulate = (item) => {
          const list = [];
          for (let i = 0; i < item.length; i += 1) {
            const val = {};
            Object.keys(item[i]).forEach((key) => {
              if (key === 'children') {
                val[key] = circulate(item[i][key]);
              } else {
                val[key] = item[i][key];
              }
            });
            list.push(val);
          }
          return list;
        };
        circle(data);
        this.cacheData = caches;
        this.setState({ data, expandedRowKeys: list1 });
      } else {
        message.error(info);
      }
    });
  }

  save = (key) => {
    const data = [...this.state.data];
    let target = {};
    const loop = (item) => {
      for (let i = 0; i < item.length; i += 1) {
        if (item[i].children) {
          if (key === item[i].id) {
            target = { ...item[i] };
            return true;
          }
          if (loop(item[i].children)) {
            return true;
          }
        } else { //
          if (key === item[i].id) {
            target = { ...item[i] };
            return true;
          }
        }
      }
    };
    loop(data);
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
        type: 'departmentManagement/edit',
        payload: { id, name },
      }).then(() => {
        const { departmentManagement: { flag, info } } = this.props;
        if (flag) {
          message.success(info);
          const circle = (item) => {
            for (let i = 0; i < item.length; i += 1) {
              if (item[i].children) {
                if (key === item[i].id) {
                  const t = item[i];
                  delete t.editable;
                  return true;
                }
                if (circle(item[i].children)) {
                  return true;
                }
              } else { //
                if (key === item[i].id) {
                  const t = item[i];
                  delete t.editable;
                  return true;
                }
              }
            }
          };
          circle(data);
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
    const loop = (item) => {
      for (let i = 0; i < item.length; i += 1) {
        if (item[i].children) {
          if (id === item[i].id) {
            const target = item[i];
            target.editable = true;
            return true;
          }
          if (loop(item[i].children)) {
            return true;
          }
        } else { //
          if (id === item[i].id) {
            const target = item[i];
            target.editable = true;
            return true;
          }
        }
      }
    };
    loop(data);
    this.setState({ data });
  }

  cancel = (id) => {
    const data = [...this.state.data];
    let assign;
    const cacheloop = (item) => {
      for (let i = 0; i < item.length; i += 1) {
        if (item[i].children) {
          if (id === item[i].id) {
            assign = item[i];
            return true;
          }
          if (cacheloop(item[i].children)) {
            return true;
          }
        } else { //
          if (id === item[i].id) {
            assign = item[i];
            return true;
          }
        }
      }
    };
    const loop = (item) => {
      for (let i = 0; i < item.length; i += 1) {
        if (item[i].children) {
          if (id === item[i].id) {
            const target = item[i];
            Object.assign(target, assign);
            delete target.editable;
            return true;
          }
          if (loop(item[i].children)) {
            return true;
          }
        } else { //
          if (id === item[i].id) {
            const target = item[i];
            Object.assign(target, assign);
            delete target.editable;
            return true;
          }
        }
      }
    };
    cacheloop(this.cacheData);
    loop(data);
    this.setState({ data });
  }

  handleChange(value, id, column) {
    const data = [...this.state.data];
    const loop = (item) => {
      for (let i = 0; i < item.length; i += 1) {
        if (item[i].children) {
          if (id === item[i].id) {
            const target = item[i];
            target[column] = value;
            return true;
          }
          if (loop(item[i].children)) {
            return true;
          }
        } else { //
          if (id === item[i].id) {
            const target = item[i];
            target[column] = value;
            return true;
          }
        }
      }
    };
    loop(data);
    this.setState({ data });
  }

  handleOkAdd = () => {
    this.props.form.validateFields((err, value) => {
      if (!err) {
        const { row: { id } } = this.state;
        const { name } = value;
        this.props.dispatch({
          type: 'departmentManagement/add',
          payload: { id, name },
        }).then(() => {
          const { departmentManagement: { flag, info } } = this.props;
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

  renderColumns(text, record, columns) {
    return (
      <EditableCell
        editable={record.editable}
        value={text}
        onChange={value => this.handleChange(value, record.id, columns)}
      />
    );
  }

  render() {
    const {
      form: {
        getFieldDecorator,
      },
      departmentManagement: {
        data: {
          security,
        },
        other: {
          loading,
          addLoading,
          editLoading,
        },
      },
    } = this.props;
    const { data, row, addVisible, expandedRowKeys } = this.state;

    const formItemLayout = {
      labelCol: { span: 4 },
      wrapperCol: { span: 20 },
    };

    const columns = [{
      title: '部门名称',
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
                    <a onClick={() => this.onClick(record)}>添加下级部门</a>
                    <Divider type="vertical" />
                    <a onClick={() => this.edit(record.id)}>编辑</a>
                    <Divider type="vertical" />
                    <a onClick={() => this.onRemove(record.id)}>删除</a>
                  </div>
            }
          </div>
        );
      },
    }];

    const formItem = (
      <Form>
        {
          row.name
            ?
              <div>
                <Form.Item {...formItemLayout} label="上级组织">
                  {getFieldDecorator('parentId', {
                  initialValue: row.name,
                  rules: [{
                    required: true, message: '上级组织不能为空',
                  }],
                })(
                  <Input disabled />
                )}
                </Form.Item>
                <Form.Item {...formItemLayout} label="部门名" hasFeedback>
                  {getFieldDecorator('name', {
                  rules: [{
                    max: 100, message: '长度不得超过100位',
                  }, {
                    required: true, message: '部门名不能为空',
                  }],
                })(
                  <Input placeholder="请输入部门名" />
                )}
                </Form.Item>
              </div>
            :
              <Form.Item {...formItemLayout} label="部门名" hasFeedback>
                {getFieldDecorator('name', {
                rules: [{
                  max: 100, message: '长度不得超过100位',
                }, {
                  required: true, message: '不能为空',
                }],
              })(
                <Input placeholder="请输入" />
              )}
              </Form.Item>
        }
      </Form>
    );

    return (
      <Card bordered={false}>
        {
          security
            ?
              <Button
                type="dashed"
                icon="plus"
                style={{ width: '100%', marginBottom: 24 }}
                onClick={() => this.onClick({})}
              >
                添加
              </Button>
            :
              undefined
        }
        <Modal
          title="添加部门"
          visible={addVisible}
          confirmLoading={addLoading}
          onOk={this.handleOkAdd}
          onCancel={this.handleCancelAdd}
        >
          {formItem}
        </Modal>
        <Table
          rowKey={record => record.id}
          loading={loading}
          columns={columns}
          dataSource={data}
          onExpand={this.onExpand}
          expandedRowKeys={expandedRowKeys}
        />
      </Card>
    );
  }
}
