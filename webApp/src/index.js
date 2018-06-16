import dva from 'dva';
import 'moment/locale/zh-cn';
import particles from 'particles.js';
import models from './models';
// import { browserHistory } from 'dva/router';

import './index.less';

// 1. Initialize
const app = dva({
  // history: browserHistory,
});

// 2. Plugins
app.use(particles);

// 3. Model move to router
models.forEach((m) => {
  app.model(m);
});

// 4. Router
app.router(require('./router'));

// 5. Start
app.start('#root');
