package com.personal;

import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {


    @Test
    public void contextLoads() {
    }

    @Test
    public void bankAccountTest() throws Exception {
        JSONObject json = new JSONObject();
        System.out.println(json);

    }

}
