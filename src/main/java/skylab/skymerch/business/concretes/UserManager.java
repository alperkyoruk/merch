package skylab.skymerch.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import skylab.skymerch.business.abstracts.AddressService;
import skylab.skymerch.core.utilities.result.*;
import skylab.skymerch.entities.Dtos.RequestUserDto;
import skylab.skymerch.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import skylab.skymerch.business.abstracts.UserService;
import skylab.skymerch.business.constants.UserMessages;
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
    public Result addUser(RequestUserDto requestUserDto) {

        User user = User.builder()
                .firstName(requestUserDto.getFirstName())
                .lastName(requestUserDto.getLastName())
                .username(requestUserDto.getUsername())
                .password(passwordEncoder.encode(requestUserDto.getPassword()))
                .email(requestUserDto.getEmail())
                .userType("User")
                .phone(requestUserDto.getPhone())
                .authorities(Set.of(Role.ROLE_USER))
                .build();

        userDao.save(user);
        return new SuccessResult(UserMessages.userAdded);
    }

    @Override
    public Result deleteUser(int userId) {
        var result = getUserById(userId);
        if(!result.isSuccess()){
            return new ErrorResult(UserMessages.UserNotFound);
        }

        var user = result.getData();
        userDao.delete(user);

        return new SuccessResult(UserMessages.userDeleted);
    }

    @Override
    public Result updateUser(RequestUserDto user) {
        var result = getUserByUsername(user.getUsername());
        if(!result.isSuccess()){
            return new ErrorResult(UserMessages.UserNotFound);
        }

        var userToUpdate = result.getData();
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        userToUpdate.setPhone(user.getPhone());
        userDao.save(userToUpdate);


        return new SuccessResult(UserMessages.userUpdated);
    }

    @Override
    public DataResult<User> getUserById(int userId) {
        var result = userDao.findById(userId);
        if(result == null){
            return new SuccessDataResult<>(UserMessages.UserNotFound);
        }

        return new SuccessDataResult<>(result, UserMessages.UserFound);
    }

    @Override
    public DataResult<User> getUserByUsername(String username) {
        var result = userDao.findByUsername(username);
        if(result == null){
            return new SuccessDataResult<>(UserMessages.UserNotFound);
        }

        return new SuccessDataResult<>(result, UserMessages.UserFound);
    }

    @Override
    public DataResult<List<User>> getUsers() {
        var result = userDao.findAll();
        if(result.isEmpty()){
            return new ErrorDataResult<>(UserMessages.UserNotFound);
        }

        return new SuccessDataResult<List<User>>(result, UserMessages.UsersFetchSuccess);
    }

 //   @Override
 //   public DataResult<List<User>> getUsersByRole(String role) {
 //       var result = userDao.findAllByAuthorities(role);
 //       if(result == null){
 //           return new ErrorDataResult<>(UserMessages.UserNotFound);
 //       }


 //       return new SuccessDataResult<List<User>>(result, UserMessages.UsersFetchSuccess);
 //   }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = getUserByUsername(username).getData();
        return user;
    }

    @Override
    public Result addModerator(int userId) {
        var result = getUserById(userId);
        if(!result.isSuccess()){
            return new ErrorResult(UserMessages.UserNotFound);
        }

        var user = result.getData();
        user.addRole(Role.ROLE_MODERATOR);
        userDao.save(user);
        return new SuccessResult(UserMessages.userUpdated);
    }

    @Override
    public Result removeModerator(int userId) {
        var result = getUserById(userId);
        if(!result.isSuccess()){
            return new ErrorResult(UserMessages.UserNotFound);
        }

        var user = result.getData();
        user.getAuthorities().remove(Role.ROLE_MODERATOR);
        userDao.save(user);
        return new SuccessResult(UserMessages.userUpdated);
    }


}
