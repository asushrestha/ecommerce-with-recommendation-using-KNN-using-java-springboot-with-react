package nce.majorproject.controller;

import lombok.extern.slf4j.Slf4j;
import nce.majorproject.constant.Route;
import nce.majorproject.dto.AdminRegisterRequest;
import nce.majorproject.dto.CountStatResponse;
import nce.majorproject.dto.Response;
import nce.majorproject.entities.User;
import nce.majorproject.services.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(Route.ADMIN)
@Slf4j
public class AdminController {
    private AdminServices adminServices;

    @Autowired
    public AdminController(AdminServices adminServices) {
        this.adminServices = adminServices;
    }

    @PostMapping(value = "/add")
    public Response addAdmin(@Valid @RequestBody AdminRegisterRequest registerRequest){
        log.info("registering new admin::{}{}",registerRequest.getFullName(),registerRequest.getUserName());
        return adminServices.addAdmin(registerRequest);
    }
    @GetMapping(value = "/get-registered-users")
    public List<User> getRegisteredUsers(){
        log.info("getting registered users List");
        return  adminServices.getRegisteredUsers();
    }

    @GetMapping(value = "/count-stat")
    public CountStatResponse countStatResponse(){
        log.info("getting count stat response");
        return adminServices.countStatResponse();
    }
    //asmin test123

}
