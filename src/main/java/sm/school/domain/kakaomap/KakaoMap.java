package sm.school.domain.kakaomap;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//@Entity
@Getter
public abstract class KakaoMap {

    @Id
    @GeneratedValue
    @Column(name = "kakao_id")
    private Long id;
}
