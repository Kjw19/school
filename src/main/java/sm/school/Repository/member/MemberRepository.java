package sm.school.Repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.school.domain.member.Member;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserId(String userId);
    Member findMemberByUserId(String userId);


    Optional<Member> findByPasswd(String passwd);
    boolean existsByUserId(String userId);

    Member findMemberById(Long id);

}
