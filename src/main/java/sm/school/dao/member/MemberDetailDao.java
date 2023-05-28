package sm.school.dao.member;

import sm.school.domain.member.MemberDetail;

import java.util.List;

public interface MemberDetailDao {

    List<MemberDetail> findAll();
    MemberDetail save(MemberDetail memberDetail);
    Boolean existsByMemberUserId(String userId);
    MemberDetail findMemberDetailByMemberUserId(String userId);
}
