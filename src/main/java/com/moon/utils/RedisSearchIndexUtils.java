package com.moon.utils;



import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class RedisSearchIndexUtils {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/moonlx?useSSL=false&characterEncoding=UTF-8&serverTimeZone=GMT+8", "root", "root");
            PreparedStatement pst = conn.prepareStatement("select * from poetry");
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                //对title+content分词
                JiebaSegmenter seg = new JiebaSegmenter();
                //index细分，search粗分
                List<SegToken> list = seg.process(title+content, JiebaSegmenter.SegMode.INDEX);
                for (SegToken s : list) {
                    //过滤小于俩个字的词条
                    if(s.word.length()<2) {
                        continue;
                    }
                    //导入redis
                    Jedis jedis = new Jedis("127.0.0.1", 6379);
                    jedis.sadd(s.word, id+"");
                }
                //一条数据导入索引成功
                System.out.println("成功导入索引:" + title);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
