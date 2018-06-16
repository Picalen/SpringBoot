import React, { PureComponent } from 'react';
import { connect } from 'dva';
import moment from 'moment';
import { message, Card, Col, DatePicker, Row } from 'antd';

import Trend from '../../components/Trend';

import styles from './Home.less';

@connect(state => ({
  home: state.home,
}))
export default class Home extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {

    };
  }

  componentDidMount() {

  }

  componentWillUnmount() {

  }



  render() {

    return (
      <div>
        首页
      </div>
    );
  }
}
