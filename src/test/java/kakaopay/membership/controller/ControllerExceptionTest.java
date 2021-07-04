package kakaopay.membership.controller;


import javax.validation.ConstraintViolationException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;

import kakaopay.membership.MembershipApplication;
import kakaopay.membership.common.CustomResponse;
import kakaopay.membership.common.exception.WrongMembershipIdException;

@ContextConfiguration(classes={MembershipApplication.class})
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerExceptionTest {
    

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("request Header 에 user ID 값이 비어있으면 ConstraintViolationException 발생, 400 리턴,  @Size(min=1, message= userIdCheck) String userId 들어간 항목 전체 테스트를 대표.")
    void noXUserIDHeaderValueThenReturn400() throws Exception  {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/membership")
                                            .header("X-USER-ID", "")
                                            .contentType(MediaType.APPLICATION_JSON))
                                            .andExpect(result->Assertions.assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
                                            .andExpect(result->Assertions.assertEquals(result.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value()));

        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        CustomResponse rep = objectMapper.readValue(contentAsString, CustomResponse.class);
        Assertions.assertTrue(rep.getError().getMessage().indexOf("X-USER-ID is blank")>=0);
        

    }

    @Test
    @DisplayName("request Header 에 X-USER-ID 항목 자체가 없으면 MissingRequestHeaderException 발생, 400 리턴,  @RequestHeader(value='X-USER-ID') 들어간 항목 전체 테스트를 대표.")
    void noExistXUserIdHeaderThenReturn400() throws Exception{
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/membership")
                                            .contentType(MediaType.APPLICATION_JSON))
                                            .andExpect(result->Assertions.assertTrue(result.getResolvedException() instanceof MissingRequestHeaderException))
                                            .andExpect(result->Assertions.assertEquals(result.getResponse().getStatus(),HttpStatus.BAD_REQUEST.value()));
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        CustomResponse rep = objectMapper.readValue(contentAsString, CustomResponse.class);
        Assertions.assertTrue("X-USER-ID header is required".equals(rep.getError().getMessage()));
    }

    @Test
    @DisplayName("pathaVariable 이 전달되지 않은 경우 HttpRequestMethodNotSupportedException 발생, 400 리턴 ")
    void noPathVariableThenReturn400() throws Exception{
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/membership/")
                                            .header("X-USER-ID", "test1")
                                            .contentType(MediaType.APPLICATION_JSON))
                                            .andExpect(result->Assertions.assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                                            .andExpect(result->Assertions.assertEquals(result.getResponse().getStatus(),HttpStatus.BAD_REQUEST.value()));
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        CustomResponse rep = objectMapper.readValue(contentAsString, CustomResponse.class);
        Assertions.assertTrue("check its method or pathvariable parameter".equals(rep.getError().getMessage()));
    }

    @Test
    @DisplayName("amount 가 음수로 전달될 경우 MethodArgumentNotValidException 발생, 400 리턴")
    void negativeAmountPassedThenReturn400() throws Exception{
        
        ObjectNode reqInfoJson = objectMapper.createObjectNode();
        reqInfoJson.put("membershipId", "cj");
        reqInfoJson.put("amount", -1000);
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(reqInfoJson);



        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/membership/point")
                                                                            .header("X-USER-ID", "test1")
                                                                            .contentType(MediaType.APPLICATION_JSON)
                                                                            .content(json))
                                                                            .andExpect(result->Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                                                                            .andExpect(result->Assertions.assertEquals(result.getResponse().getStatus(),HttpStatus.BAD_REQUEST.value()));
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        CustomResponse rep = objectMapper.readValue(contentAsString, CustomResponse.class);
        Assertions.assertTrue("check amount value".equals(rep.getError().getMessage()));
    }
    

    @Test
    @DisplayName("point 가 음수로 전달될 경우 MethodArgumentNotValidException 발생, 400 리턴")
    void negativePointPassedThenReturn400() throws Exception{
        
        ObjectNode reqInfoJson = objectMapper.createObjectNode();
        reqInfoJson.put("membershipId", "cj");
        reqInfoJson.put("membershipName","cjone");
        reqInfoJson.put("point", -1000);
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(reqInfoJson);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/membership")
                                                                            .header("X-USER-ID", "test1")
                                                                            .contentType(MediaType.APPLICATION_JSON)
                                                                            .content(json))
                                                                            .andExpect(result->Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                                                                            .andExpect(result->Assertions.assertEquals(result.getResponse().getStatus(),HttpStatus.BAD_REQUEST.value()));
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        CustomResponse rep = objectMapper.readValue(contentAsString, CustomResponse.class);
        Assertions.assertTrue("check point value".equals(rep.getError().getMessage()));
    }
    

    @Test
    @DisplayName("membershipId 가 spc, cj, shinsegye 중 어느것에도 속하지 않을 때 WrongMembershipIdException 발생, 400 리턴")
    void wrongMembershipIdPassedThenReturn400() throws Exception{
        ObjectNode reqInfoJson = objectMapper.createObjectNode();
        reqInfoJson.put("membershipId", "xxx");
        reqInfoJson.put("membershipName","cjone");
        reqInfoJson.put("point", 1000);
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(reqInfoJson);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/membership")
                                                                            .header("X-USER-ID", "test1")
                                                                            .contentType(MediaType.APPLICATION_JSON)
                                                                            .content(json))
                                                                            .andExpect(result->Assertions.assertTrue(result.getResolvedException() instanceof WrongMembershipIdException))
                                                                            .andExpect(result->Assertions.assertEquals(result.getResponse().getStatus(),HttpStatus.BAD_REQUEST.value()));
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        CustomResponse rep = objectMapper.readValue(contentAsString, CustomResponse.class);
        Assertions.assertTrue("check membership id".equals(rep.getError().getMessage()));
    }

    @Test
    @DisplayName("membershipId 가 비어있는 경우 WrongMembershipIdException 발생, 400 리턴")
    void blankMembershipIdPassedThenReturn400() throws Exception{
        ObjectNode reqInfoJson = objectMapper.createObjectNode();
        reqInfoJson.put("membershipId", "");
        reqInfoJson.put("membershipName","cjone");
        reqInfoJson.put("point", 1000);
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(reqInfoJson);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/membership")
                                                                            .header("X-USER-ID", "test1")
                                                                            .contentType(MediaType.APPLICATION_JSON)
                                                                            .content(json))
                                                                            .andExpect(result->Assertions.assertTrue(result.getResolvedException() instanceof WrongMembershipIdException))
                                                                            .andExpect(result->Assertions.assertEquals(result.getResponse().getStatus(),HttpStatus.BAD_REQUEST.value()));
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        CustomResponse rep = objectMapper.readValue(contentAsString, CustomResponse.class);
        Assertions.assertTrue("check membership id".equals(rep.getError().getMessage()));
    }

}



