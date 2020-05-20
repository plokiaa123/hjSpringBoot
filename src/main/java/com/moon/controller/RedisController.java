package com.moon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.moon.utils.BaseController;

@Controller
@RequestMapping("/redis")
public class RedisController extends BaseController {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
		
		String res = ops.get("leader");
		return res;
		// return "success";
	}
}
