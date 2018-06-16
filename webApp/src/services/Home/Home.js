import config from '../../utils/config';
import { request } from '../../utils/request';

export async function fetch(params) {
  return request(`${config.URL}/home/getAllDeposit`, {
    method: 'POST',
    body: params,
  });
}

export async function deposit(params) {
  return request(`${config.URL}/home/getDeposit`, {
    method: 'POST',
    body: params,
  });
}

export async function month(params) {
  return request(`${config.URL}/home/getDepositByMonth`, {
    method: 'POST',
    body: params,
  });
}

export async function year(params) {
  return request(`${config.URL}/home/getDepositByYear`, {
    method: 'POST',
    body: params,
  });
}
