package com.moon.dao;

import com.moon.entity.Poetry;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PoetryMapper extends Mapper<Poetry> {

    List<Poetry> queryListByWhereStr(@Param("whereIds") String whereIds);
}