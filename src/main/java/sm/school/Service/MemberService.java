package sm.school.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sm.school.Repository.member.MemberRepository;
import sm.school.domain.member.Address;
import sm.school.domain.member.PersonalInf;
import sm.school.domain.member.Member;
import sm.school.dto.MemberDTO;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;


    public Member signUp(MemberDTO memberDTO) {

        memberDTO.setPasswd(passwordEncoder.encode(memberDTO.getPasswd()));//패스워드 인코딩진행
        Member memberSave = memberDTO.toMemberEntity();//MemberDTO -> 엔티티로 변환

        return memberRepository.save(memberSave);
    }

    public boolean checkUserIdDuplicate(String userId) {
        return memberRepository.existsByUserId(userId);
    }
}
