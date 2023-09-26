package physical.education.yehjung.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import physical.education.yehjung.Member.dto.User;
import physical.education.yehjung.Member.repository.UserRepository;

//Security 설정에서 loginProcessUrl("/login");
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 ioc 되어 있는 loaduserbyusername 함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    //security session =  authenticaiton = UserDetails
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByNickname(username);
        if(userEntity != null){
            return new PrincipalDetails(userEntity);
        }

        return null;
    }
}
