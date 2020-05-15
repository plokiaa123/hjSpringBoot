package com.moon.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.moon.entity.Student;
import tk.mybatis.mapper.common.Mapper;

public interface StudentMapper extends Mapper<Student> {
	// 多条件查询
	// public List<Student> selectByMulti(@Param("dept") String dept, @Param("sex")
	// String sex);

	// 多条件查询-count-配合分页
	// public int selectCountByMulti(@Param("dept") String dept, @Param("sex")
	// String sex);

	//
}