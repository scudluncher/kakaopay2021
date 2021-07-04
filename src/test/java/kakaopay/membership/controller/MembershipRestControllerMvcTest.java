package kakaopay.membership.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import kakaopay.membership.MembershipApplication;

@ContextConfiguration(classes={MembershipApplication.class})
@SpringBootTest
@AutoConfigureMockMvc
public class MembershipRestControllerMvcTest {
   
    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("request Header 에 user ID 를 통해 조회")
    void getMembershipInfoByUserIdWithIdTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/membership")
                            .header("X-USER-ID", "test1")
                            .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(jsonPath("$.success").value(true))
                            .andExpect(jsonPath("$.error").value(IsNull.nullValue()))
                            .andExpect(jsonPath("$.response").exists()); 
    
    }


    @Test
    @DisplayName("멤버쉽 정보 등록")
    void saveMembershipInfoByUserIdTest() throws Exception{

        int point = 52000;
        ObjectNode reqInfoJson = objectMapper.createObjectNode();
        reqInfoJson.put("membershipId", "cj");
        reqInfoJson.put("membershipName", "cjone");
        reqInfoJson.put("point", point);
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(reqInfoJson);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/membership")
                            .header("X-USER-ID", "test2")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                            .andExpect(jsonPath("$.success").value(true))
                            .andExpect(jsonPath("$..point").value(point))
                            .andExpect(jsonPath("$.error").value(IsNull.nullValue()))
                            .andExpect(jsonPath("$.response").exists()); 
    
    }

    @Test
    @DisplayName("멤버십 비활성화 하기")
    void deactivateMembershipInfoByUserIdTest() throws Exception{
        final String membershipIdParameter ="cj";
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/membership/"+membershipIdParameter)
                            .header("X-USER-ID", "test1")
                            .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(jsonPath("$.success").value(true))
                            .andExpect(jsonPath("$.error").value(IsNull.nullValue()))
                            .andExpect(jsonPath("$.response").value(true)); 
    }

    @Test
    @DisplayName("멤버십 1개 상세 조회하기")
    void getMembershipByMembershipIdAndUserIdTest() throws Exception{
        final String membershipIdParameter ="cj";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/membership/"+membershipIdParameter)
                            .header("X-USER-ID", "test1")
                            .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(jsonPath("$.success").value(true))
                            .andExpect(jsonPath("$.error").value(IsNull.nullValue()))
                            .andExpect(jsonPath("$.response").exists())
                            .andExpect(jsonPath("$..membershipId").value(membershipIdParameter)); 

    }

    @Test
    @DisplayName("포인트 적립하기")
    void addMembershipPointDependingAmountForUser() throws Exception{
        int amount = 100000;
        ObjectNode reqInfoJson = objectMapper.createObjectNode();
        reqInfoJson.put("membershipId", "cj");
        reqInfoJson.put("amount", amount);
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(reqInfoJson);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/membership/point")
                            .header("X-USER-ID", "test1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                            .andExpect(jsonPath("$.success").value(true))
                            .andExpect(jsonPath("$.error").value(IsNull.nullValue()))
                            .andExpect(jsonPath("$.response").value(true)); 

    }


    
}
