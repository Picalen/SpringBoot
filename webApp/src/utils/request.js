import fetch from 'dva/fetch';
import { notification } from 'antd';
import cookie from 'js-cookie';

function checkStatus(response) {
  const { status, url, statusText } = response;
  if (url.indexOf('ELogin') >= 0) {
    cookie.remove('jdBjId');
    cookie.remove('aMjedNuS');
    window.location.href = '/#/user/login';
    return response;
  }
  switch (status) {
    case 401:
      window.location.href = '/#/exception/404';
      return response;
    case 403:
      window.location.href = '/#/exception/403';
      return response;
    case 404:
      window.location.href = '/#/exception/404';
      return response;
    case 500:
      window.location.href = '/#/exception/500';
      return response;
    default:
      break;
  }
  if (status >= 200 && status < 300) {
    return response;
  }
  notification.error({
    message: `请求错误 ${status}: ${url}`,
    description: statusText,
  });
  const error = new Error(statusText);
  error.response = response;
  throw error;
}

export function request(url, options) {
  const defaultOptions = {
    credentials: 'include',
  };
  const newOptions = { ...defaultOptions, ...options };
  if (newOptions.method === 'POST' || newOptions.method === 'PUT') {
    newOptions.headers = {
      Accept: 'application/json',
      'Content-Type': 'application/json; charset=utf-8',
      ...newOptions.headers,
    };
    newOptions.body = JSON.stringify(newOptions.body);
  }

  return fetch(url, newOptions)
    .then(checkStatus)
    .then(response => response.json())
    .catch((error) => {
      if (error.code) {
        notification.error({
          message: error.name,
          description: error.message,
        });
      }
      if ('stack' in error && 'message' in error) {
        notification.error({
          message: `请求错误: ${url}`,
          description: error.message,
        });
      }
      return error;
    });
}

export function requestBlob(url, options) {
  const defaultOptions = {
    credentials: 'include',
  };
  const newOptions = { ...defaultOptions, ...options };
  if (newOptions.method === 'POST' || newOptions.method === 'PUT') {
    newOptions.headers = {
      Accept: 'application/json',
      'Content-Type': 'application/json; charset=utf-8',
      ...newOptions.headers,
    };
    newOptions.body = JSON.stringify(newOptions.body);
  }

  return fetch(url, newOptions)
    .then(checkStatus)
    .then(response => response.blob())
    .catch((error) => {
      if (error.code) {
        notification.error({
          message: error.name,
          description: error.message,
        });
      }
      if ('stack' in error && 'message' in error) {
        notification.error({
          message: `请求错误: ${url}`,
          description: error.message,
        });
      }
      return error;
    });
}
