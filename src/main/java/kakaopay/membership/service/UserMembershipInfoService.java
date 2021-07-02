package kakaopay.membership.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        List<UserMembershipInfo> infoList = membershipRepo.findByUser_UserId(userId);
        if(infoList.isEmpty()){
            throw new NoSuchElementException();
        }
        return infoList;
    }


    public UserMembershipInfo createMembershipInfoByUserId(String userId, UserMembershipReqDTO reqDto){
        User user = userRepository.findById(userId).orElseThrow(()->new NoSuchElementException());
        UserMembershipInfo membershipInfo = reqDto.toPostEntity(user);
        return membershipRepo.save(membershipInfo);
    }


    @Transactional
    public void deactivateMembershipInfoByUserId(String userId, String membershipId) {
        UserMembershipInfo savedMembershipInfo = findMembershipByUserIdAndMembershipId(userId, membershipId);
        savedMembershipInfo.deactivateMembership();
    }




    public UserMembershipInfoDTO getMembershipByMembershipIdAndUserId(String userId, String membershipId){
        UserMembershipInfo savedMembershipInfo = findMembershipByUserIdAndMembershipId(userId, membershipId);
        UserMembershipInfoDTO infoDto = new UserMembershipInfoDTO(savedMembershipInfo, 1);
        return infoDto;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    private UserMembershipInfo findMembershipByUserIdAndMembershipId(String userId, String membershipId){
        UserMembershipInfo membershipInfo = membershipRepo.findByUser_userIdAndMembershipType(userId, MembershipType.valueOf(membershipId));
        if (membershipInfo==null){
            throw new NoSuchElementException();
        }else {
            return membershipInfo;
        }
    }


    @Transactional
    public void addMembershipPointDependingAmountForUser(String userId, UserMembershipReqDTO reqDto) throws NoSuchElementException{
        UserMembershipInfo savedMembershipInfo = findMembershipByUserIdAndMembershipId(userId, reqDto.getMembershipId());
        savedMembershipInfo.addPoint(reqDto.getAmount());
    }
    
    

}