package com.personal.utils;

import net.sf.json.JSONObject;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by daila on 2017/12/13.
 */
public class HttpClientUtil {
    private static Logger logger = Logger.getLogger(HttpClientUtil.class);

    /**
     * post请求
     *
     * @param url
     * @param json
     * @return
     */
    public static JSONObject postClientMonitorCopy(JSONObject json, String url) {
        logger.info("post请求地址：" + url);
        logger.info("post请求参数：" + json.toString());
        OutputStreamWriter out = null;
        BufferedReader reader = null;
        String response = "";
        JSONObject responseJson = null;
        try {
            URL httpUrl = null; //HTTP URL类 用这个类来创建连接
            //创建URL
            httpUrl = new URL(url);
            //建立连接
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("connection", "keep-alive");
            conn.setUseCaches(false);//设置不要缓存
            conn.setInstanceFollowRedirects(true);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            //POST请求
            out = new OutputStreamWriter(
                    conn.getOutputStream());
            json.write(out);
            out.flush();
            //读取响应
            reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                response += lines;
            }
            reader.close();
            // 断开连接
            conn.disconnect();
            responseJson = JSONObject.fromObject(response);
            logger.info("post请求返回结果：" + response);
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return responseJson;
    }

    public static JSONObject postClientMonitor(Map<String, Object> map, String url, String encoding) throws Exception {
        String body = "";
        JSONObject responseJson = null;
        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        //装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
            }
        }
        //设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

        System.out.println("请求地址：" + url);
        System.out.println("请求参数：" + nvps.toString());
        CloseableHttpResponse response = null;
        try {
            //设置header信息
            //指定报文头【Content-type】、【User-Agent】
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            //httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //执行请求操作，并拿到结果（同步阻塞）
            response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                logger.error("post请求返回状态码：" + response.getStatusLine().getStatusCode());
                //获取结果实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    //按指定编码转换结果实体为String类型
                    body = EntityUtils.toString(entity, encoding);
                    responseJson = JSONObject.fromObject(body);
                    EntityUtils.consume(entity);
                    logger.info("post请求成功！返回信息：" + String.valueOf(responseJson));
                }
            } else {
                responseJson.put("Error(post请求失败！失败编码为)：", response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放链接
            response.close();
        }
//        //获取结果实体
//        HttpEntity entity = response.getEntity();
//        if (entity != null) {
//            //按指定编码转换结果实体为String类型
//            body = EntityUtils.toString(entity, encoding);
//            responseJson = JSONObject.fromObject(body);
//        }

        return responseJson;
    }
}
