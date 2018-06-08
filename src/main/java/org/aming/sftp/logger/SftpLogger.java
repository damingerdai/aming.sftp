package org.aming.sftp.logger;

import org.slf4j.Logger;

import java.util.function.Supplier;

/**
 * 日志
 *
 * @athur aming
 * @date 2018-06-08 13:45
 */
public class SftpLogger {

    private final Logger logger;

    public void trace(String msg) {
        if(logger.isTraceEnabled()) {
            logger.trace(msg);
        }
    }

    public void trace(String format, Object...params) {
        if(logger.isTraceEnabled()) {
            logger.trace(format, params);
        }
    }

    public void trace(String msg, Throwable t) {
        if(logger.isTraceEnabled()) {
            logger.trace(msg, t);
        }
    }

    public void trace(Supplier<String> supplier) {
        if (logger.isTraceEnabled()) {
            logger.trace(supplier.get());
        }
    }

    public void trace(Supplier<String> supplier, Throwable cause) {
        if (logger.isTraceEnabled()) {
            logger.trace(supplier.get(), cause);
        }
    }

    public void debug(String msg) {
        if(logger.isDebugEnabled()) {
            logger.debug(msg);
        }
    }

    public void debug(String format, Object...params) {
        if(logger.isDebugEnabled()) {
            logger.debug(format, params);
        }
    }

    public void debug(String msg, Throwable t) {
        if(logger.isDebugEnabled()) {
            logger.debug(msg, t);
        }
    }

    public void debug(Supplier<String> supplier) {
        if (logger.isDebugEnabled()) {
            logger.debug(supplier.get());
        }
    }

    public void debug(Supplier<String> supplier, Throwable cause) {
        if (logger.isDebugEnabled()) {
            logger.debug(supplier.get(), cause);
        }
    }

    public void info(String msg) {
        if(logger.isInfoEnabled()) {
            logger.info(msg);
        }
    }

    public void info(String format, Object...params) {
        if(logger.isInfoEnabled()) {
            logger.info(format, params);
        }
    }

    public void info(String msg, Throwable t) {
        if(logger.isInfoEnabled()) {
            logger.info(msg, t);
        }
    }

    public void info(Supplier<String> supplier) {
        if(logger.isInfoEnabled()) {
            logger.info(supplier.get());
        }
    }

    public void info(Supplier<String> supplier, Throwable cause) {
        if(logger.isInfoEnabled()) {
            logger.info(supplier.get(), cause);
        }
    }

    public void warn(String msg) {
        if(logger.isWarnEnabled()) {
            logger.warn(msg);
        }
    }

    public void warn(String format, Object...params) {
        if(logger.isWarnEnabled()) {
            logger.warn(format, params);
        }
    }

    public void warn(String msg, Throwable t) {
        if(logger.isWarnEnabled()) {
            logger.warn(msg, t);
        }
    }

    public void warn(Supplier<String> supplier) {
        if(logger.isWarnEnabled()) {
            logger.warn(supplier.get());
        }
    }

    public void warn(Supplier<String> supplier, Throwable cause) {
        if(logger.isWarnEnabled()) {
            logger.warn(supplier.get(), cause);
        }
    }

    public void error(String msg) {
        if(logger.isErrorEnabled()) {
            logger.error(msg);
        }
    }

    public void error(String msg, Object...params) {
        if(logger.isErrorEnabled()) {
            logger.error(msg, params);
        }
    }

    public void error(String msg, Throwable t) {
        if(logger.isErrorEnabled()) {
            logger.error(msg, t);
        }
    }

    public void error(Supplier<String> supplier) {
        if (logger.isErrorEnabled()) {
            logger.error(supplier.get());
        }
    }

    public void error(Supplier<String> supplier, Throwable cause) {
        if (logger.isErrorEnabled()) {
            logger.error(supplier.get(), cause);
        }
    }

    public SftpLogger(Logger logger) {
        super();
        this.logger = logger;
    }
}
