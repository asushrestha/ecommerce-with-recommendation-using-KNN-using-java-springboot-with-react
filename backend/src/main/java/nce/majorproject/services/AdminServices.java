package nce.majorproject.services;

import nce.majorproject.dto.AdminRegisterRequest;
import nce.majorproject.dto.CountStatResponse;
import nce.majorproject.dto.Response;
import nce.majorproject.entities.User;

import java.util.List;

public interface AdminServices {
    Response addAdmin(AdminRegisterRequest registerRequest);
    List<User> getRegisteredUsers();

    CountStatResponse countStatResponse();
}
