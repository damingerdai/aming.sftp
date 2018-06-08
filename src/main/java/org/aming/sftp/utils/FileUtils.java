package org.aming.sftp.utils;

import java.io.File;
import java.io.IOException;

/**
 * @athur aming
 * @date 2018-06-08 16:11
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    public static String getFileName(String file) {
        if (file != null) {
            if (file.contains("/")) {
                return file.substring(file.lastIndexOf("/") + 1);
            } else {
                return file;
            }
        }
        return null;
    }

    /**
     * 获取不包含后缀的文件名
     * @since 2017-11-22
     */
    public static String getPrefix(String fileName) {
        Assert.notNull(fileName, "fileName is required");
        return fileName.substring(0,fileName.lastIndexOf("."));
    }

    /**
     * 获取文件后缀名
     * @since 2017-11-22
     */
    public static String getSuffix(String fileName) {
        Assert.notNull(fileName, "fileName is required");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 生成临时文件，需要自行删除
     * @param prefix
     * @param suffix
     * @return
     * @throws java.io.IOException
     */
    public static File createTempFile(String prefix, String suffix) throws IOException {
        if(!suffix.startsWith(".")) {
            suffix = "." + suffix;
        }
        String tempPath = getTempDirectoryPath();
        File file = new File(tempPath + prefix + suffix);
        if(!file.exists()) {
            file.createNewFile();
        }
        return file;
    }
}
