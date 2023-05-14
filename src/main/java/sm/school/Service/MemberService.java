package sm.school.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import sm.school.Repository.member.MemberRepository;
import sm.school.Service.commonError.DataNotFoundException;
import sm.school.domain.member.Member;
import sm.school.dto.MemberDTO;

import java.util.List;
import java.util.stream.Collectors;

import static sm.school.Service.commonConst.Status.SELECTED;
import static sm.school.Service.commonConst.Status.NOT_SELECTED;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;


    public Member signUp(MemberDTO memberDTO, BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            throw new DataNotFoundException();
        }
        memberDTO.setPasswd(passwordEncoder.encode(memberDTO.getPasswd()));//패스워드 인코딩진행
        Member memberSave = memberDTO.toMemberEntity();//MemberDTO -> 엔티티로 변환

        return memberRepository.save(memberSave);
    }

    public boolean checkUserIdDuplicate(String userId) {
        return memberRepository.existsByUserId(userId);
    }

    //회원 정보 받아오기
    public List<MemberDTO> memberList() {
        return memberRepository.findAll().stream()
                .map(Member::toMemberDTO)
                .collect(Collectors.toList());
    }

    public void changeRole(Long id) {
        Member member = memberRepository.findMemberById(id);
        log.info("memberRole {}",member.getRole());
        if (member.getRole().equals(NOT_SELECTED)) {
            member.changeRole(NOT_SELECTED);
        } else {
            member.changeRole(SELECTED);
        }
    }
}
