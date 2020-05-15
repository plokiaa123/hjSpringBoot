package com.moon.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.moon.utils.BaseController;
import com.moon.entity.Student;
import com.moon.entity.UserInfo;
import com.moon.service.StudentService;

@Controller
@RequestMapping("/moon/Student")
public class StudentController extends BaseController {
	@Autowired
	private StudentService studentService;

	
	@RequestMapping("/selectByMulti")
	@ResponseBody
	public List<Student> selectByMulti(UserInfo userinfo) {
		return studentService.selectByMulti(userinfo);
	}


	@RequestMapping("/deleteById")
	@ResponseBody
	public String deleteById(int id) {
		studentService.deleteById(id);
		return "success";
	}

	//
}