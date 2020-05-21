package com.moon.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.moon.entity.Poetry;
import com.moon.dao.PoetryMapper;

import java.util.List;

@Transactional
@Service
public class PoetryService {
	@Autowired
	private PoetryMapper poetryMapper;

	public PoetryMapper getPoetryMapper() {
		return poetryMapper;
	}

	public Poetry queryListByWhereStr(Poetry poetry,String whereIds) {
		PageHelper.startPage(poetry.getPage(),poetry.getPageSize());
		List<Poetry> list = poetryMapper.queryListByWhereStr(whereIds);
		PageInfo pageInfo = new PageInfo(list);
		poetry.setRows(list);
		poetry.setTotal((new Long(pageInfo.getTotal())).intValue());
		return poetry;
	}
}