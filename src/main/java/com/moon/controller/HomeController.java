package com.moon.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.moon.utils.BaseController;

@Controller
public class HomeController extends BaseController {

	@RequestMapping("/")
	public String home() {
		return "moon/home";
	}

	@RequestMapping("/openWork/{work}")
	public String openWork(@PathVariable("work") String work) {
		return "moon/" + work;
	}

	@RequestMapping({ "/success", "/info" })
	public String success() {
		return "moon/success";
	}

	@RequestMapping("/login")
	public String login() {
		return "moon/login";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession sess) {
		sess.removeAttribute("user");
		return "redirect:/login";
	}

	//
}