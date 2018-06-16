import React, { PureComponent } from 'react';
import { connect } from 'dva';
import { Button, Card, Form, Input, message } from 'antd';

@connect(state => ({
  personalCenter: state.personalCenter,
}))
@Form.create()
export default class UserInfo extends PureComponent {
  handleSubmit = () => {
    this.props.form.validateFields((err, values) => {
      if (!err) {
        const { nickname, phone, email, weChat } = values;
        this.props.dispatch({
          type: 'personalCenter/updateUserInfo',
          payload: { nickname, phone, email, weChat },
        }).then(() => {
          const { personalCenter: { flag, info } } = this.props;
          if (flag) {
            message.success(info);
          } else {
            message.error(info);
          }
        });
      }
    });
  };

  render() {
    const {
      form: {
        getFieldDecorator,
      },
      personalCenter: {
        other: {
          loading,
          userInfoLoading,
        },
      },
      data: {
        uid,
        username,
        nickname,
        depart,
        bank,
        phone,
        email,
        weChat,
      },
    } = this.props;

    const formItemLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 },
        md: { span: 4 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
        md: { span: 16 },
      },
    };

    return (
      <Card
        bordered={false}
        title="账户信息"
        loading={loading}
        style={{ marginRight: 24 }}
        extra={
          <Button
            ghost
            type="primary"
            icon="check"
            onClick={this.handleSubmit}
            loading={userInfoLoading}
          >
            提交
          </Button>
        }
      >
        <Form.Item {...formItemLayout} label="用户ID">
          {getFieldDecorator('uid', {
            initialValue: uid,
          })(
            <Input disabled />
          )}
        </Form.Item>
        <Form.Item {...formItemLayout} label="账号">
          {getFieldDecorator('username', {
            initialValue: username,
          })(
            <Input disabled />
          )}
        </Form.Item>
        <Form.Item {...formItemLayout} label="所属部门">
          {getFieldDecorator('depart', {
            initialValue: depart,
          })(
            <Input disabled />
          )}
        </Form.Item>
        <Form.Item {...formItemLayout} label="所属银行">
          {getFieldDecorator('bank', {
            initialValue: bank,
          })(
            <Input disabled />
          )}
        </Form.Item>
        <Form.Item {...formItemLayout} label="姓名">
          {getFieldDecorator('nickname', {
            initialValue: nickname,
            rules: [{
              required: true, message: '姓名不能为空',
            }, {
              max: 25, message: '姓名长度不得超过25位',
            }],
          })(
            <Input placeholder="请输入姓名" />
          )}
        </Form.Item>
        <Form.Item {...formItemLayout} label="手机号">
          {getFieldDecorator('phone', {
            initialValue: phone,
            rules: [{
              len: 11, message: '请输入正确的手机号',
            }],
          })(
            <Input placeholder="请输入手机号" />
          )}
        </Form.Item>
        <Form.Item {...formItemLayout} label="邮箱">
          {getFieldDecorator('email', {
            initialValue: email,
            rules: [{
              type: 'email', message: '请输入正确的邮箱地址',
            }],
          })(
            <Input placeholder="请输入邮箱" />
          )}
        </Form.Item>
        <Form.Item {...formItemLayout} label="微信">
          {getFieldDecorator('weChat', {
            initialValue: weChat,
            rules: [{
              max: 25, message: '微信长度不得超过25位',
            }],
          })(
            <Input placeholder="请输入微信" />
          )}
        </Form.Item>
      </Card>
    );
  }
}
