package global.exception;

public enum ExceptionConstants {

    DEFAULT(100), VALIDATION(101), CONFIGURATION(102);

    private int statusCode;

    ExceptionConstants(int statusCode) {
        this.statusCode = statusCode;
    }

    public int status() {
        return statusCode;
    }
}
