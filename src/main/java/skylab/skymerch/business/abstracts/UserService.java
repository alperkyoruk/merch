package skylab.skymerch.business.abstracts;

import skylab.skymerch.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;

import java.util.List;

public interface UserService extends UserDetailsService {
    Result addUser(User user);

    Result deleteUser(int userId);

    Result updateUser(User user);

    DataResult<User> getUserById(int userId);

    DataResult<User> getUserByUsername(String username);

    DataResult<List<User>> getUsers();

    DataResult<List<User>> getUsersByRole(String role);


}
