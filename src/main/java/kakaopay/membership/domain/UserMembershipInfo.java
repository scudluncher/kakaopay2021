package kakaopay.membership.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import kakaopay.membership.common.MembershipType;

@Entity
@Table(name="USER_MEMBERSHIP_INFO")
public class UserMembershipInfo {
    
    public UserMembershipInfo(){};
    
    @Id
    @Column(name="INFO_SEQ")
    private Long infoSeq;

    @Column(name="MEMBERSHIP_TYPE")
    @Enumerated(EnumType.STRING)
    private MembershipType membershipType;

    @ManyToOne
    @JoinColumn(name="USER_ID",referencedColumnName = "USER_ID")
    private User user;

    @Column(name="START_DATE")
    private LocalDateTime startDate;

    @Column(name="POINT")
    private BigDecimal point;

    @Column(name="MEMBERSHIP_STATUS")
    private String membershipStatus;

    public Long getInfoSeq() {
        return infoSeq;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public BigDecimal getPoint() {
        return point;
    }

    public String getMembershipStatus() {
        return membershipStatus;
    }


    


}