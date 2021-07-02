package kakaopay.membership.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kakaopay.membership.common.MembershipType;
import kakaopay.membership.domain.User;
import kakaopay.membership.domain.UserMembershipInfo;

public class UserMembershipInfoDTOTest {
    
    @Test
    @DisplayName("DTO 변환이 정상적으로 이루어진다.")
    void DtoConstructorTest(){

        final int seq = 1;
        final LocalDateTime timeStamp = LocalDateTime.now();
        final BigDecimal point = BigDecimal.valueOf(999);
        final String userId = "test1";
        final User user = new User(userId);
        final String membershipStatus = "Y";
        final MembershipType membershipType = MembershipType.cj;

        UserMembershipInfoDTO expectedDTO = new UserMembershipInfoDTO();
        expectedDTO.setSeq(seq);
        expectedDTO.setUserId(userId);
        expectedDTO.setMembershipId("cj");
        expectedDTO.setMembershipName("cjone");
        expectedDTO.setStartDate(timeStamp);
        expectedDTO.setPoint(point);
        expectedDTO.setMembershipStatus(membershipStatus);

        UserMembershipInfo targetEntity = new UserMembershipInfo(Long.valueOf(1), membershipType, user, timeStamp, point, membershipStatus);
        UserMembershipInfoDTO convertedDTO = new UserMembershipInfoDTO(targetEntity, seq);

        Assertions.assertEquals(expectedDTO.getMembershipId(), convertedDTO.getMembershipId());
        Assertions.assertEquals(expectedDTO.getMembershipName(), convertedDTO.getMembershipName());
        Assertions.assertEquals(expectedDTO.getUserId(), convertedDTO.getUserId());

    }
}
