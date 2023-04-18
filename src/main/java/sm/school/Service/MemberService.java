package sm.school.Service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sm.school.Repository.member.MemberRepository;
import sm.school.domain.member.Address;
import sm.school.domain.member.PersonalInf;
import sm.school.domain.member.Member;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    public Member signUp(Member member) {

        PersonalInf personalInf = PersonalInf.builder()
                .name(member.getPersonalInf().getName())
                .birth(member.getPersonalInf().getBirth())
                .phone(member.getPersonalInf().getPhone())
                .build();

        Address address = Address.builder()
                .zipcode(member.getAddress().getZipcode())
                .address1(member.getAddress().getAddress1())
                .address2(member.getAddress().getAddress2())
                .build();


        Member memberSave = Member.builder()
                .userId(member.getUserId())
                .passwd(passwordEncoder.encode(member.getPasswd()))
                .school(member.getSchool())
                .major(member.getMajor())
                .mem_profile(member.getMem_profile())
                .role(member.getRole())
                .date(member.getDate())
                .personalInf(personalInf) // 값을 할당
                .address(address) // 값을 할당
                .build();

        return memberRepository.save(memberSave);
    }

    public boolean checkUserIdDuplicate(String userId) {
        return memberRepository.existsByUserId(userId);
    }
}
