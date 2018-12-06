package com.personal.service.impl;

import com.personal.service.inter.OperationService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * 业务操作类 接口实现
 *
 * @author sunchao
 * @date 2018/12/6
 */

@Service
public class OperationServiceImpl  implements OperationService{

    private Logger logger = Logger.getLogger(this.getClass());


    /**
     * 测试打印
     * */
    @Override
    public  void printObjectName(){

        logger.info("--- come into OperationServiceImpl ---");

    }
}
