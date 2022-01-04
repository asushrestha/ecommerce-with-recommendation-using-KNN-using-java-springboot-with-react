package nce.majorproject.services.impl;

import nce.majorproject.constant.UserType;
import nce.majorproject.context.ContextHolderServices;
import nce.majorproject.dto.Response;
import nce.majorproject.dto.UserAddRequest;
import nce.majorproject.dto.UserAddResponse;
import nce.majorproject.dto.UserProfileResponse;
import nce.majorproject.entities.User;
import nce.majorproject.exception.RestException;
import nce.majorproject.repositories.UserRepository;
import nce.majorproject.services.UserService;
import nce.majorproject.util.DateUtil;
import nce.majorproject.util.JwtTokenUtil;
import nce.majorproject.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ContextHolderServices contextHolderServices;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ContextHolderServices contextHolderServices,
                           JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.contextHolderServices=contextHolderServices;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Transactional
    @Override
    public UserAddResponse addUser(UserAddRequest userAddRequest) {
       verifyUserAge(DateUtil.stringToDate(userAddRequest.getDob()));
       verifyDuplicateUserName(userAddRequest.getUserName());
       User user=prepareUserAddRequest(userAddRequest);
       user.setLoginTime(LocalDateTime.now());
       User response=userRepository.save(user);
        final String token= jwtTokenUtil.generateToken(this.prepareClaims(user.getUserName(),user.getId(), UserType.User.name()));
       return UserAddResponse.builder().accessToken(token)
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
    private void verifyDuplicateUserName(String userName){
        if(userRepository.validateUserName(userName).isPresent()){
            throw new RestException("UserName is already taken");
        }
    }
    private Map<String, Object> prepareClaims(String userName, Long id, String type) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("userName", userName);
        claims.put("userType", type);
        return claims;
    }

    private void verifyUserAge(LocalDate dob){
        if(dob.isAfter(LocalDate.now().minusYears(13))){
            throw new RestException("Your age should be greater than 13");
        }
}
    @Override
    public UserProfileResponse getProfile() {
        //1
//        System.out.println(contextHolderServices.getContext().getId());
        User user=userRepository.getUserProfile(contextHolderServices.getContext().getId());
        return UserProfileResponse.builder().
                address(user.getAddress()).
                gender(user.getGender()).
                id(user.getId()).
                userName(user.getUserName())
                .addedDate(user.getAddedDate())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .dob(user.getDob())
                .loginTime(user.getLoginTime())
                .build();
    }

    private User prepareUserAddRequest(UserAddRequest request){
        User user=new User();

        user.setAddedDate(LocalDateTime.now());
        user.setAddress(request.getAddress());
        user.setDob(DateUtil.stringToDate(request.getDob()));
        user.setGender(request.getGender());
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setPasword(SecurityUtil.encode(request.getPassword()));
        user.setUserName(request.getUserName());

        return user;
    }
    @Override
    public User validateUser(Long userId){

        Optional<User> validate=userRepository.validateUserById(userId);

        return validate.orElseThrow(()->new RestException("invalid user id"));
    }
}
