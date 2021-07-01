package kakaopay.membership.common;

public enum MembershipType {
    
    spc("happypoint"),
    shinsegae("shinsegaepoint"),
    cj("cjone");


    private String membershipName;

    public String getMembershipName(){
        return membershipName;
    }

    MembershipType(String membershipName){
        this.membershipName = membershipName;
    };
}