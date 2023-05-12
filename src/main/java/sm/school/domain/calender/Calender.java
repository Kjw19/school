package sm.school.domain.calender;

import lombok.Getter;
import sm.school.domain.embeded.CalenderPeriod;

import javax.persistence.*;

//@Entity
@Getter
public abstract class Calender {

    @Id
    @GeneratedValue
    @Column(name = "cal_id")
    private Long id;

    private String cal_name;
    private String cal_content;

    @Embedded
    private CalenderPeriod calendarPeriod;

}
