package sm.school.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sm.school.Repository.member.MemberRepository;
import sm.school.domain.embeded.Address;
import sm.school.domain.embeded.PersonalInf;
import sm.school.domain.member.Member;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member signUp(String user_id, String passwd, String school,
                         String major, String mem_profile, int role,
                         PersonalInf personalInf, Address address) {


        Member member = Member.builder()
                .user_id(user_id)
                .passwd(passwordEncoder.encode(passwd))
                .school(school)
                .major(major)
                .mem_profile(mem_profile)
                .role(role)
                .personalInf(personalInf)
                .address(address)
                .build();

        return memberRepository.save(member);
    }
}
