package kakaopay.membership.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

    @Test
    @DisplayName("request Header 에 user ID 가 없으면 실패 반환한다")
    void getMembershipInfoByUserIdWithoutId() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/membership")
                            .header("X-USER-ID", "")
                            .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(jsonPath("$.success").value(false))
                            .andExpect(jsonPath("$.error").exists())
                            .andExpect(jsonPath("$.error.message").value("membershipId must be provided"))
                            .andExpect(jsonPath("$.error.status").value(400))
                            .andExpect(jsonPath("$.response").doesNotExist()); 
    
    
    }


    @Test
    @DisplayName("request Header 에 user ID 를 통해 조회")
    void getMembershipInfoByUserIdWithId() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/membership")
                            .header("X-USER-ID", "test1")
                            .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(jsonPath("$.success").value(true))
                            // .andExpect(jsonPath("$.error").doesNotExist())
                            // .andExpect(jsonPath("$.error.message").value("membershipId must be provided"))
                            // .andExpect(jsonPath("$.error.status").value(400))
                            .andExpect(jsonPath("$.response").exists()); 
    
    
    }
    
}
