package kyoongdev.rolling_bites.modules.user.repository;

import java.util.Optional;
import kyoongdev.rolling_bites.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findUserByNickname(String nickname);

  Optional<User> findUserBySocialId(String socialId);


}
