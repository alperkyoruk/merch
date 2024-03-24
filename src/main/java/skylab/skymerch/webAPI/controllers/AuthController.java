package skylab.skymerch.webAPI.controllers;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import skylab.skymerch.business.abstracts.UserService;
import skylab.skymerch.core.security.JwtService;
import skylab.skymerch.core.utilities.result.ErrorDataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.core.utilities.result.SuccessDataResult;
import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.entities.Dtos.RequestUserDto;
import skylab.skymerch.entities.User;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private UserService userService;

    public AuthController(UserService userService,AuthenticationManager authenticationManager, JwtService jwtService){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;

    }

    @PostMapping("/generateToken")
    public DataResult<String> generateToken(@RequestParam String username, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if (authentication.isAuthenticated()) {
            return new SuccessDataResult<String>(jwtService.generateToken(username), "Token generated successfully");
        }
        return new ErrorDataResult<>("Invalid username or password");
    }

    @PostMapping("/registerUser")
    public Result registerUser(@RequestBody RequestUserDto requestUserDto){
        return userService.addUser(requestUserDto);
    }

    @PostMapping("/addModerator")
    public Result addModerator(@RequestParam int userId){
        return userService.addModerator(userId);
    }

    @PostMapping("/removeModerator")
    public Result removeModerator(@RequestParam int userId){
        return userService.removeModerator(userId);
    }




}


