package sm.school.Service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sm.school.domain.member.Member;

@Service
@Transactional
public class CommonService {

    public Member getMemberFromAuthentication(Authentication authentication) {
        MemberDetailsService memberDetails = (MemberDetailsService) authentication.getPrincipal();
        return memberDetails.getMember();
    }
}
