package sm.school.Repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.school.domain.member.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

}