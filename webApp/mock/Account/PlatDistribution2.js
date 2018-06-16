// 可分配角色
const roleData = [];
for (let i = 0; i < 10; i += 1) {
  roleData.push({
    key: i,
    title: `角色种类${i + 1}`,
    description: `角色描述${i + 1}`,
    disabled: i % 3 < 1,
  });
}

// 已分配角色[Keys]
const targetKeys = roleData.filter(item => item.key % 3 > 1).map(item => item.key);

const PlatDistribution = {
  roleData,
  targetKeys,
};

export default PlatDistribution;
