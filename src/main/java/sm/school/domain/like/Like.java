package sm.school.domain.like;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public abstract class Like {

    @Id
    @GeneratedValue
    @Column(name = "like_id")
    private Long id;
}
