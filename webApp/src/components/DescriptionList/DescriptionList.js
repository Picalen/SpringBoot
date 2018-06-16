import React from 'react';
import classNames from 'classnames';
import { Row } from 'antd';
import styles from './index.less';
import stylec from '../../routes/PlatInfo/PlatDetail/PlatDetail.less';

export default ({ className, title, image, platname, datePicker, monthPicker, yearPicker, col = 3, layout = 'horizontal', gutter = 32,
  children, size, ...restProps }) => {
  const clsString = classNames(styles.descriptionList, styles[layout], className, {
    [styles.descriptionListSmall]: size === 'small',
    [styles.descriptionListLarge]: size === 'large',
  });
  const column = col > 4 ? 4 : col;
  return (
    <div className={clsString} {...restProps}>
      {
        title
        ?
          <div className={styles.title}>{title}
            {datePicker}
            {monthPicker}
            {yearPicker}
            {image ? <img alt="logo" src={image} className={styles.imagee} /> : null}
            {platname ? <span className={stylec.platnameclass}>{platname}</span> : null}
          </div>
        :
        null
      }
      <Row gutter={gutter}>
        {React.Children.map(children, child => React.cloneElement(child, { column }))}
      </Row>
    </div>
  );
};
