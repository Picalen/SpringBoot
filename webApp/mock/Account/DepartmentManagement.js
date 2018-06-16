const data = [];
data.push({
  id: 1,
  name: '北京银行',
  children: [
    { id: 11, name: '北京银行支行1' },
    { id: 12, name: '北京银行支行2' },
    {
      id: 13,
      name: '北京银行支行3',
      children: [
        { id: 14, name: '北京银行支行11' },
        { id: 15, name: '北京银行支行22' },
        { id: 16, name: '北京银行支行33' },
      ],
    },
  ],
});
data.push({
  id: 2,
  name: '中国建设银行',
  children: [
    { id: 21, name: '中国建设银行1' },
    { id: 22, name: '中国建设银行2' },
    { id: 23, name: '中国建设银行3' },
  ],
});
data.push({
  id: 3,
  name: '中国工商银行',
  children: [
    { id: 31, name: '中国工商银行1' },
    { id: 32, name: '中国工商银行2' },
    { id: 33, name: '中国工商银行3' },
  ],
});

const DepartmentManagement = {
  data,
};

export default DepartmentManagement;
