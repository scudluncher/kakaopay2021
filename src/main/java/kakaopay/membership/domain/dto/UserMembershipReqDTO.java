package kakaopay.membership.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import kakaopay.membership.common.MembershipType;
import kakaopay.membership.domain.User;
import kakaopay.membership.domain.UserMembershipInfo;

public class UserMembershipReqDTO {
    
    public UserMembershipReqDTO(){}

    private String membershipId;
    private String membershipName;
    private BigDecimal point;
    private BigDecimal amount;



    public String getMembershipId() {
        return membershipId;
    }

    public String getMembershipName() {
        return membershipName;
    }

    public BigDecimal getPoint() {
        return point;
    }

    public BigDecimal getAmount() {
        return amount;
    }


    public UserMembershipInfo toPostEntity(User user){
        BigDecimal pointAsBigDecimal = this.point;//new BigDecimal(this.point);
        MembershipType membershipType = MembershipType.valueOf(this.membershipId);
        //create entity object to persist
        UserMembershipInfo membershipInfo = new UserMembershipInfo(null, membershipType, user, LocalDateTime.now(), pointAsBigDecimal,"Y");
        return membershipInfo;
    }


    
}
