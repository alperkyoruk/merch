package skylab.skymerch.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import skylab.skymerch.core.utilities.result.SuccessResult;
import skylab.skymerch.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import skylab.skymerch.business.abstracts.UserService;
import skylab.skymerch.business.constants.UserMessages;
import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.ErrorResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.dataAccess.UserDao;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import skylab.skymerch.entities.Role;


import java.util.List;
import java.util.Set;

@Service
public class UserManager implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserManager(UserDao userDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Result addUser(User user) {

        if(user.getUsername().isEmpty() || user.getPassword().isEmpty()){
            return new ErrorResult(UserMessages.userNameOrPasswordCannotBeNull);
        }

        if(userDao.existsByUsername(user.getUsername())){
            return new ErrorResult(UserMessages.usernameAlreadyExists);
        }

        if(user.getPassword().length() < 8){
            return new ErrorResult(UserMessages.passwordLengthMustBeGreaterThan8);
        }

        if(user.getAuthorities()==null){
            user.setAuthorities(Set.of(Role.ROLE_USER));
        }



        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userDao.save(user);

        return new SuccessResult(UserMessages.userAdded);
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
