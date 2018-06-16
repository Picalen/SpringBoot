import React, { PureComponent } from 'react';
import { connect } from 'dva';
import { Button, Card, Form, Icon, Input, message } from 'antd';

@connect(state => ({
  personalCenter: state.personalCenter,
}))
@Form.create()
export default class ModifyPassword extends PureComponent {
  onChange = (rule, value, callback) => {
    const { getFieldValue } = this.props.form;
    if (value && value !== getFieldValue('newPassword')) {
      callback('两次输入不一致');
    } else {
      callback();
    }
  }

  handleSubmit = () => {
    this.props.form.validateFields((err, values) => {
      if (!err) {
        const { oldPassword, newPassword } = values;
        this.props.dispatch({
          type: 'personalCenter/updatePassword',
          payload: { oldPassword, newPassword },
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
  }

  render() {
    const {
      form: {
        getFieldDecorator,
      },
      personalCenter: {
        other: {
          loading,
          passwordLoading,
        },
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
        title="修改密码"
        loading={loading}
        extra={
          <Button
            ghost
            type="primary"
            icon="check"
            loading={passwordLoading}
            onClick={this.handleSubmit}
          >
            提交
          </Button>
      }
      >
        <Form onSubmit={this.handleSubmit}>
          <Form.Item {...formItemLayout} label="旧密码" hasFeedback>
            {getFieldDecorator('oldPassword', {
                rules: [{
                  required: true, message: '请输入旧密码',
                }],
              })(
                <Input prefix={<Icon type="lock" />} type="password" placeholder="请输入旧密码" />
              )}
          </Form.Item>
          <Form.Item {...formItemLayout} label="新密码" hasFeedback>
            {getFieldDecorator('newPassword', {
                rules: [{
                  required: true, message: '密码不能为空',
                }, {
                  min: 6, message: '密码不能少于6位',
                }],
              })(
                <Input prefix={<Icon type="lock" />} type="password" placeholder="请输入密码，不少于6位" />
              )}
          </Form.Item>
          <Form.Item {...formItemLayout} label="确认密码" hasFeedback>
            {getFieldDecorator('repeatPassword', {
                rules: [{
                  required: true, message: '确认密码不能为空',
                }, {
                  validator: this.onChange,
                }],
              })(
                <Input prefix={<Icon type="lock" />} type="password" placeholder="请确认密码" />
              )}
          </Form.Item>
        </Form>
      </Card>
    );
  }
}
