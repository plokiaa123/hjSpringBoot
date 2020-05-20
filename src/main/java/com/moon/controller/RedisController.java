package com.moon.controller;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.moon.entity.Poetry;
import com.moon.service.PoetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.moon.utils.BaseController;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/redis")
public class RedisController extends BaseController {

    @Autowired
    private PoetryService poetryService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/search")
    @ResponseBody
    public Poetry search(Poetry poetry) {
        JiebaSegmenter jb = new JiebaSegmenter();
        List<SegToken> list = jb.process(poetry.getContent(), JiebaSegmenter.SegMode.INDEX);
        String ids = "";
        for (SegToken segToken : list) {
            //获取redis库中所有的set集合
            SetOperations<String, String> ops = stringRedisTemplate.opsForSet();
            Set<String> set = ops.members(segToken.word);//得到一个词对应的所有id
            for (String id : set) {
                ids += id + ",";
            }
        }
        //这里是一个拼接手段就好比where 1=1 。这里可以不加-1，但是你需要想办法把最后一个","去掉
        ids += "-1";
        String whereIds = "where id in (" + ids + ")";
        Poetry poetries = poetryService.queryListByWhereStr(poetry,whereIds);
        return poetries;
    }

    ;

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String res = ops.get("leader");
        return res;
        // return "success";
    }
}
