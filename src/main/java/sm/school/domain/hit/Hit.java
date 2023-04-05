package sm.school.domain.hit;

import lombok.Getter;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public abstract class Hit {

    @Id
    @GeneratedValue
    @Column(name = "hit_id")
    private Long id;
}
