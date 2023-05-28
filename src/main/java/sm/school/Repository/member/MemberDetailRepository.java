package sm.school.Repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import sm.school.domain.member.MemberDetail;

public interface MemberDetailRepository extends JpaRepository<MemberDetail, Long> {

    MemberDetail findMemberDetailByMemberUserId(String userId);

    Boolean existsByMemberUserId(String userId);
}
