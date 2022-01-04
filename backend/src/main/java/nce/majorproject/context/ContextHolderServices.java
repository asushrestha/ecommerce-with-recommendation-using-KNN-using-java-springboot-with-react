package nce.majorproject.context;

import lombok.NoArgsConstructor;
import nce.majorproject.constant.UserType;
import nce.majorproject.entities.Admin;
import nce.majorproject.entities.User;
import nce.majorproject.repositories.AdminRepository;
import nce.majorproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class ContextHolderServices {
    private AdminRepository adminRepository;
    private UserRepository userRepository;

    @Autowired
    public ContextHolderServices(AdminRepository adminRepository, UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
    }
    public Context getContext() {
        return ContextHolder.get();
    }
//    public void setContext(String userType, String userName) {
//        System.out.println(userType+" "+userName);
//        if (userType.equalsIgnoreCase(UserType.Admin.name())){
//            System.out.println("hello admin");
//            this.setContextForAdmin(userName);
//        }else if (userType.equalsIgnoreCase(UserType.User.name())){
//            System.out.println("hello user");
//            this.setContextForUser(userName);
//        }
//        System.out.println("test1");
//    }

    public void setContextForAdmin(String username) {
        Optional<Admin> adminOptional = adminRepository.validateUserName(username);
        adminOptional.ifPresent(admin -> {
            ContextHolder thread = new ContextHolder(admin.getId(),UserType.Admin.name(),admin.getUserName());
            thread.run();
        });
    }
    public void setContextForUser(String username) {
        Optional<User> userOptional = userRepository.validateUserName(username);
        userOptional.ifPresent(user -> {
            ContextHolder thread = new ContextHolder(user.getId(),UserType.User.name(),user.getUserName());
            System.out.println(user.getId()+ " "+2343);
            thread.run();
        });
    }
}
