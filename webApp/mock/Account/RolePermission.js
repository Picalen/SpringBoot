const treeData = [{
  title: 'A菜单',
  key: '0',
  children: [{
    title: 'A菜单A权限',
    key: '01',
    children: [
      { title: 'A菜单add权限', key: '010' },
      { title: 'A菜单edit权限', key: '011' },
      { title: 'A菜单remove权限', key: '012' },
    ],
  }, {
    title: 'A菜单A1权限',
    key: '02',
    children: [
      { title: 'A菜单add权限', key: '020' },
      { title: 'A菜单edit权限', key: '021' },
      { title: 'A菜单remove权限', key: '022' },
    ],
  }, {
    title: 'A菜单A2权限',
    key: '03',
  }],
}, {
  title: 'B菜单',
  key: '1',
  children: [
    { title: 'B菜单add权限', key: '10' },
    { title: 'B菜单edit权限', key: '11' },
    { title: 'B菜单remove权限', key: '12' },
  ],
}, {
  title: 'C菜单',
  key: '2',
}];

const data = [];
data.push({ id: 1, name: '管理员' });
data.push({ id: 2, name: '管理员1' });
data.push({ id: 3, name: '管理员2' });

const RolePermission = {
  treeData,
  data,
};

export default RolePermission;
