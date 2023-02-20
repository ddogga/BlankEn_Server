package blank.english.exception;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException() {
        super("회원을 조회할 수 없습니다.");
    }

    public MemberNotFoundException(String message) {
        super(message);
    }

    public MemberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberNotFoundException(Throwable cause) {
        super(cause);
    }
}
