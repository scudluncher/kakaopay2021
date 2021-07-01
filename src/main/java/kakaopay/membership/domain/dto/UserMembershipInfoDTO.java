package kakaopay.membership.domain.dto;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import kakaopay.membership.domain.UserMembershipInfo;



public class UserMembershipInfoDTO {
    
    
    private int seq;  // it is for display sequence 
    private String membershipId;
    private String userId;
    private String membershipName;
    private LocalDateTime startDate;
    private BigDecimal point;  
    private String membershipStatus;


    public UserMembershipInfoDTO(UserMembershipInfo info, int seq) {
        this.seq = seq;
        this.userId = info.getUser().getUserId();
        this.membershipId = info.getMembershipType().name();
        this.membershipName = info.getMembershipType().getMembershipName();
        this.startDate = info.getStartDate();
        this.point = info.getPoint();
        this.membershipStatus = info.getMembershipStatus();
    }




    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMembershipName() {
        return membershipName;
    }

    public void setMembershipName(String membershipName) {
        this.membershipName = membershipName;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public BigDecimal getPoint() {
        return point;
    }

    public void setPoint(BigDecimal point) {
        this.point = point;
    }

    public String getMembershipStatus() {
        return membershipStatus;
    }

    public void setMembershipStatus(String membershipStatus) {
        this.membershipStatus = membershipStatus;
    }

    



}