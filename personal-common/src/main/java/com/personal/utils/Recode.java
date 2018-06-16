package com.personal.utils;

/**
 * Created by daila on 2017/12/13.
 */
public class Recode {
    /*********************客户端引起的错误*********************/
    /**
     * 缺少必要参数
     **/
    public static final String PARAMETER_LACK = "20001";
    /**
     * 参数错误
     **/
    public static final String PARAMETER_ERROR = "20002";
    /**
     * 签名错误
     **/
    public static final String SIGN_ERROR = "20003";
    /**
     * appid为空
     **/
    public static final String APPID_NULL = "20004";
    /**
     * appid错误
     **/
    public static final String APPID_ERROR = "20005";
    /**
     * platNo为空
     **/
    public static final String PLATNO_NULL = "20014";
    /**
     * platNo错误
     **/
    public static final String PLATNO_ERROR = "20015";
    /**
     * 订单号重复
     **/
    public static final String ORDER_REPEAT = "20016";
    /**
     * 原订单号错误
     **/
    public static final String ORIGINORDER_ERROR = "20017";
    /**
     * 原请求订单未处理成功
     **/
    public static final String ORIGINORDER_UNSUCCESS = "20018";
    /**
     * 验证码错误
     **/
    public static final String VCODE_ERROR = "20019";
    /**
     * 验证码失效
     **/
    public static final String VCODE_FAIL = "20020";
    /*********************服务端内部错误*********************/

    /**
     * 未知错误
     **/
    public static final String ERROR = "30001";
    //数据库操作异常
    public static final String DATAERROR = "30002";
    //空指针异常
    public static final String NullPointerERROR = "30003";
    //IO操作异常
    public static final String IOERROR = "30004";
    //数学运算异常
    public static final String ArithmeticERROR = "30005";
    //数组越界
    public static final String ArrayIndexOutOfBoundsERROR = "30006";
    //方法参数错误
    public static final String IllegalArgumentERROR = "30007";
    //类转换异常
    public static final String ClassCastERROR = "30008";
    //违背安全原则
    public static final String SecurityERROR = "30009";
    //SQL操作异常
    public static final String SQLERROR = "30010";
    //运行异常
    public static final String RuntimeERROR = "30011";
    //方法名不存在
    public static final String NoSuchMethodException = "30012";
    //远程调用端错误
    public static final String REMOTE_ERROR = "31111";

    public static final String REMOTE_UNKNOWN = "32222";

    public static final String ServerBusy = "30013";//服务器繁忙
    public static final String ORDER_NOTFOUND = "34401";//未查询到订单信息
    /*********************业务相关信息*********************/
    public static final String SUCCESS="00000";
    public static final String UNSUCCESSFUL="10000";
}
