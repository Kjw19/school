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
public class MemberDetailResponseDto {

    private Long id;
    private String nickname;//유저 아이디
    private String school;//학교
    private String major;//전공
    private String profile;//회원 이미지
    private LocalDate date; //가입일자
    private PersonalInfResponseDto personalInfResponseDto;//이름 생년월일 전화번호 내장
    private AddressResponseDto addressResponseDto; //우편번호, 주소, 상세 주소 내장
    private Member member;

    @Builder
    public MemberDetailResponseDto(Long id, String nickname, String school, String major, String profile,
                                   LocalDate date, PersonalInfResponseDto personalInfResponseDto, AddressResponseDto addressResponseDto, Member member) {
        this.id = id;
        this.nickname = nickname;
        this.school = school;
        this.major = major;
        this.profile = profile;
        this.date = date;
        this.personalInfResponseDto = personalInfResponseDto;
        this.addressResponseDto = addressResponseDto;
        this.member = member;
    }

    public static MemberDetailResponseDto toDto(MemberDetail memberDetail) {
        PersonalInfResponseDto personalInfResponseDto = PersonalInfResponseDto.toDto(memberDetail.getPersonalInf());
        AddressResponseDto addressResponseDto = AddressResponseDto.toDto(memberDetail.getAddress());

        return MemberDetailResponseDto.builder()
                .id(memberDetail.getId())
                .nickname(memberDetail.getNickname())
                .school(memberDetail.getSchool())
                .major(memberDetail.getMajor())
                .profile(memberDetail.getProfile())
                .date(memberDetail.getDate())
                .personalInfResponseDto(personalInfResponseDto)
                .addressResponseDto(addressResponseDto)
                .member(memberDetail.getMember())
                .build();
    }
}
