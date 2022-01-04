package nce.majorproject.controller;

import lombok.extern.slf4j.Slf4j;
import nce.majorproject.constant.Route;
import nce.majorproject.dto.AuthRequest;
import nce.majorproject.dto.AuthResponse;
import nce.majorproject.dto.UserAuthResponse;
import nce.majorproject.services.AuthService;
import nce.majorproject.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(Route.Auth)
@Slf4j
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/admin")
    public AuthResponse authenticateAdmin(@Valid @RequestBody AuthRequest request){

        log.info("authenticating admin::{}",request.getUserName());

        return authService.authenticateAdmin(request);
    }
    @PostMapping(value = "/user")
    public UserAuthResponse authenticateUser(@Valid @RequestBody AuthRequest request){
        log.info("authenticating user::{}",request.getUserName());
        //log.info("Encode: ", SecurityUtil.encode("slap4msth").toString());
        return authService.authenticateUser(request);
    }
}
