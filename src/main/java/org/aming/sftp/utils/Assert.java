package org.aming.sftp.utils;

import java.util.Objects;

/**
 * @athur aming
 * @date 2018-06-08 16:02
 */
public class Assert {

    public static void notNull(Object object, String message) {
        if (Objects.nonNull(object)) {
            throw new IllegalArgumentException(message);
        }
    }
}
