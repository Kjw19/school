package sm.school.Service.commonError;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException() {
        super("정보를 찾을 수 없습니다.");
    }
}
