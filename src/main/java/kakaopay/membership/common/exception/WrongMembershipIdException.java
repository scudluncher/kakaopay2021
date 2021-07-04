package kakaopay.membership.common.exception;


///membershipId 가 spc, cj, shinsegye 중 어느것에도 속하지 않을 때 
public class WrongMembershipIdException extends RuntimeException {
    public WrongMembershipIdException() {
        super();
    }
}