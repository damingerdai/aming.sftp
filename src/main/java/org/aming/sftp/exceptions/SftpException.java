package org.aming.sftp.exceptions;

/**
 * 异常包装类
 *
 * @athur aming
 * @date 2018-06-08 14:42
 */
public class SftpException extends RuntimeException {

    private String message;

    private Throwable cause;

    @Override
    public String getMessage() {
        return message;
    }

    public SftpException setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    public SftpException setCause(Throwable cause) {
        this.cause = cause;
        return this;
    }

    SftpException() {
        super();
    }

    public SftpException(String message, Throwable cause) {
        super();
        this.message = message;
        this.cause = cause;
    }
}
