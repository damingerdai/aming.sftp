package org.aming.sftp.logger;

import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 日志管理器
 *
 * @athur aming
 * @date 2018-06-08 13:47
 */
public class LoggerManager {

    private LoggerManager() {
    }

    private static Map<String, SftpLogger> cache;

    static {
        cache = new HashMap<>();
    }

    public static SftpLogger getLogger(String loggerName) {
        return doGetLogger(loggerName);
    }

    public static SftpLogger getLogger(Class<?> className) {
        return doGetLogger(className.getName());
    }

    private static SftpLogger doGetLogger(String loggerName) {
        if (cache.containsKey(loggerName)) {
            return cache.get(loggerName);
        } else {
            SftpLogger logger = new SftpLogger(LoggerFactory.getLogger(loggerName));
            cache.put(loggerName, logger);
            return logger;
        }
    }

}
