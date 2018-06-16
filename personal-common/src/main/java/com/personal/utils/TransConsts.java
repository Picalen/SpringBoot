package com.personal.utils;

/**
 * Created by daila on 2017/12/14.
 */
public interface TransConsts {
    /*
    * 交易或系统常量定义
    */
    /*********************企业监控业务字段*********************/
    // 添加监控企业
    public static final String APPKEY = "cgyvas76lnie9moqxp0b1=wrdk483ftu";
    public static final String APPID = "sunyard_jk";

    /*********************监控接口api*********************/
    // 添加监控企业
    public static final String ADDMC_API_CODE = "1177";
    public static final String ADDMC_API_NAME = "添加监控企业";

    // 取消监控企业
    public static final String REMOVEMC_API_CODE = "1178";
    public static final String REMOVEMC_API_NAME = "取消监控企业";

    // 更改监控企业
    public static final String UPDATEMC_API_CODE = "1179";
    public static final String UPDATEMC_API_NAME = "更改监控企业";

    // 查询监控企业
    public static final String QUERYMC_API_CODE = "1225";
    public static final String QUERYMC_API_NAME = "查询监控企业";

    // 查询接收
    public static final String QUERYREC_API_CODE = "1183";
    public static final String QUERYREC_API_NAME = "查询接收";

    // 更新接收
    public static final String UPDATEREC_API_CODE = "1184";
    public static final String UPDATEREC_API_NAME = "更新接收";

    // 查询监控套餐
    public static final String QUERYMP_API_CODE = "1181";
    public static final String QUERYMP_API_NAME = "查询监控套餐";


    /*********************监控企业业务代码*********************/
    // 添加监控企业
    public static final String ADDMC_TO_CODE = "M0001";
    public static final String ADDMC_TO_NAME = "添加监控企业";

    // 取消监控企业
    public static final String REMOVEMC_TO_CODE = "M0002";
    public static final String REMOVEMC_TO_NAME = "取消监控企业";

    // 更改监控企业
    public static final String UPDATEMC_TO_CODE = "M0003";
    public static final String UPDATEMC_TO_NAME = "更改监控企业";

    // 查询监控企业
    public static final String QUERYMC_TO_CODE = "M0004";
    public static final String QUERYMC_TO_NAME = "查询监控企业";

    // 查询接收
    public static final String QUERYREC_TO_CODE = "M0005";
    public static final String QUERYREC_TO_NAME = "查询接收";

    // 更新接收
    public static final String UPDATEREC_TO_CODE = "M0006";
    public static final String UPDATEREC_TO_NAME = "更新接收";

    // 查询监控套餐
    public static final String QUERYMP_TO_CODE = "M0007";
    public static final String QUERYMP_TO_NAME = "查询监控套餐";

    /*********************舆情接口api*********************/
    // 舆情订阅爬取
    public static final String SUBSCRIBEPO_API_CODE = "1232";
    public static final String SUBSCRIBEPO_API_NAME = "舆情订阅爬取";

    // 舆情正文获取
    public static final String CONTENTGETPO_API_CODE = "1231";
    public static final String CONTENTGETPO_API_NAME = "舆情获取正文";

    // 舆情检索
    public static final String SEARCHPO_API_CODE = "1230";
    public static final String SEARCHPO_API_NAME = "舆情检索";

    //增加舆情订阅词
    public static final String ADDPOKEY_API_CODE = "1226";
    public static final String ASSPOKEY_API_NAME = "增加订阅词";

    /*********************舆情接口业务代码*********************/
    // 舆情订阅爬取
    public static final String SUBSCRIBEPO_TO_CODE = "P0001";
    public static final String SUBSCRIBEPO_TO_NAME = "舆情订阅爬取";

    // 舆情正文获取
    public static final String CONTENTGETPO_TO_CODE = "P0002";
    public static final String CONTENTGETPO_TO_NAME = "舆情获取正文";

    // 舆情检索
    public static final String SEARCHPO_TO_CODE = "P0003";
    public static final String SEARCHPO_TO_NAME = "舆情检索";

    //增加舆情订阅词
    public static final String ADDPOKEY_TO_CODE = "P0004";
    public static final String ADDPOKEY_TO_NAME = "增加订阅词";

    /**************************监控类型**************************/
    //运营数据
    public static final String OPER_TO_CODE = "T0001";
    public static final String OPER_TO_NAME = "运营数据";
        
    /*********************运营数据预警内容*********************/
    // 平台充值金额低于
    public static final String PTCZJEDY_CODE = "OPW0001";
    public static final String PTCZJEDY_NAME= "平台充值金额低于";

    // 平台净流出金额高于
    public static final String PTJLCJEGY_CODE = "OPW0002";
    public static final String PTJLCJEGY_NAME = "平台净流出金额高于";

    // 存管汇总户余额高于
    public static final String CGHZHYEGY_CODE = "OPW0003";
    public static final String CGHZHYEGY_NAME = "存管汇总户余额高于";

    // 红包、抵用券和加息劵使用高于
    public static final String HBDYJHJXJSYGY_CODE = "OPW0004";
    public static final String HBDYJHJXJSYGY_NAME = "红包、抵用券和加息劵使用高于";

    // 逾期笔数高于
    public static final String YQBSGY_CODE = "OPW0005";
    public static final String YQBSGY_NAME = "逾期笔数高于";

    // 流标笔数高于
    public static final String LBBSGY_CODE = "OPW0006";
    public static final String LBBSGY_NAME = "流标笔数高于";

    // 标的转让金额占比高于
    public static final String BDZRJEZBGY_CODE = "OPW0007";
    public static final String BDZRJEZBGY_NAME = "标的转让金额占比高于";

    // 代偿金额高于
    public static final String DCJEGY_CODE = "OPW0008";
    public static final String DCJEGY_NAME = "代偿金额高于";

    // 代偿笔数高于
    public static final String DCBSGY_CODE = "OPW0009";
    public static final String DCBSGY_NAME = "代偿笔数高于";

    // 个人募集单个标的金额超过20W高于
    public static final String GRMJDGBDJECGGY_CODE = "OPW00010";
    public static final String GRMJDGBDJECGGY_NAME = "个人募集单个标的金额超过20W高于";

    // 企业募集单个标的金额超过100W高于
    public static final String QYMJDGBDJEGY_CODE = "OPW00011";
    public static final String QYMJDGBDJEGY_NAME = "企业募集单个标的金额超过100W高于";

    // 开户和投资用户数量的比例高于
    public static final String KHHTZYHSLDBLGY_CODE = "OPW00012";
    public static final String KHHTZYHSLDBLGY_NAME = "开户和投资用户数量的比例高于";

    // 复投率低于
    public static final String FTLGY_CODE = "OPW00013";
    public static final String FTLGY_NAME = "复投率低于";

    // 前十大投资人累计借款金额
    public static final String QSDTZRLJJKJE_CODE = "OPW00014";
    public static final String QSDTZRLJJKJE_NAME = "前十大投资人累计借款金额";

    // 前十大投资人累计借款占比
    public static final String QSDTZRLJJKZB_CODE = "OPW00015";
    public static final String QSDTZRLJJKZB_NAME = "前十大投资人累计借款占比";

    // 前十大借款人待还金额占比高于
    public static final String QSDJKRDHJEZBGY_CODE = "OPW00016";
    public static final String QSDJKRDHJEZBGY_NAME = "前十大借款人待还金额占比高于";

    // 最大单一借款人待还金额占比高于
    public static final String ZDDYJKRDHJEZBGY_CODE = "OPW00017";
    public static final String ZDDYJKRDHJEZBGY_NAME = "最大单一借款人待还金额占比高于";

    // 个人借款待还金额超过20W人数高于
    public static final String GRJKDHJECGRSGY_CODE = "OPW00018";
    public static final String GRJKDHJECGRSGY_NAME = "个人借款待还金额超过20W人数高于";

    // 企业借款待还金额超过100W人数高于
    public static final String QYJKDHJECGRSGY_CODE = "OPW00019";
    public static final String QYJKDHJECGRSGY_NAME = "企业借款待还金额超过100W人数高于";
}
