package kakaopay.membership.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kakaopay.membership.domain.UserMembershipInfo;

@Repository
public interface UserMembershipInfoRepository extends JpaRepository<UserMembershipInfo, Long>{
    
    List<UserMembershipInfo> findByUser_UserId(String userId);
   
}