package kakaopay.membership.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import kakaopay.membership.common.CustomResponse;
import kakaopay.membership.service.UserMembershipInfoService;
import kakaopay.membership.domain.dto.UserMembershipInfoDTO;

@RestController
public class MembershipRestController {
    
    @Autowired
    private UserMembershipInfoService userMembershipInfoService;


    // 멤버십 전체 조회 다음의 요건을 만족하는 나의 멤버십 조회 API 를 작성해주세요. 
    // ▪ 모든 멤버십을 조회합니다. 
    // ▪ 사용자 식별값을 입력값으로 받습니다. 
    // ▪ 나의 멤버십 조회 응답은 다음 내용을 포함합니다. 
    // ▪ 멤버십 ID, 멤버십 이름, 포인트, 멤버십상태(활성, 비활성), 가입 일시
    @GetMapping("/api/v1/membership")
    public CustomResponse getMembershipInfoByUserId(){//@RequestHeader(value="X-USER-ID") String userId
        
    CustomResponse rep = new CustomResponse();

        List <UserMembershipInfoDTO> dtoList = userMembershipInfoService.getMembershipInfoByUserId("test1");//userId
        rep.setSuccess("true");
        rep.setResponse(dtoList);

        return rep;
    }
}