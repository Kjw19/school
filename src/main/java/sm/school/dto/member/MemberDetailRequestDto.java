package sm.school.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sm.school.domain.member.Member;
import sm.school.domain.member.MemberDetail;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class MemberDetailRequestDto {

    private Long id;
    private String nickname;//유저 아이디
    private String school;//학교
    private String major;//전공
    private String profile;//회원 이미지
    private LocalDate date; //가입일자
    private PersonalInfRequestDto personalInfRequestDto;//이름 생년월일 전화번호 내장
    private AddressRequestDto addressRequestDto; //우편번호, 주소, 상세 주소 내장
    private Member member;

    @Builder
    public MemberDetailRequestDto(Long id, String nickname, String school, String major, String profile, LocalDate date,
                                  PersonalInfRequestDto personalInfRequestDto, AddressRequestDto addressRequestDto, Member member) {
        this.id = id;
        this.nickname = nickname;
        this.school = school;
        this.major = major;
        this.profile = profile;
        this.date = date;
        this.personalInfRequestDto = personalInfRequestDto;
        this.addressRequestDto = addressRequestDto;
        this.member = member;
    }

    public MemberDetail toEntity() {

        return MemberDetail.builder()
                .id(this.id)
                .nickname(this.nickname)
                .school(this.school)
                .major(this.major)
                .profile(this.profile)
                .date(date)
                .personalInf(this.personalInfRequestDto.toEntity())
                .address(this.addressRequestDto.toEntity())
                .member(member)
                .build();
    }
}
