const depositBalance = {};
depositBalance.depository = { amount: '3323元', proportion: '12', compare: '78', flag: 'up' };
depositBalance.deposit = { amount: '1787元', proportion: '21', compare: '56', flag: 'up' };
depositBalance.own = { amount: '801元', proportion: '43', compare: '54', flag: 'down' };
depositBalance.repayment = { amount: '310元', proportion: '23', compare: '32', flag: 'down' };

const monthTransaction = {};
monthTransaction.withdrawals = '142000元';
monthTransaction.recharge = '21213元';
monthTransaction.invest = '13540元';

const yearTransaction = {};
yearTransaction.withdrawals = '23400元';
yearTransaction.recharge = '1056400元';
yearTransaction.invest = '106780元';

const Home = {
  depositBalance,
  monthTransaction,
  yearTransaction,
};

export default Home;
