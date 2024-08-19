package com.ubs.cpt.rest;

import com.ubs.cpt.infra.domain.EntityId;
import com.ubs.cpt.service.UserService;
import com.ubs.cpt.service.dto.UserDto;
import com.ubs.cpt.service.searchparams.UserSearchParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * The rest controller for smoothie application.
 *
 * @author Amar Pandav
 */
@RestController
@RequestMapping("/controller")
public class CptController {


    @Autowired
    private UserService userService;


    /**
     * Find Users based on Search Parameters
     *
     * @param searchParameters
     * @return searched stories
     */
    @RequestMapping(method = RequestMethod.POST, value = "findUsers")
    @ResponseBody
    public List<UserDto> findUsers(@RequestBody UserSearchParameters searchParameters) {
        return userService.findUsers(searchParameters);
    }

    @RequestMapping(method = RequestMethod.POST, value = "countUsers")
    @ResponseBody
    public int countUsers(@RequestBody UserSearchParameters searchParameters) {
        return userService.countUsers(searchParameters);
    }
}
