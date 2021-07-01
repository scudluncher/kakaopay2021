package kakaopay.membership.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kakaopay.membership.domain.UserMembershipInfo;
import kakaopay.membership.domain.dto.UserMembershipInfoDTO;
import kakaopay.membership.repository.UserMembershipInfoRepository;

@Service
public class UserMembershipInfoService {

    @Autowired
    private UserMembershipInfoRepository userMembershipInfoRepository;

    
    public List<UserMembershipInfoDTO> getMembershipInfoByUserIdAsDTO(String userId){
        List <UserMembershipInfo> list = this.getMembershipInfoByUserId(userId);
        AtomicInteger count=new AtomicInteger(0);
        List <UserMembershipInfoDTO> dtoList = list.stream()
                                                    .map(info-> new UserMembershipInfoDTO(info, count.incrementAndGet()))
                                                    .collect(Collectors.toList());
        return dtoList;
    }

    public List<UserMembershipInfo> getMembershipInfoByUserId(String userId){
        return userMembershipInfoRepository.findByUser_UserId(userId);

    }
    
    

}