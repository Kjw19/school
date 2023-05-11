package sm.school.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sm.school.Repository.member.MemberRepository;
import sm.school.domain.member.Member;
import sm.school.dto.MemberDTO;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
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

    //회원 정보 받아오기
    public List<MemberDTO> memberList() {

        List<Member> memberList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (Member member : memberList) {
            if (!member.getUserId().equals("admin")) {
                MemberDTO memberDTO = member.toMemberDTO();
                memberDTOList.add(memberDTO);

            }
        }
        return memberDTOList;
    }

    public void changeRole(Long id) {
        Member member = memberRepository.findMemberById(id);
        log.info("memberRole {}",member.getRole());
        if (member.getRole().equals(1)) {
            member.changeRole(0);
        } else {
            member.changeRole(1);
        }
    }
}
