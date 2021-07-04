package kakaopay.membership.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.Min;

import org.springframework.validation.annotation.Validated;

import kakaopay.membership.common.MembershipType;
import kakaopay.membership.common.exception.WrongMembershipIdException;
import kakaopay.membership.domain.User;
import kakaopay.membership.domain.UserMembershipInfo;

@Validated
public class UserMembershipReqDTO {
    
    public UserMembershipReqDTO(){}

    
    private String membershipId;
    private String membershipName;

    @Min(value=0, message="check point value")
    private BigDecimal point;

    @Min(value=0, message="check amount value")
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
        try{
            MembershipType membershipType = MembershipType.valueOf(this.membershipId);
            //create entity object to persist
            UserMembershipInfo membershipInfo = new UserMembershipInfo(null, membershipType, user, LocalDateTime.now(), point,"Y");
            return membershipInfo;
        }catch(IllegalArgumentException e){
            throw new WrongMembershipIdException();
        }

       
    }


    
}
