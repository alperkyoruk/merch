package skylab.skymerch.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

public interface UserDao extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findById(int id);

}
