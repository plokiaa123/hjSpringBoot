package com.moon.config;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import com.moon.utils.RedisCache;
@EnableCaching
@Configuration
public class RedisCacheConfig {
	@Autowired
	private JedisConnectionFactory JedisConnectionFactory;

	@Bean
	public RedisTemplate redisTemplate() {
		RedisTemplate redtpl = new RedisTemplate();
		redtpl.setConnectionFactory(JedisConnectionFactory);
		redtpl.setEnableTransactionSupport(true);
		return redtpl;
	}

	@Bean
	public SimpleCacheManager cacheManager(RedisTemplate redisTemplate) {
		SimpleCacheManager cachemgr = new SimpleCacheManager();
		RedisCache r0 = new RedisCache();
		r0.setRedisTemplate(redisTemplate);
		r0.setName("cache0");
		r0.setLiveTime(86400);
		RedisCache r1 = new RedisCache();
		r1.setRedisTemplate(redisTemplate);
		r1.setName("cache1");
		r1.setLiveTime(86400);
		Set<RedisCache> set = new HashSet<RedisCache>();
		set.add(r0);
		set.add(r1);
		cachemgr.setCaches(set);
		return cachemgr;
	}
}
