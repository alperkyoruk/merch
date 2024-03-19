package skylab.skymerch.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import skylab.skymerch.business.abstracts.UserService;
import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.dataAccess.UserDao;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.List;

@Service
public class UserManager implements UserService, UserDetailsService {

    private UserDao userDao;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserManager(UserDao userDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Result addUser(User user) {
        return null;
    }

    @Override
    public Result deleteUser(int userId) {
        return null;
    }

    @Override
    public Result updateUser(User user) {
        return null;
    }

    @Override
    public DataResult<User> getUserById(int userId) {
        return null;
    }

    @Override
    public DataResult<User> getUserByUsername(String username) {
        return null;
    }

    @Override
    public DataResult<List<User>> getUsers() {
        return null;
    }

    @Override
    public DataResult<List<User>> getUsersByRole(String role) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
