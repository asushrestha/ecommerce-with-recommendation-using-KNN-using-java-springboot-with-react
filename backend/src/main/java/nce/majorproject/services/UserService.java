package nce.majorproject.services;

import nce.majorproject.dto.Response;
import nce.majorproject.dto.UserAddRequest;
import nce.majorproject.dto.UserAddResponse;
import nce.majorproject.dto.UserProfileResponse;
import nce.majorproject.entities.User;

public interface UserService {
    UserAddResponse addUser(UserAddRequest userAddRequest);
    UserProfileResponse getProfile();
    User validateUser(Long userId);
}
