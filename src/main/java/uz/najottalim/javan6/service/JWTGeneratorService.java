package uz.najottalim.javan6.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.najottalim.javan6.utility.JWTUtility;

@Service
@RequiredArgsConstructor
public class JWTGeneratorService {
    private final JWTUtility jwtUtility;
    public String jwtGenerate(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            return jwtUtility.generate(
                    authentication.getName(),
                    "user"
            );
        }
        return "";
    }
}
