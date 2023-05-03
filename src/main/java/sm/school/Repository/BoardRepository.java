package sm.school.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.school.domain.board.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
