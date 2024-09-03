package com.ubs.cpt.web.rest;

import com.ubs.cpt.service.UserService;
import com.ubs.cpt.service.dto.UserDto;
import com.ubs.cpt.service.searchparams.UserSearchParameters;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The rest controller for user management.
 *
 * @author Amar Pandav
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private record UserResponse(List<UserDto> users) {
    }

    private record UserCountResponse(int usersCount) {
    }

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * Find Users based on Search Parameters
     *
     * @param searchParameters  User Search Parameters
     * @return searched stories
     */
    @RequestMapping(method = RequestMethod.POST, value = "findUsers")
    public ResponseEntity<UserResponse> findUsers(@RequestBody(required = false) UserSearchParameters searchParameters) {
        List<UserDto> users = userService.findUsers(searchParameters);
        return ResponseEntity.ok(new UserResponse(users));
    }

    @RequestMapping(method = RequestMethod.POST, value = "countUsers")
    public ResponseEntity<UserCountResponse> countUsers(@RequestBody UserSearchParameters searchParameters) {
        return ResponseEntity.ok(new UserCountResponse(userService.countUsers(searchParameters)));
    }

    // Debugging purpose
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<UserResponse> getAllUsers(){
        List<UserDto> users = userService.findUsers(new UserSearchParameters());
        return ResponseEntity.ok(new UserResponse(users));
    }
}
