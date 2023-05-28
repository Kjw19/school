package sm.school.dao.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sm.school.Repository.member.MemberDetailRepository;
import sm.school.domain.member.MemberDetail;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class JpaMemberDetailDao implements MemberDetailDao {

    private final MemberDetailRepository memberDetailRepository;

    @Override
    public List<MemberDetail> findAll() {
        return memberDetailRepository.findAll();
    }

    @Override
    public MemberDetail save(MemberDetail memberDetail) {
        return memberDetailRepository.save(memberDetail);
    }

    @Override
    public Boolean existsByMemberUserId(String userId) {
        return memberDetailRepository.existsByMemberUserId(userId);
    }

    @Override
    public MemberDetail findMemberDetailByMemberUserId(String userId) {
        return memberDetailRepository.findMemberDetailByMemberUserId(userId);
    }
}
