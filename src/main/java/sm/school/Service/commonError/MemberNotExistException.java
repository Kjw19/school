package sm.school.Service.commonError;

public class MemberNotExistException extends RuntimeException{
    public MemberNotExistException() {
        super("회원이 존재하지 않습니다");
    }
}
