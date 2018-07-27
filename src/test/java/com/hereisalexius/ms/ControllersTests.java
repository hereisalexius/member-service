package com.hereisalexius.ms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hereisalexius.ms.dto.MemberInputInfo;
import com.hereisalexius.ms.dto.MemberUpdateInfo;
import com.hereisalexius.ms.model.Member;
import com.hereisalexius.ms.services.DeleteMemberService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllersTests {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private DeleteMemberService deleteMemberService;

    private MockMvc mockMvc;

    private Set<Member> savedMembers;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        savedMembers = new HashSet<>();
    }

    @After
    public void clearData() {
        savedMembers.forEach(member -> {
            deleteMemberService.delete(member.getId());
        });

    }

    //CREATE CONTROLLER

    private MemberInputInfo generateMemberInputInfo() {
        MemberInputInfo inputInfo = new MemberInputInfo();
        inputInfo.setFirstName("Firstname");
        inputInfo.setLastName("Lastname");

        String postal = (System.currentTimeMillis() + "").substring(0, 5);
        inputInfo.setPostalCode(postal);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        inputInfo.setBirthDate(sdf.format(new Date()));

        inputInfo.setImageUrl("https://static.highsnobiety.com/wp-content/uploads/2018/01/31094145/tommy-wiseau-the-room-3d-release-01-960x640.jpg");
        return inputInfo;
    }

    private Member createNewMember(boolean addToList) throws Exception {
        MemberInputInfo inputInfo = generateMemberInputInfo();
        String input = asJson(inputInfo);

        String response = mockMvc.perform(post("/members/create")
                .content(input)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        Member m = (Member) fromJson(response, Member.class);
        if (addToList)
            savedMembers.add(m);

        return m;
    }

    @Test
    @WithMockUser
    public void createMemberWithValidInputData() throws Exception {
        MemberInputInfo inputInfo = generateMemberInputInfo();
        String input = asJson(inputInfo);

        String response = mockMvc.perform(post("/members/create")
                .content(input)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        Member m = (Member) fromJson(response, Member.class);
        savedMembers.add(m);
        Assert.assertEquals(true, m != null && m.getId() != null && !m.getId().isEmpty());
    }

    @Test
    @WithMockUser
    public void createMemberWithInvalidInputData() throws Exception {
        MemberInputInfo inputInfo = generateMemberInputInfo();
        inputInfo.setBirthDate("12345");
        inputInfo.setPostalCode("0");

        String input = asJson(inputInfo);

        mockMvc.perform(post("/members/create")
                .content(input)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }


    // UPDATE CONTROLLER

    @Test
    @WithMockUser
    public void updateMemberWithValidInputData() throws Exception {
        Member m = createNewMember(true);
        String response = mockMvc.perform(patch("/members/" + m.getId()).content("{\"firstName\":\"patch_result\"}")
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();

        Assert.assertEquals("contains changed firstName", true, response.contains("patch_result"));

    }

    @Test
    @WithMockUser
    public void updateMemberWithInvalidInputData() throws Exception {

        Member m = createNewMember(true);
        mockMvc.perform(patch("/members/" + m.getId()).content("{\"postalCode\":\"333\"}")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());

    }

    // LIST CONTROLLER

    @Test
    @WithMockUser
    public void listMembers() throws Exception {
        Member m = createNewMember(true);

        String responseList = mockMvc.perform(get("/members/list")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        Assert.assertEquals("Is JSON Array", true, responseList.matches("\\[.*\\]"));
        Assert.assertEquals("Contains member code",
                true, responseList.contains(m.getId()));
    }

    // READ CONTROLLER

    @Test
    @WithMockUser
    public void readMember() throws Exception {

        Member m = createNewMember(true);

        String getResponse = mockMvc.perform(get("/members/" + m.getId()))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        Member getM = (Member) fromJson(getResponse, Member.class);

        Assert.assertEquals(true, m.getId().contentEquals(getM.getId()));

    }

    @Test(expected = NestedServletException.class)
    @WithMockUser
    public void readNotExistedMember() throws Exception {
        mockMvc.perform(get("/members/" + UUID.randomUUID().toString()));
    }


    @Test
    @WithMockUser
    public void readMembersImage() throws Exception {
        Member m = createNewMember(true);

        mockMvc.perform(get("/members/" + m.getId() + "/image"))
                .andExpect(content().contentType("image/jpg"));
    }

    //DELETE CONTROLLER

    @Test
    @WithMockUser
    public void deleteMember() throws Exception {
        Member m = createNewMember(false);
        mockMvc.perform(delete("/members/" + m.getId())).andExpect(status().isOk());
    }

    private String asJson(Object obj) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    private Object fromJson(String doc, Class<?> _class) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(doc, _class);
    }

}
