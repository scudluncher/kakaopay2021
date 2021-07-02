package kakaopay.membership.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kakaopay.membership.common.MembershipType;
import kakaopay.membership.domain.User;
import kakaopay.membership.domain.UserMembershipInfo;
import kakaopay.membership.domain.dto.UserMembershipInfoDTO;
import kakaopay.membership.domain.dto.UserMembershipReqDTO;
import kakaopay.membership.repository.UserMembershipInfoRepository;
import kakaopay.membership.repository.UserRepository;

@Service
public class UserMembershipInfoService {

    @Autowired
    private UserMembershipInfoRepository membershipRepo;
    
    @Autowired
    private UserRepository userRepository;

    
    public List<UserMembershipInfoDTO> getMembershipInfoByUserIdAsDTO(String userId){
        List <UserMembershipInfo> list = this.getMembershipInfoByUserId(userId);
        AtomicInteger count=new AtomicInteger(0);
        List <UserMembershipInfoDTO> dtoList = list.stream()
                                                    .map(info-> new UserMembershipInfoDTO(info, count.incrementAndGet()))
                                                    .collect(Collectors.toList());
        return dtoList;
    }

    public List<UserMembershipInfo> getMembershipInfoByUserId(String userId){
        return membershipRepo.findByUser_UserId(userId);
    }


    public UserMembershipInfo saveMembershipInfoByUserId(String userId, UserMembershipReqDTO reqDto ){
        User user = userRepository.findById(userId).orElseThrow(()->new NoSuchElementException());
        UserMembershipInfo membershipInfo = reqDto.toPostEntity(user);
        return membershipRepo.save(membershipInfo);
    }

    public void deactivateMembershipInfoByUserId(String userId, String membershipId){
        UserMembershipInfo savedMembershipInfo = membershipRepo.findByUser_userIdAndMembershipType(userId, MembershipType.valueOf(membershipId));
        membershipRepo.delete(savedMembershipInfo);        
    }


    public UserMembershipInfoDTO getMembershipByMembershipIdAndUserId(String userId, String membershipId){
        UserMembershipInfo savedMembershipInfo = membershipRepo.findByUser_userIdAndMembershipType(userId, MembershipType.valueOf(membershipId));
        UserMembershipInfoDTO infoDto = new UserMembershipInfoDTO(savedMembershipInfo, 1);
        return infoDto;
    }

    @Transactional
    public void addMembershipPointDependingAmountForUser(String userId, UserMembershipReqDTO reqDto){
        UserMembershipInfo savedMembershipInfo = membershipRepo.findByUser_userIdAndMembershipType(userId, MembershipType.valueOf(reqDto.getMembershipId()));
        savedMembershipInfo.addPoint(reqDto.getAmount());
    }
    
    

}