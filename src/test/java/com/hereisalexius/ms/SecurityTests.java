package com.hereisalexius.ms;

import com.hereisalexius.ms.config.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SecurityConfig.class)
@AutoConfigureMockMvc
public class SecurityTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void performActionWithValidUser() throws Exception {

        mockMvc.perform(get("/members/list")
                .with(user("admin")
                        .password("password")))
                .andExpect(authenticated().withUsername("admin"));
    }

    @Test
    public void performActionWithInvalidUser() throws Exception {
        mockMvc.perform(get("/members/list")
                .with(user("root")
                        .password("root")))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void performActionWithoutUser() throws Exception {
        mockMvc.perform(get("/members/list"))
                .andExpect(status().is4xxClientError());
    }

}

