package org.aming.sftp.utils;

/**
 * 消息格式化
 *
 * @athur aming
 * @date 2018-06-08 14:53
 */
@FunctionalInterface
public interface MessageFormatter {

    String get(String message, Object...params);

    default String get(String message) {
        return message;
    }
}
