package com.example.demo;

import cn.hutool.cache.impl.TimedCache;
import org.assertj.core.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.owasp.esapi.ESAPI;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Resource
    private TimedCache<String, String> timedCache;

    @Test
    public void test(){
//        TimedCache<String, String> timedCache = new TimedCache<String, String>(3000);
        timedCache.put("key1", "value1", 4000);
        timedCache.put("key2", "value2", 30000);
        timedCache.put("key3", "value3", 3000);
        timedCache.put("key4", "value4", 3000);

        //启动定时任务，每4秒检查一次过期
//        timedCache.schedulePrune(DateUtil.SECOND_MS * 3);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (String value : timedCache) {
            System.out.println(value);
        }
        String key2 = timedCache.get("key2");
        String key4 = timedCache.get("key4");
    }

    @Test
    public void test1(){
        //正态
        int[] array = {15,96,85,88,18,58,68,16,6,99,88,11,8,36,82,44,55,66};
        int sum = 0;
        for(int i=0;i<array.length;i++){
            sum += array[i];      //求出数组的总和
        }
        System.out.println(sum);  //939
        double average = sum/array.length;  //求出数组的平均数
        System.out.println(average);   //52.0
        int total=0;
        for(int i=0;i<array.length;i++){
            total += (array[i]-average)*(array[i]-average);   //求出方差，如果要计算方差的话这一步就可以了
        }
        double standardDeviation = Math.sqrt(total/array.length);   //求出标准差
        System.out.println(standardDeviation);    //32.55764119219941
    }

    @Test
    public void test2(){
        String str = "餐桌 * 0 椅子白色 * 0 椅子灰色 * 0 沙发床 * 1 茶几 * 0 客厅柜 * 0 双人床1.5m * 1 床头柜 * 0 衣柜 * 1 双人床1.5m * 2 床头柜 * 0 斗柜 * 0 双人床1.8m * 1 单人床1.2m * 0 床头柜 * 0 边柜 * 0 斗柜 * 1 鞋柜 * 0 客厅柜 * 0 餐椅 * 0 ";
        String s = str.replaceAll("(\\s\\d+)", "$1,").replaceAll("\\s","");
        System.out.println(s);
        String[] split1 = s.split(",");
        Map<String,Integer> map = new HashMap<>();
        Arrays.stream(split1).forEach(s1 -> {
            String[] split = s1.split("\\*");
            String key = split[0];
            Integer count = Integer.valueOf(split[1]);
            if (map.containsKey(key)){
                map.put(key,map.get(key) + count);
                return;
            }
            map.put(key,count);
        });
        System.out.println(map);
    }

}
