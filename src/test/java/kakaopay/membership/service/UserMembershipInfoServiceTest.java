package kakaopay.membership.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import kakaopay.membership.common.MembershipType;
import kakaopay.membership.common.exception.AlreadyRegisteredMembershipException;
import kakaopay.membership.common.exception.NotRegisteredMembershipException;
import kakaopay.membership.common.exception.NotRegisteredUserException;
import kakaopay.membership.domain.User;
import kakaopay.membership.domain.UserMembershipInfo;
import kakaopay.membership.domain.dto.UserMembershipReqDTO;
import kakaopay.membership.repository.UserMembershipInfoRepository;
import kakaopay.membership.repository.UserRepository;

@ExtendWith(SpringExtension.class)
public class UserMembershipInfoServiceTest {
    
    private static final String userId ="test1";
    private static final String membershipActivated = "Y";
    private static final String membershipDeactivated = "N";

    @InjectMocks
    UserMembershipInfoService userMembershipInfoSvc;

    @Mock
    UserMembershipInfoRepository membershipInfoRepo;

    @Mock
    UserRepository userRepo;

    


    @Test
    @DisplayName("유저의 멤버쉽 정보를 Entity 로 가져온다")
    void getMembershipInfoByUserId(){
        User user = new User(userId);
        UserMembershipInfo info1 = new UserMembershipInfo(Long.valueOf(1),MembershipType.spc,user,LocalDateTime.now(),BigDecimal.valueOf(120),"Y");
        UserMembershipInfo info2 = new UserMembershipInfo(Long.valueOf(2),MembershipType.cj,user,LocalDateTime.now(),BigDecimal.valueOf(3500),"Y");
        UserMembershipInfo info3 = new UserMembershipInfo(Long.valueOf(3),MembershipType.shinsegae,user,LocalDateTime.now(),BigDecimal.valueOf(1029),"N");

        List <UserMembershipInfo> membershipList = new ArrayList<UserMembershipInfo>();
        membershipList.add(info1);
        membershipList.add(info2);
        membershipList.add(info3);

        given(membershipInfoRepo.findByUser_UserId(userId)).willReturn(membershipList);
        List<UserMembershipInfo> membershipListByGet = userMembershipInfoSvc.getMembershipInfoByUserId(userId);
        Assertions.assertEquals(membershipList, membershipListByGet);
    }

    @Test
    @DisplayName("유저의 멤버쉽 정보를 검색하나 저장된 값이 없을 때 NotRegisteredMembershipException 발생")
    void getMembershipInfoByUserIdButNoResult_then_NoSuchElementException(){
        List<UserMembershipInfo> list = Collections.emptyList();
        given(membershipInfoRepo.findByUser_UserId(userId)).willReturn(list);
        Assertions.assertThrows(NotRegisteredMembershipException.class, ()->{userMembershipInfoSvc.getMembershipInfoByUserId(userId);});
    }



    @DisplayName("멤버쉽 정보를 신규로 생성한다.")
    @Test
    void createMembershipInfoByUserId(){
        final MembershipType membershipType = MembershipType.shinsegae;
        final String membershipActivated = "Y";
        final Long membershipPk = Long.valueOf(999);
        final User user = new User(userId);

        UserMembershipReqDTO reqDto = new UserMembershipReqDTO();
        ReflectionTestUtils.setField(reqDto, "membershipId", "cj");
        ReflectionTestUtils.setField(reqDto, "point", BigDecimal.valueOf(333));

        UserMembershipInfo toBeSavedInfo = new UserMembershipInfo(membershipPk, membershipType, user, LocalDateTime.now(), BigDecimal.valueOf(999), membershipActivated);
        
        given(userRepo.findById(userId)).willReturn(Optional.ofNullable(user));
        given(membershipInfoRepo.findByUser_userIdAndMembershipType(userId, membershipType)).willReturn(toBeSavedInfo);
        given(membershipInfoRepo.save(any())).willReturn(toBeSavedInfo);
        UserMembershipInfo savedInfo = userMembershipInfoSvc.createMembershipInfoByUserId(userId, reqDto);
        

        Assertions.assertEquals(toBeSavedInfo, savedInfo);
      
    }

    @Test
    @DisplayName("멤버쉽 정보 생성을 시도하나 이미 등록된 정보가 있는 경우 AlreadyRegisteredMembershipException 발생")
    void createMembershipButAlreadyExists_then_AlreadyRegisteredMembershipException(){
        final MembershipType membershipType = MembershipType.shinsegae;
        final String membershipActivated = "Y";
        final Long membershipPk = Long.valueOf(999);
        final User user = new User(userId);   
        UserMembershipReqDTO reqDto = new UserMembershipReqDTO();
        ReflectionTestUtils.setField(reqDto, "membershipId", "cj");

        UserMembershipInfo toBeSavedInfo = new UserMembershipInfo(membershipPk, membershipType, user, LocalDateTime.now(), BigDecimal.valueOf(999), membershipActivated);
      
        Assertions.assertThrows(AlreadyRegisteredMembershipException.class,
                                ()->{
                                    given(userRepo.findById(userId)).willReturn(Optional.ofNullable(user));
                                    given(membershipInfoRepo.findByUser_userIdAndMembershipType(userId, MembershipType.valueOf("cj"))).willReturn(toBeSavedInfo);
                                    userMembershipInfoSvc.createMembershipInfoByUserId(userId, reqDto);});
    }

    @Test
    @DisplayName("멤버쉽 정보 생성을 시도하나 등록되지 않은 id 인 경우 NotRegisteredUserException 발생")
    void createMembershipButAlreadyExists_then_NotRegisteredUserException(){
        Optional<User> emptyUser = Optional.empty();
        given(userRepo.findById(userId)).willReturn(emptyUser);

        Assertions.assertThrows(NotRegisteredUserException.class, 
                                ()->{userMembershipInfoSvc.createMembershipInfoByUserId(userId, any(UserMembershipReqDTO.class));});
    }


    @Test
    @DisplayName("멤버쉽 정보를 비활성화한다.")
    void deactivateMembership(){
        final MembershipType membershipType = MembershipType.shinsegae;
        
        User user = new User(userId);
        UserMembershipInfo savedInfo = new UserMembershipInfo(Long.valueOf(999), membershipType, user, LocalDateTime.now(), BigDecimal.valueOf(999), membershipActivated);
        given(membershipInfoRepo.findByUser_userIdAndMembershipType(userId,membershipType)).willReturn(savedInfo);
        userMembershipInfoSvc.deactivateMembershipInfoByUserId(userId, membershipType.name()); 
    
        Assertions.assertEquals(membershipDeactivated, savedInfo.getMembershipStatus());
        Assertions.assertNotEquals(membershipActivated, savedInfo.getMembershipStatus());
        
    }



    @Test
    @DisplayName("멤버쉽 정보를 비활성 시도하나, 등록된 정보가 없는 경우 NotRegisteredMembershipException 발생")
    void deactivateMemebershipButNoRegistered_then_NotRegisteredMembershipException(){
        final MembershipType membershipType = MembershipType.cj;
        UserMembershipInfo info = null;
        given(membershipInfoRepo.findByUser_userIdAndMembershipType(userId,membershipType)).willReturn(info);
        Assertions.assertThrows(NotRegisteredMembershipException.class, ()->{userMembershipInfoSvc.deactivateMembershipInfoByUserId(userId, membershipType.name());});
    }

}
