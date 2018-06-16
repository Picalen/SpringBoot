const departments = [];
departments.push({ id: 1, department: '营销一部' });
departments.push({ id: 2, department: '营销二部' });
departments.push({ id: 3, department: '营销三部' });

const data = [];
data.push({ id: 1, username: 'Rocky', name: '赤火', department: '营销一部', phone: '12345678901', email: '123@qq.com', lock: Math.round(Math.random()) === 1 });
data.push({ id: 2, username: 'sc', name: '孙超', department: '营销二部', phone: '12345678902', email: '123@qq.com', lock: Math.round(Math.random()) === 1 });
data.push({ id: 3, username: 'wj', name: '王嘉', department: '营销三部', phone: '12345678903', email: '123@qq.com', lock: Math.round(Math.random()) === 1 });
data.push({ id: 4, username: 'jmy', name: '贾明玉', department: '营销四部', phone: '12345678904', email: '123@qq.com', lock: Math.round(Math.random()) === 1 });
data.push({ id: 5, username: 'dmc', name: '窦梦成', department: '营销五部', phone: '12345678905', email: '123@qq.com', lock: Math.round(Math.random()) === 1 });

const ChildrenAccount = {
  departments,
  data,
};

export default ChildrenAccount;
