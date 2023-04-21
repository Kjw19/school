package sm.school;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import sm.school.Repository.member.MemberRepository;
import sm.school.Service.MemberSecurityService;
import sm.school.domain.member.Member;

@Configuration //스프링의 환경설정 파일임을 명시
@EnableWebSecurity //모든 요청이 스프링 시큐리티의 제어를 받음
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberSecurityService memberSecurityService;

    //사용자 인증 및 권한 부여 과정에서 사용자 정보를 찾고, 
    // 비밀번호를 확인할 때 암호화된 비밀번호와 입력된 비밀번호를 비교하는 작업
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberSecurityService).passwordEncoder(passwordEncoder());
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //인증 설정
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")//관리자 권한이 있을시 /admin및 하위경로 접근 제한
                .anyRequest().permitAll()//보안설정 되지 않은 모든 경로 인증없이 접근허용
                .and()//anyMatchers로 먼저 제한할 페이지 설정후 anyRequest 설정해야 오류안남
                .formLogin()//로그인 설정
                .loginPage("/member/login")//로그인 URL경로
                .defaultSuccessUrl("/")//로그인 성공시 이동경로
                .permitAll()//모든 사용자에게 접근 허용
                .and()
                .logout()//로그아웃 설정 시작
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) //로그아웃 경로
                .logoutSuccessUrl("/") //로그아웃성공시 이동 URL
                .invalidateHttpSession(true)//로그아웃시 세션 무효화
                .and()
                .exceptionHandling().accessDeniedPage("/accessBlock");//권한 없는 페이지 접근시 이동경로


        return http.build();
    }

    @Bean //PasswordEncoder Bean 등록
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
