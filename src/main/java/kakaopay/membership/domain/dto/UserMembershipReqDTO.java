package kakaopay.membership.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.Min;

import org.springframework.validation.annotation.Validated;

import kakaopay.membership.common.MembershipType;
import kakaopay.membership.domain.User;
import kakaopay.membership.domain.UserMembershipInfo;

@Validated
public class UserMembershipReqDTO {
    
    public UserMembershipReqDTO(){}

    
    private String membershipId;
    private String membershipName;

    @Min(value=0, message="point value should be positve number")
    private BigDecimal point;

    @Min(value=0, message="amount value should not be negative number")
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
        BigDecimal pointAsBigDecimal = this.point;
        MembershipType membershipType = MembershipType.valueOf(this.membershipId);
        //create entity object to persist
        UserMembershipInfo membershipInfo = new UserMembershipInfo(null, membershipType, user, LocalDateTime.now(), pointAsBigDecimal,"Y");
        return membershipInfo;
    }


    
}
