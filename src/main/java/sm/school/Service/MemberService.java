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
import sm.school.dto.MemberDTO;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    public Member signUp(MemberDTO memberDTO) {

        PersonalInf personalInf = memberDTO.getPersonalInfDTO().toPersonalInf();
        Address address = memberDTO.getAddressDTO().toAddress();

        Member memberSave = Member.builder()
                .userId(memberDTO.getUserId())
                .passwd(passwordEncoder.encode(memberDTO.getPasswd()))
                .school(memberDTO.getSchool())
                .major(memberDTO.getMajor())
                .mem_profile(memberDTO.getMem_profile())
                .role(memberDTO.getRole())
                .date(memberDTO.getDate())
                .personalInf(personalInf) // 값을 할당
                .address(address) // 값을 할당
                .build();

        return memberRepository.save(memberSave);
    }

    public boolean checkUserIdDuplicate(String userId) {
        return memberRepository.existsByUserId(userId);
    }
}
