package com.lf.car.service;

import com.lf.car.entity.LcConversation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LeanCloudServiceTest {

    @Autowired
    private LeanCloudService leanCloudService;

    @Test
    public void createOneConversations() throws Exception {
        LcConversation entity = new LcConversation();
        List<String> list = new ArrayList<>();
        list.add("haha");
        list.add("kangkang");
        entity.setM(list);
        entity.setC("haha");
        entity.setName("haha发起的聊天");
        leanCloudService.createOneConversations(entity);

    }

}