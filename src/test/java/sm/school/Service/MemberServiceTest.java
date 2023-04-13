package sm.school.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import sm.school.Repository.member.MemberRepository;
import sm.school.domain.embeded.Address;
import sm.school.domain.embeded.PersonalInf;
import sm.school.domain.member.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) //Junit5와 Mockito를 함께 사용할 수 있도록 설정
class MemberServiceTest {

    @Mock//인스턴스를 모킹하도록 설정, 가짜객체 설정을 통해 실제 DB와 상호작용 피할수있음
    private MemberRepository memberRepository;

    @InjectMocks //MemberService객체 생성 및 모킹된 MemberRepository 인스턴스를 해당 객체 주입
    private MemberService memberService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp(){
        memberService = new MemberService(memberRepository, passwordEncoder);
    }


    @Test
    void 회원가입() {

        //given
        //회원가입 데이터 입력
        Member member = Member.builder()
                .user_id("test")
                .passwd("1111")
                .school("경기대")
                .major("미술경영")
                .mem_profile("1")
                .role(1)
                .personalInf(new PersonalInf("testName", 990329, 01011111111))
                .address(new Address("16827", "가고일", "101호"))
                .build();

        String encodedPassword = passwordEncoder.encode(member.getPasswd());

        //when
        when(memberRepository.save(any(Member.class)))//any(Member.class)는 Member 타입의 객체가 인자로 전달되는 모든 경우를 나타냄
                .thenAnswer(invocation -> invocation.getArgument(0));
        //save 메서드가 호출되면 어떤 동작을 수행할지 정의, 여기서는 람다 함수사용
        //invocation : InvocationOnMock 객체로, 현재 메서드 호출에 대한 정보를 포함
        //invocation.getArgument(0) : InvocationOnMock 객체의 getArgument 메서드를 사용하여 첫 번째 인자를 가져옵니다.
        // 여기서 첫 번째 인자는 Member 객체

        Member savedMember1 = memberService.signUp("test", "1111", "경기대", "미술경영", "1", 1, new PersonalInf("testName", 990329, 01011111111), new Address("16827", "가고일", "101호"));

        //then

        assertThat(member).usingRecursiveComparison().ignoringFields("passwd").isEqualTo(savedMember1);
        //비밀번호를 제외한 필드들을 확인한다.

        assertThat(savedMember1.getPasswd()).isEqualTo(encodedPassword);
        //암호화된 비밀번호가 같은 지 확인한다.

    }


    @Test
    void 암호화확인() {

        //given
        //회원가입 데이터 입력
        Member member = Member.builder()
                .user_id("test")
                .passwd("1111")
                .school("경기대")
                .major("미술경영")
                .mem_profile("1")
                .role(1)
                .personalInf(new PersonalInf("testName", 990329, 01011111111))
                .address(new Address("16827", "가고일", "101호"))
                .build();


        //when
        //암호화된 값이 있을때  encoded_password 로 반환한다.
        when(passwordEncoder.encode(member.getPasswd())).thenReturn("encoded_password");


        when(memberRepository.save(any(Member.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Member savedMember1 = memberService.signUp("test", "1111", "경기대", "미술경영", "1", 1, new PersonalInf("testName", 990329, 01011111111), new Address("16827", "가고일", "101호"));

        //then
        assertThat(savedMember1.getPasswd()).isEqualTo("encoded_password");
        //암호화가 잘 되었는지 확인하는 용도이다

    }

}
