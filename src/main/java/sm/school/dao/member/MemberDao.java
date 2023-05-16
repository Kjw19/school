package sm.school.dao.member;

import sm.school.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberDao {
    List<Member> findAll();

    Member save(Member member);

    Optional<Member> findById(Long id);

    void deleteById(Long id);

    Boolean existsById(Long id);
    Boolean existsByUserId(String userId);

    Member findMemberById(Long id);
    Optional<Member> findByUserId(String userId);

    Member findMemberByUserId(String userId);
}
