package skylab.skymerch.webAPI.controllers;

import org.springframework.web.bind.annotation.*;
import skylab.skymerch.business.abstracts.UserService;
import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.entities.Dtos.RequestUserDto;
import skylab.skymerch.entities.User;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/addUser")
    public Result addUser(@RequestBody RequestUserDto requestUserDto){
        return userService.addUser(requestUserDto);
    }

    @GetMapping("/getUsers")
    public DataResult<List<User>> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/getByUsername")
    public DataResult<User> getByUsername(@RequestParam String username){
        return userService.getUserByUsername(username);
    }

    @GetMapping("/getUserById")
    public DataResult<User> getUserById(@RequestParam int userId){
        return userService.getUserById(userId);
    }

    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody RequestUserDto requestUserDto){
        return userService.updateUser(requestUserDto);

    }

    @PostMapping("/deleteUser")
    public Result deleteUser(@RequestParam int userId){
        return userService.deleteUser(userId);
    }





}
