package nce.majorproject.services;

import nce.majorproject.dto.AuthRequest;
import nce.majorproject.dto.AuthResponse;
import nce.majorproject.dto.UserAuthResponse;

public interface AuthService {

    UserAuthResponse authenticateUser(AuthRequest request);
    AuthResponse authenticateAdmin(AuthRequest request );
}
