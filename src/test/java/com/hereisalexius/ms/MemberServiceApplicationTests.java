package com.hereisalexius.ms;

import com.hereisalexius.ms.dto.MemberInputInfo;
import com.hereisalexius.ms.dto.MemberUpdateInfo;
import com.hereisalexius.ms.model.Member;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceApplicationTests {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;


    private MemberInputInfo testInput;
    private MemberUpdateInfo testUpdate;
    private Member testMember;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void createMember() {


    }

}
