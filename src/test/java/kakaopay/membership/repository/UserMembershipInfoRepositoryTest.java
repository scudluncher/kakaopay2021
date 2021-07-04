package kakaopay.membership.repository;


import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import kakaopay.membership.common.MembershipType;
import kakaopay.membership.domain.UserMembershipInfo;

@DataJpaTest
@AutoConfigureTestDatabase
public class UserMembershipInfoRepositoryTest {
    
    private static final String userId="test1"; 

    @Autowired
    private UserMembershipInfoRepository membershipInfoRepo;

    //custom declared methods test in UserMembershipInfoRepository
    @Test
    @DisplayName("id 로 저장된 멤버쉽 모두 조회")
    void findByUser_UserIdTest(){
        List<UserMembershipInfo> savedMembershipList= membershipInfoRepo.findByUser_UserId(userId);
        Assertions.assertNotNull(savedMembershipList);
    }

    @Test
    @DisplayName("id 와 멤버쉽 id 로 특정 멤버쉽을 조회")
    void findByUser_userIdAndMembershipTypeTest(){
        final MembershipType membershipType = MembershipType.cj;
        UserMembershipInfo membershipInfo = membershipInfoRepo.findByUser_userIdAndMembershipType(userId, membershipType);
        Assertions.assertNotNull(membershipInfo);
    }
 
 
}
