package com.moon.controller;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.moon.entity.Poetry;
import com.moon.service.PoetryService;
import org.redisson.Redisson;
import org.redisson.api.RLock;
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
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/redis")
public class RedisController extends BaseController {

    @Autowired
    private Redisson redisson;

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
            Set<String> set = ops.members("search:"+segToken.word);//得到一个词对应的所有id
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

    @RequestMapping("/redislock")
    @ResponseBody
    public String redislock(){
        String product="goods";
        /*String clientId= UUID.randomUUID().toString();*/
        RLock redislock = redisson.getLock(product);
        try {
            /*Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(product,clientId,30,TimeUnit.SECONDS);
            if (!result){
                return "error";
            }*/
            redislock.lock();
            int stock=Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0){
                int realStock=stock-1;
                stringRedisTemplate.opsForValue().set("stock",realStock+"");
                System.out.println("购买成功，剩余库存："+realStock);
            }else {
                System.out.println("库存不足");
            }
        } finally {
            /*stringRedisTemplate.delete(product);*/
            redislock.unlock();
        }
        return "end";
    }
}
