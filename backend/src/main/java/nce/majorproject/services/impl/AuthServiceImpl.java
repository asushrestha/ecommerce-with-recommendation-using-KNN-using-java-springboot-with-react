package nce.majorproject.services.impl;

import nce.majorproject.constant.UserType;
import nce.majorproject.dto.AuthRequest;
import nce.majorproject.dto.AuthResponse;
import nce.majorproject.dto.UserAuthResponse;
import nce.majorproject.entities.Admin;
import nce.majorproject.entities.User;
import nce.majorproject.exception.RestException;
import nce.majorproject.repositories.AdminRepository;
import nce.majorproject.repositories.UserRepository;
import nce.majorproject.services.AuthService;
import nce.majorproject.util.JwtTokenUtil;
import nce.majorproject.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private AdminRepository adminRepository;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, AdminRepository adminRepository,JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public UserAuthResponse authenticateUser(AuthRequest request) {
        Optional<User> optionalUser=userRepository.authenticateUserCredential(request.getUserName(), SecurityUtil.encode(request.getPassword()));
        User user= optionalUser.orElseThrow(()->new RestException("invalid login credentials!!"));
        user.setLoginTime(LocalDateTime.now());
        User response = userRepository.save(user);
        final String token= jwtTokenUtil.generateToken(this.prepareClaims(user.getUserName(),user.getId(), UserType.User.name()));
        return UserAuthResponse.builder().accessToken(token)
                .id(response.getId())
                .addedDate(response.getAddedDate())
                .address(response.getAddress())
                .dob(response.getDob())
                .fullName(response.getFullName())
                .gender(response.getGender())
                .loginTime(response.getLoginTime())
                .phone(response.getPhone())
                .userName(response.getUserName())
                .build();

    }
    private Map<String, Object> prepareClaims(String userName, Long id, String type) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("userName", userName);
        claims.put("userType", type);
        return claims;
    }

    @Override
    public AuthResponse authenticateAdmin(AuthRequest request) {
       // System.out.println( SecurityUtil.encode(request.getPassword()));
        Optional<Admin> optionalAdmin=adminRepository.authenticateAdminCredential(request.getUserName(), SecurityUtil.encode(request.getPassword()));
        Admin admin= optionalAdmin.orElseThrow(()->new RestException("invalid login credentials!!"));
        final String accessToken= jwtTokenUtil.generateToken(this.prepareClaims(admin.getUserName(),admin.getId(), UserType.Admin.name()));
        return AuthResponse.builder().accessToken(accessToken)
                .fullName(admin.getFullName())
                .addedDate(admin.getAddedDate())
                .id(admin.getId())
                .phone(admin.getPhone())
                .dob(admin.getDob())
                .userName(admin.getUserName())
                .build();
    }
}
