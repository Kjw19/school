package sm.school.Service.commonError;

public class FileSizeException extends RuntimeException{
    public FileSizeException() {
        super("용량이 초과하였습니다");
    }
}
