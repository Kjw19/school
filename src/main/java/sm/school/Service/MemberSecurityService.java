package sm.school.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sm.school.Repository.member.MemberRepository;
import sm.school.domain.enumType.MemRole;
import sm.school.domain.member.Member;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberSecurityService implements UserDetailsService {

    /**
     * UserDetailsService: SpringSecurity에서 유저의 정보를 가져오는 인터페이스
     * 유저의 정보를 구현해야하는 인터페이스로
     * loadUserByUsername를 기본 오버라이드 메서드로 사용한다.
     */

    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 ID를 찾을 수 없습니다."));//람다식 사용 예외처리


        log.debug("userId,passwd {} {}", userId, member.getPasswd()); //디버그 userid passwd
        return new MemberDetailsService(member); //시큐리티에서 로그인 처리
    }
}
