package org.aming.sftp.exceptions;

import org.aming.sftp.logger.LoggerManager;
import org.aming.sftp.logger.SftpLogger;
import org.aming.sftp.utils.MessageFormatter;

import java.util.function.Supplier;

/**
 * 异常工具类
 *
 * @athur aming
 * @date 2018-06-08 14:46
 */
public class ExceptionBuilder {

    private static final SftpLogger logger = LoggerManager.getLogger(ExceptionBuilder.class);

    public static SftpException buildException(String message, Throwable cause) {
       return doBuildException(message, cause);
    }

    public static SftpException buildException(Supplier<String> supplier, Throwable cause) {
        return doBuildException(supplier.get(), cause);
    }

    public static SftpException buildException(String message, Object[] params, Throwable cause, MessageFormatter formatter) {
        return doBuildException(message, params, cause, formatter);
    }

    public static SftpException buildException(String message, Object[] params, Throwable cause) {
        return doBuildException(message, params, cause, String::format);
    }

    private static SftpException doBuildException(String message, Throwable cause) {
        logger.error(message, cause);
        return new SftpException(message, cause);
    }

    private static SftpException doBuildException(String message, Object[] params, Throwable cause, MessageFormatter formatter) {
        message = formatter.get(message, params);
        return doBuildException(message, cause);
    }
}
