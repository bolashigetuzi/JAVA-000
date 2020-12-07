package com.datasource.demo.mapper;

import com.datasource.demo.aop.DataSoureceChange;
import com.datasource.demo.entity.DataSourceType;
import com.datasource.demo.entity.Emp;
import org.apache.ibatis.annotations.*;

/**
 * Created by IntelliJ IDEA
 * User: yyzz
 * Date: 2020/11/30
 * Time: 19:52
 */
public interface EmpMapper {
    @DataSoureceChange(type = DataSourceType.SLAVE)
    @Select("SELECT * FROM t_emp WHERE empNo = #{empNo}")
    @Results({
            @Result(property = "empName",  column = "empName"),
            @Result(property = "empNo", column = "empNo")
    })
    Emp getOne(int empNo);

    @DataSoureceChange(type = DataSourceType.MASTER)
    @Insert("INSERT INTO t_emp(empName) VALUES(#{empName})")
    void insert(Emp emp);

    @DataSoureceChange(type = DataSourceType.MASTER)
    @Update("UPDATE t_emp set empName=#{empName} where empNo=#{empNo}")
    void update(Emp emp);
}
