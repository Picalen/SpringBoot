package com.personal;

import com.personal.model.User;
import com.personal.redis.UserRedis;
import com.personal.service.inter.OperationService;
import com.personal.service.inter.UserRedisService;
import net.sf.json.JSONObject;
import org.apache.shiro.util.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    @Autowired
    private OperationService operationService;

    @Autowired
    private UserRedis userRedis;

    @Autowired
    UserRedisService userRedisService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void bankAccountTest() throws Exception {
        JSONObject json = new JSONObject();
        System.out.println(json);
    }

    @Test
    public void testAop() {
        operationService.printObjectName();
    }


    @Test
    public void get() {
        User user = new User();
        user.setName("wangjianfeng");
        user.setAge(22);
        userRedis.add("userByName:" + user.getName(), user, 10L);
        List<User> list = new ArrayList<>();
        list.add(user);
        userRedis.add("list", list, 10L);
        User user1 = userRedis.get("userByName:wangjianfeng");
        Assert.notNull(user1, "user is null");
        System.out.println("user1: "+user1.toString());
        List<User> list2 = userRedis.getUserList("list");
        Assert.notNull(list2, "list is null");
        System.out.println();
        if(list2.size()>0){
            for (User user2 : list){
                System.out.println("user2: "+user2.toString());
            }
        }

    }

    @Test
    public void testRedis() {
        int id1 = 1;
        int id2 = 4;

        User testUser = new User();
        testUser.setId(id1);
        User user1 = userRedisService.findById(testUser);
        if(user1!=null){
            System.out.println(user1.toString());
        }else {
            System.out.println("user1為空");
        }
        testUser.setId(id2);
        User user2 = userRedisService.findById(testUser);
        if(user2!=null){
            System.out.println(user2.toString());
        }else {
            System.out.println("user2為空");
        }
    }


}
