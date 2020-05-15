package com.moon.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.moon.entity.Student;
import com.moon.entity.UserInfo;
import com.github.pagehelper.PageHelper;
import com.moon.dao.StudentMapper;

@Transactional
@Service
public class StudentService {
	@Autowired
	private StudentMapper studentMapper;

	public StudentMapper getStudentMapper() {
		return studentMapper;
	}

	public List<Student> selectByMulti(String dept, String sex, int pageNum, int pageSize) {
		// 构建查询条件
		Student crt = new Student();
		if (!"".equals(dept))
			crt.setDepartment(dept);
		if (!"".equals(sex))
			crt.setSex(sex);
		// 查询
		PageHelper.startPage(pageNum, pageSize);
		return studentMapper.select(crt);
	}

	// 根据id删除学员
	@CacheEvict(value="cache0",allEntries = true)
	public void deleteById(int id) {
		studentMapper.deleteByPrimaryKey(id);
	}
	@Cacheable(value="cache0",key="#root.targetClass.simpleName+':'+#root.methodName+'-'+#userinfo.dept+'-'+#userinfo.sex+'-'+#userinfo.pageNum+'-'+#userinfo.pageSize")
	public List<Student> selectByMulti(UserInfo userinfo) {
		// 构建查询条件
				Student crt = new Student();
				if (!"".equals(userinfo.getDept()))
					crt.setDepartment(userinfo.getDept());
				if (!"".equals(userinfo.getSex()))
					crt.setSex(userinfo.getSex());
				// 查询
				PageHelper.startPage(userinfo.getPageNum(), userinfo.getPageSize());
				return studentMapper.select(crt);
	}

	//
}