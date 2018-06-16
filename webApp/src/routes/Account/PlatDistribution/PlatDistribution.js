import React, { PureComponent } from 'react';
import { connect } from 'dva';
import { Button, Card, Col, Form, Input, message, Modal, Row, Spin, Table, Tag, Transfer } from 'antd';

import styles from './PlatDistribution.less';

@connect(state => ({
  platDistribution: state.platDistribution,
}))
@Form.create()
export default class PlatDistribution extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      roleId: -1,
      visible: false,
      roleData: [],
      targetKeys: [],
    };
  }
  componentDidMount() {
    this.props.dispatch({
      type: 'platDistribution/fetch',
    });
  }

  componentWillUnmount() {
    this.props.dispatch({
      type: 'platDistribution/clear',
    });
  }

  onClick = (id) => {
    this.setState({ visible: true });
    this.props.dispatch({
      type: 'platDistribution/getRoles',
      payload: { id },
    }).then(() => {
      const { platDistribution: { flag, info, data: { roleData, targetKeys } } } = this.props;
      if (flag) {
        this.setState({ roleId: id, roleData, targetKeys });
      } else {
        message.error(info);
      }
    });
  }

  handleOk = () => {
    const { roleId, targetKeys } = this.state;
    this.props.dispatch({
      type: 'platDistribution/setRoles',
      payload: { id: roleId, roles: targetKeys },
    }).then(() => {
      const { platDistribution: { flag, info } } = this.props;
      if (flag) {
        message.success(info);
        this.setState({ roleId: -1, roleData: [], targetKeys: [], visible: false });
        this.props.dispatch({
          type: 'platDistribution/fetch',
        });
      } else {
        message.error(info);
      }
    });
  }

  handleCancel = () => {
    this.setState({ roleId: -1, roleData: [], targetKeys: [], visible: false });
  }

  handleChange = (targetKeys) => {
    this.setState({ targetKeys });
  }

  handleSubmit = (e) => {
    e.preventDefault();
    this.props.form.validateFields((err, value) => {
      if (!err) {
        this.props.dispatch({
          type: 'platDistribution/query',
          payload: { ...value },
        }).then(() => {
          const { platDistribution: { flag, info } } = this.props;
          if (flag) {
            message.success(info);
          } else {
            message.error(info);
          }
        });
      }
    });
  }

  handleFormReset = () => {
    this.props.form.resetFields();
  }

  render() {
    const {
      form: {
        getFieldDecorator,
      },
      platDistribution: {
        data: {
          data,
        },
        other: {
          loading,
          spinning,
          confirmLoading,
        },
      },
    } = this.props;
    const {
      visible,
      roleData,
      targetKeys,
    } = this.state;

    const columns = [{
      title: '用户名',
      dataIndex: 'username',
      width: '10%',
    }, {
      title: '姓名',
      dataIndex: 'nickname',
      width: '10%',
    }, {
      title: '已分配平台',
      dataIndex: 'platforms',
      width: '70%',
      render: text => text.map((item, key) => <Tag key={key}>{item}</Tag>),
    }, {
      title: '操作',
      dataIndex: 'operation',
      width: '10%',
      render: (text, record) => <a onClick={() => this.onClick(record.id)}>分配平台</a>,
    }];

    return (
      <div className={styles.filterCardList}>
        <Card bordered={false} style={{ marginBottom: 24 }}>
          <div className={styles.tableListForm}>
            <Form onSubmit={this.handleSubmit} layout="inline">
              <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
                <Col xl={12} lg={12} md={12} sm={24} xs={24} >
                  <Form.Item label="用户名">
                    {getFieldDecorator('username')(
                      <Input placeholder="请输入用户名" />
                  )}
                  </Form.Item>
                </Col>
                <Col xl={12} lg={12} md={12} sm={24} xs={24} >
                  <Form.Item label="姓名">
                    {getFieldDecorator('nickname')(
                      <Input placeholder="请输入姓名" />
                  )}
                  </Form.Item>
                </Col>
              </Row>
              <Row>
                <div style={{ overflow: 'hidden' }}>
                  <span style={{ float: 'right' }}>
                    <Button type="primary" htmlType="submit">查询</Button>
                    <Button style={{ marginLeft: 8 }} onClick={this.handleFormReset}>重置</Button>
                  </span>
                </div>
              </Row>
            </Form>
          </div>
        </Card>
        <Card bordered={false}>
          <Table
            rowKey={record => record.id}
            loading={loading}
            columns={columns}
            dataSource={data}
          />
        </Card>
        <Modal
          title="查看/分配平台"
          visible={visible}
          confirmLoading={confirmLoading}
          onOk={this.handleOk}
          onCancel={this.handleCancel}
          width={700}
        >
          <Spin spinning={spinning}>
            <Transfer
              showSearch
              searchPlaceholder="请输入搜索内容"
              listStyle={{ width: 300, height: 500 }}
              render={item => item.title}
              titles={['可分配平台', '已分配平台']}
              dataSource={roleData}
              targetKeys={targetKeys}
              onChange={this.handleChange}
            />
          </Spin>
        </Modal>
      </div>
    );
  }
}
