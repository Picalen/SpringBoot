import React, { Component } from 'react';
import { connect } from 'dva';
import { routerRedux } from 'dva/router';
import cookie from 'js-cookie';
import { Alert, Button, Checkbox, Form, Icon, Input, message } from 'antd';

import styles from './Login.less';

@connect(state => ({
  login: state.login,
}))
@Form.create()
export default class Login extends Component {
  state = {
    // count: 0,
    type: 'account',
    UUID: '',
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.login.flag && cookie.get('jdBjId')) {
      const jdBjId = cookie.get('jdBjId');
      cookie.remove('jdBjId');
      cookie.set('jdBjId', jdBjId);
      cookie.set('UUID', this.state.UUID, { expires: 30, path: '' });
      this.props.dispatch(routerRedux.push('/index'));
    }
  }

  componentWillUnmount() {
    clearInterval(this.interval);
  }

  /*
  onSwitch = (key) => {
    this.setState({
      type: key,
    });
  }

  onGetCaptcha = () => {
    let count = 59;
    this.setState({ count });
    this.interval = setInterval(() => {
      count -= 1;
      this.setState({ count });
      if (count === 0) {
        clearInterval(this.interval);
      }
    }, 1000);
  }
  */

  handleSubmit = (e) => {
    e.preventDefault();
    const { type } = this.state;
    this.props.form.validateFields({ force: true }, (err, values) => {
      if (!err) {
        this.setState({ UUID: values.username });
        this.props.dispatch({
          type: `login/${type}Submit`,
          payload: values,
        }).then(() => {
          const { login: { flag, info } } = this.props;
          if (!flag) {
            message.error(info);
          }
        });
      }
    }
    );
  }

  renderMessage = () => {
    const { login: { info } } = this.props;
    return (
      <Alert
        style={{ marginBottom: 24 }}
        message={info}
        type="error"
        showIcon
      />
    );
  }

  render() {
    const {
      form: {
        getFieldDecorator,
      },
      login: {
        flag,
        data: {
          type,
        },
        other: {
          submitting,
        },
      },
    } = this.props;
    // const { count } = this.state;
    return (
      <div className={styles.main}>
        <Form onSubmit={this.handleSubmit}>
          {/* <Tabs
             animated={false}
             className={styles.tabs}
             activeKey={type}
             onChange={this.onSwitch}
           >
           <TabPane tab="账户密码登录" key="account"> */}
          {
            !flag &&
            type === 'account' &&
            submitting === false &&
            this.renderMessage()
          }
          <Form.Item>
            {getFieldDecorator('username', {
              initialValue: cookie.get('UUID'),
              rules: [{
                required: true, message: '请输入账户',
              }, {
                whitespace: true, message: '请输入账户',
              }],
            })(
              <Input
                size="large"
                prefix={<Icon type="user" className={styles.prefixIcon} />}
                placeholder="请输入账户"
              />
                )}
          </Form.Item>
          <Form.Item>
            {getFieldDecorator('password', {
              rules: [{
                required: true, message: '请输入密码',
              }, {
                whitespace: true, message: '请输入密码',
              }],
            })(
              <Input
                size="large"
                prefix={<Icon type="lock" className={styles.prefixIcon} />}
                type="password"
                placeholder="请输入密码"
              />
                )}
          </Form.Item>
          {/* </TabPane>
           <TabPane tab="手机号登录" key="mobile">
              {
                flag &&
                type === 'mobile' &&
                submitting === false &&
                this.renderMessage('验证码错误')
              }
              <Form.Item>
                {getFieldDecorator('mobile', {
                  rules: [{
                    required: type === 'mobile', message: '请输入手机号！',
                  }, {
                    pattern: /^1\d{10}$/, message: '手机号格式错误！',
                  }],
                })(
                  <Input
                    size="large"
                    prefix={<Icon type="mobile" className={styles.prefixIcon} />}
                    placeholder="手机号"
                  />
                )}
              </Form.Item>
              <Form.Item>
                <Row gutter={8}>
                  <Col span={16}>
                    {getFieldDecorator('captcha', {
                      rules: [{
                        required: type === 'mobile', message: '请输入验证码！',
                      }],
                    })(
                      <Input
                        size="large"
                        prefix={<Icon type="mail" className={styles.prefixIcon} />}
                        placeholder="验证码"
                      />
                    )}
                  </Col>
                  <Col span={8}>
                    <Button
                      disabled={count}
                      className={styles.getCaptcha}
                      size="large"
                      onClick={this.onGetCaptcha}
                    >
                      {count ? `${count} s` : '获取验证码'}
                    </Button>
                  </Col>
                </Row>
              </Form.Item>
            </TabPane>
           </Tabs> */}
          <Form.Item className={styles.additional}>
            {getFieldDecorator('remember', {
              valuePropName: 'checked',
              initialValue: true,
            })(
              <Checkbox className={styles.autoLogin}>记住账号</Checkbox>
            )}
            {/* <a className={styles.forgot} href="">忘记密码</a> */}
            <Button size="large" loading={submitting} className={styles.submit} type="primary" htmlType="submit">
              登录
            </Button>
          </Form.Item>
        </Form>
        {/* <div className={styles.other}>
          其他登录方式
           需要加到 Icon 中
          <span className={styles.iconAlipay} />
          <span className={styles.iconTaobao} />
          <span className={styles.iconWeibo} />
          <Link className={styles.register} to="/user/register">注册账户</Link>
        </div> */}
      </div>
    );
  }
}
