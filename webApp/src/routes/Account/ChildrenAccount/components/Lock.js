import React, { PureComponent } from 'react';
import { message, Switch } from 'antd';

import { editLock } from '../../../../services/Account/ChildrenAccount';

export default class Lock extends PureComponent {
  constructor(props) {
    super(props);
    this.state = { loading: false };
  }

  onChange = (lock) => {
    this.setState({ loading: true });
    const { id } = this.props;
    editLock({ id, lock }).then((json) => {
      const { flag, info } = json;
      if (flag === true) {
        message.success(info);
        this.props.changeLock(id, lock);
      } else {
        message.error(info);
      }
      this.setState({ loading: false });
    });
  }

  render() {
    const { check } = this.props;
    const { loading } = this.state;

    return (
      <Switch loading={loading} checked={check} onChange={this.onChange} />
    );
  }
}
