package kakaopay.membership.service;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import kakaopay.membership.common.MembershipType;
import kakaopay.membership.domain.User;
import kakaopay.membership.domain.UserMembershipInfo;
import kakaopay.membership.repository.UserMembershipInfoRepository;
import kakaopay.membership.service.UserMembershipInfoService;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class UserMembershipInfoServiceTest {
    

    @InjectMocks
    UserMembershipInfoService userMembershipInfoService;

    @Mock
    UserMembershipInfoRepository userMembershipInfoRepository;


    @Test
    @DisplayName("유저의 멤버쉽 정보를 Entity 로 가져온다")
    void getMembershipInfoByUserId(){
        String userId = "test1";
        User user = new User(userId);
        UserMembershipInfo info1 = new UserMembershipInfo(Long.valueOf(1),MembershipType.spc,user,LocalDateTime.now(),BigDecimal.valueOf(120),"Y");
        UserMembershipInfo info2 = new UserMembershipInfo(Long.valueOf(2),MembershipType.cj,user,LocalDateTime.now(),BigDecimal.valueOf(3500),"Y");
        UserMembershipInfo info3 = new UserMembershipInfo(Long.valueOf(3),MembershipType.shinsegae,user,LocalDateTime.now(),BigDecimal.valueOf(1029),"N");

        List <UserMembershipInfo> membershipList = new ArrayList<UserMembershipInfo>();
        membershipList.add(info1);
        membershipList.add(info2);
        membershipList.add(info3);

        given(userMembershipInfoRepository.findByUser_UserId(userId)).willReturn(membershipList);
        List<UserMembershipInfo> membershitListByGet = userMembershipInfoService.getMembershipInfoByUserId(userId);
        Assertions.assertEquals(membershipList, membershitListByGet);



    }



}