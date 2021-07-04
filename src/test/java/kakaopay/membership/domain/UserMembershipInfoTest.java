package kakaopay.membership.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kakaopay.membership.common.MembershipType;

public class UserMembershipInfoTest {
    
    @Test
    @DisplayName("정해진 퍼센티이지를 금액에 곱하고 기존 포인트에 더한다")
    void getPercntgAndMultiplyAmuntAndAdd(){
        BigDecimal amount = BigDecimal.valueOf(10000);
        BigDecimal initialPoint = BigDecimal.valueOf(1000);
        final MembershipType membershipType = MembershipType.shinsegae;
        User user = new User("userId1");
        UserMembershipInfo userMembershipInfo = new UserMembershipInfo(Long.valueOf(999), membershipType, user, LocalDateTime.now(), initialPoint, "Y");


        userMembershipInfo.addPoint(amount);

        Assertions.assertEquals(initialPoint.add(amount.multiply(membershipType.getPercntg())),userMembershipInfo.getPoint());


    }

}
