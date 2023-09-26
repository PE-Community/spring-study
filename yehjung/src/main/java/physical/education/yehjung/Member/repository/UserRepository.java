package physical.education.yehjung.Member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import physical.education.yehjung.Member.dto.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByNickname(String nickname);
}
