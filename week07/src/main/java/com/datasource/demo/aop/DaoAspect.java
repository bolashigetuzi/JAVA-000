package com.datasource.demo.aop;

import com.datasource.demo.entity.DataSourceType;
import com.datasource.demo.util.DataSourceContext;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA
 * User: yyzz
 * Date: 2020/12/1
 * Time: 09:29
 */
@Aspect
@Component
public class DaoAspect {
    @Pointcut(value="execution(* com.datasource.demo.mapper.*.*(..))")
    public void daoPoint(){}

    @Before(value="daoPoint()&&@annotation(dataSoureceChange)")
    public void before(DataSoureceChange dataSoureceChange){
        if(dataSoureceChange.type()== DataSourceType.MASTER){
            DataSourceContext.setDataSource(DataSourceType.MASTER.getValue());
        }else{
            DataSourceContext.setDataSource(DataSourceType.SLAVE.getValue());
        }
        return;

    }

    @After(value="daoPoint()")
    public void after(){
        DataSourceContext.clear();
    }
}
