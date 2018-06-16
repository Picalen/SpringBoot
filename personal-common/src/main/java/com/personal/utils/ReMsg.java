package com.personal.utils;

/**
 * Created by daila on 2017/12/13.
 */
public class ReMsg {
    /*********************客户端引起的错误*********************/
    //缺少必要参数
    public static final String PARAMETER_LACK = "缺少必要参数";
    //参数错误
    public static final String PARAMETER_ERROR = "参数错误";
    //appid为空
    public static final String APPID_NULL = "appid为空";
    //appid错误
    public static final String APPID_ERROR = "appid错误";
    //plat_no为空
    public static final String PlatNo_NULL = "plat_no为空";
    //plat_no错误
    public static final String PlatNo_ERROR = "plat_no错误";
    //订单号重复
    public static final String ORDER_REPEAT = "订单号重复";
    //原订单号错误
    public static final String OriginOrder_ERROR = "原订单号错误";
    //原订单号错误
    public static final String BatchNum_ERROR = "总数量和明细数量不一致";
    //原订单号错误
    public static final String BatchNumOverrun_ERROR = "总数量超出处理数量限制";
    //原请求订单未处理成功
    public static final String OriginOrder_UnSuccess = "原订单请求异常";
    //验证码失效
    public static final String VCODE_ERROR = "验证码失效";
    //验证码失效
    public static final String VCODE_FAIL = "验证码错误";
    /*********************服务端内部错误*********************/
    public static final String ERROR = "未知错误";
    public static final String DATAERROR = "数据库操作异常";
    public static final String NullPointerERROR = "空指针异常";
    public static final String IOERROR = "IO操作异常";
    public static final String ArithmeticERROR = "数学运算异常";
    public static final String ArrayIndexOutOfBoundsERROR = "数组越界";
    public static final String IllegalArgumentERROR = "方法参数错误";
    public static final String ClassCastERROR = "类转换异常";
    public static final String SecurityERROR = "违背安全原则";
    public static final String SQLERROR = "SQL操作异常";
    public static final String RuntimeERROR = "运行异常";
    public static final String NoSuchMethodException = "方法名不存在";
    //短信发送失败
    public static final String SMSERROR = "短信发送失败";
    public static final String ORDER_NOTFOUND = "未查询到订单信息";
    /*********************业务相关信息*********************/
    public static final String SUCCESS="添加成功";
}
