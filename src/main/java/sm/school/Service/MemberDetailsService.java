package sm.school.Service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sm.school.domain.enumType.MemRole;
import sm.school.domain.member.Member;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetails {

    private final Member member;


    //권한정보 처리
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(member.getUserId())) {
            authorities.add(new SimpleGrantedAuthority(MemRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(MemRole.USER.getValue()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPasswd();
    }

    @Override
    public String getUsername() {
        return member.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
