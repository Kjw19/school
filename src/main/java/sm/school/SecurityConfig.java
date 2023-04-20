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

    private MemberSecurityService memberSecurityService;

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberSecurityService).passwordEncoder(passwordEncoder());
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /**
         * 모든 인증되지 않은 요청을 허락한다.
         * 로그인 하지 않더라도 모든 페이지 접근 가능
         */
        http.authorizeHttpRequests().requestMatchers(
                        new AntPathRequestMatcher("/**")).permitAll()//모든 경로 인증없이 접근허용
            .and()
                .formLogin()//로그인 설정
                .loginPage("/member/login")//로그인 URL경로
                .defaultSuccessUrl("/")//로그인 성공시 이동경로
                .permitAll()//모든 사용자에게 접근 허용
            .and()
                .logout()//로그아웃 설정 시작
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) //로그아웃 경로
                .logoutSuccessUrl("/") //로그아웃성공시 이동 URL
                .invalidateHttpSession(true);//로그아웃시 세션 무효화

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
