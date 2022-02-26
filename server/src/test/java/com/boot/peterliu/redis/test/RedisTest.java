package com.boot.peterliu.redis.test;

import com.boot.peterliu.redis.server.MainApplication;
import com.google.common.collect.Sets;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.util.Lists;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Set;

@Log4j2
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 字符串的测试
     */
    @Test
    public void method1() {
        log.info("----此处打印字符串----");
        final String key = "SpringbootRedis:Order:1001";
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, 1001L);
        log.info("当前key={} 所对应的value={}", key, valueOperations.get(key));

        valueOperations.increment(key, 1001L);
        log.info("当前key={} 所对应的value={}", key, valueOperations.get(key));

    }

    /**
     * 字符串列表的测试
     */
    @Test
    public void method2() {
        log.info("开始列表list测试");
        final String key = "SpringBootRedis:List:10010";
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        redisTemplate.delete(key);//清空key

        //TODO: 列表插入顺序为e,d,c,b,a
        List<String> list = Lists.newArrayList("c", "d", "e");
        listOperations.leftPush(key, "a");
        listOperations.leftPush(key, "b");

        listOperations.leftPushAll(key, list);

        log.info("当前列表元素个数：{}", listOperations.size(key));
        log.info("当前列表元素有：{}", listOperations.range(key, 0L, 10L));

        log.info("当前列表中下标为0的元素是:{}", listOperations.index(key, 0L));
        log.info("当前列表中下标为4的元素是:{}", listOperations.index(key, 4L));
        log.info("当前列表中下标为10的元素是:{}", listOperations.index(key, 10L));

        log.info("当前列表中从右边弹出来的元素：{}", listOperations.rightPop(key));

        listOperations.set(key, 0L, "99");
        log.info("当前列表中下标为0的元素：{}", listOperations.index(key, 0L));
        log.info("当前列表元素有：{}", listOperations.range(key, 0L, 10L));

        listOperations.remove(key,0,"99");
        log.info("当前列表元素有：{}", listOperations.range(key, 0L, 10L));

    }

    /**
     * Set数据结构单元测试
     */
    @Test
    public void test3(){
        log.info("开始集合Set的测试");
        final String key1="SpringBootRedis:Set:1001";
        final String key2="SpringBootRedis:Set:1002";
        redisTemplate.delete(key1);
        redisTemplate.delete(key2);

        SetOperations setOperations = redisTemplate.opsForSet();

        setOperations.add(key1,new String[]{"a","b","c"});
        setOperations.add(key2,new String[]{"b","e","f"});

        log.info("集合key1的元素:{} ",setOperations.members(key1));
        log.info("集合key2的元素:{} ",setOperations.members(key2));

        log.info("集合key1中随机取出的1个元素:{} ",setOperations.randomMember(key1));
        log.info("集合key2中随机取出的n个元素:{} ",setOperations.randomMembers(key2,2L));

        log.info("集合key1中元素个数:{} ",setOperations.size(key1));
        log.info("集合key2中元素个数:{} ",setOperations.size(key2));

        log.info("元素e是否是key1集合中的:{} ",setOperations.isMember(key1,"e"));
        log.info("元素f是否是key2集合中的:{} ",setOperations.isMember(key2,"f"));

        log.info("集合key1和集合key2的差集元素:{}",setOperations.difference(key1,key2));
        log.info("集合key1和集合key2的交集元素:{}",setOperations.intersect(key1,key2));
        log.info("集合key1和集合key2的并集元素:{}",setOperations.union(key1,key2));

        log.info("从集合key1中弹出的一个随机元素:{}",setOperations.pop(key1,1L));
        log.info("集合key1的元素:{} ",setOperations.members(key1));

        log.info("从集合key2中移除元素e:{} ",setOperations.remove(key2,"e"));


    }



}







