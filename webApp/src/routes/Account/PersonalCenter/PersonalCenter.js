import React, { PureComponent } from 'react';
import { connect } from 'dva';
import { Col, message, Row } from 'antd';
import BaseInfo from './components/BaseInfo/BaseInfo';
import ModifyPassword from './components/ModifyPassword/ModifyPassword';

@connect(state => ({
  personalCenter: state.personalCenter,
}))
export default class PersonalCenter extends PureComponent {
  componentDidMount() {
    this.props.dispatch({
      type: 'personalCenter/fetch',
    }).then(() => {
      const { personalCenter: { flag, info } } = this.props;
      if (!flag) {
        message.error(info);
      }
    });
  }

  componentWillUnmount() {
    this.props.dispatch({
      type: 'personalCenter/clear',
    });
  }

  render() {
    const {
      personalCenter: {
        data: {
          baseInfo,
        },
      },
    } = this.props;

    return (
      <div>
        <Row style={{ marginBottom: 24 }}>
          <Col span={12}>
            <BaseInfo data={baseInfo} />
          </Col>
          <Col span={12}>
            <ModifyPassword />
          </Col>
        </Row>
      </div>
    );
  }
}

