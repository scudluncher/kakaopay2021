package kakaopay.membership.common;

import java.math.BigDecimal;

public enum MembershipType {
    

    //percntg may change later 
    spc("happypoint", BigDecimal.valueOf(0.01)),
    shinsegae("shinsegaepoint", BigDecimal.valueOf(0.01)),
    cj("cjone", BigDecimal.valueOf(0.01));


    private String membershipName;

    private BigDecimal percntg;

    public String getMembershipName(){
        return membershipName;
    }

    public BigDecimal getPercntg(){
        return percntg;
    }

    MembershipType(String membershipName, BigDecimal percntg){
        this.membershipName = membershipName;
        this.percntg = percntg;
    };
}