package kakaopay.membership.controller;

import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import kakaopay.membership.common.CustomResponse;
import kakaopay.membership.common.ErrorResponse;
import kakaopay.membership.common.MembershipType;
import kakaopay.membership.controller.MembershipRestController;
import kakaopay.membership.domain.User;
import kakaopay.membership.domain.UserMembershipInfo;
import kakaopay.membership.domain.dto.UserMembershipInfoDTO;
import kakaopay.membership.service.UserMembershipInfoService;


@ExtendWith(MockitoExtension.class) 
@DisplayName("MembershipRestController 테스트")
public class MembershipRestControllerTest {
    @InjectMocks
    private MembershipRestController membershipRestController;

    @Mock
    private UserMembershipInfoService userMembershipInfoService;



    
    @Test
    @DisplayName("ID로 멤버쉽 조회하여 양식에 맞춰 반환한다.")
    void getMembershipInfoByUserIdTest(){
        //given
        String userId = "test1";
        User user = new User(userId);
        UserMembershipInfo info1 = new UserMembershipInfo(Long.valueOf(1),MembershipType.spc,user,LocalDateTime.now(),BigDecimal.valueOf(120),"Y");
        UserMembershipInfo info2 = new UserMembershipInfo(Long.valueOf(2),MembershipType.cj,user,LocalDateTime.now().minusDays(1),BigDecimal.valueOf(3500),"Y");
        UserMembershipInfo info3 = new UserMembershipInfo(Long.valueOf(3),MembershipType.shinsegae,user,LocalDateTime.now().minusDays(2),BigDecimal.valueOf(1029),"N");
        UserMembershipInfoDTO dto1 = new UserMembershipInfoDTO(info1, 1);
        UserMembershipInfoDTO dto2 = new UserMembershipInfoDTO(info2, 2);
        UserMembershipInfoDTO dto3 = new UserMembershipInfoDTO(info3, 3);
        List<UserMembershipInfoDTO> dtoList = new ArrayList<UserMembershipInfoDTO>();
        dtoList.add(dto1);
        dtoList.add(dto2);
        dtoList.add(dto3);

        given(userMembershipInfoService.getMembershipInfoByUserIdAsDTO(userId)).willReturn(dtoList);

        final boolean success = true;
        final ErrorResponse errRep = null;
        ResponseEntity<CustomResponse> result = membershipRestController.getMembershipInfoByUserId(userId);
        CustomResponse rep = result.getBody() ;
        Assertions.assertEquals(success,rep.getSuccess());
        Assertions.assertEquals(errRep,rep.getError());
        Assertions.assertNotNull(rep.getResponse());
    }







}
