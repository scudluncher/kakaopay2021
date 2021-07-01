package kakaopay.membership.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public class User {


    public User(){};
    
    @Id
    @Column(name="USER_ID")
    private String userId;

    public String getUserId() {
        return userId;
    }




    




}