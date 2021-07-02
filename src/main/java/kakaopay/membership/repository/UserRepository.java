package kakaopay.membership.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kakaopay.membership.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User,String>{
    
}
