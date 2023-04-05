package sm.school.domain.reply;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public abstract class Reply {

    @Id
    @GeneratedValue
    @Column(name = "reply_id")
    private Long id;
}
