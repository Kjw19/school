package sm.school.domain.embeded;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class CalenderPeriod {

    private String open_date;
    private String close_date;
    private String open_time;
    private String close_time;

    protected CalenderPeriod(){}

    @Builder
    public CalenderPeriod(String open_date, String close_date, String open_time, String close_time) {
        this.open_date = open_date;
        this.close_date = close_date;
        this.open_time = open_time;
        this.close_time = close_time;
    }
}
