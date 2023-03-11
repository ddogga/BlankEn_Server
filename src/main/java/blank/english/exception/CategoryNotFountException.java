package blank.english.exception;

public class CategoryNotFountException extends RuntimeException{

    public CategoryNotFountException() {
        super("카테고리를 찾을 수 없습니다.");
    }

    public CategoryNotFountException(String message) {
        super(message);
    }

    public CategoryNotFountException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryNotFountException(Throwable cause) {
        super(cause);
    }
}
