package org.aming.sftp.support;

import org.aming.sftp.exceptions.SftpException;

import java.io.File;
import java.nio.file.Path;
import java.util.Set;

/**
 * sftp操作接口
 *
 * @athur aming
 * @date 2018-06-08 15:58
 */
public interface SftpOperations {

    /**
     * 下载文件到本地的临时文件
     * @param remotePath
     * @param fileName
     * @return
     * @throws SftpException
     */
    File downloadFile(String remotePath, String fileName) throws SftpException;

    /**
     * 下载文件到<code>localPath</code>目录
     * @param remotePath
     * @param fileName
     * @param localPath
     * @throws SftpException
     */
    void downloadFile(String remotePath, String fileName, Path localPath) throws SftpException;

    /**
     * 上传文件
     * @param remotePath
     * @param file
     * @throws SftpException
     */
    void uploadFile(String remotePath, File file) throws SftpException;

    /**
     * 删除文件
     * @param remotePath
     * @param fileName
     * @throws SftpException
     */
    void deleteFile(String remotePath, String fileName) throws SftpException;

    /**
     * 从指定目录下加载所有文件名
     * @param remotePath
     * @return
     * @throws SftpException
     */
    Set<String> listFileNames(String remotePath) throws SftpException;

    /**
     * 从指定目录下加载所有后缀名为<code>suffix</code>文件名
     * @param remotePath
     * @return
     * @throws SftpException
     */
    Set<String> listFileNames(String remotePath, String suffix) throws SftpException;

    /**
     * 将<code>srcPath</code>目录下的<code>fileName</code>移动到<code>targetPath</code>目录下
     * 如果<code>targetPath</code>目录下存在<code>fileName</code>文件，将会直接覆盖
     * Note: 这不是复制，是移动
     * @param srcPath
     * @param targetPath
     * @param fileName
     * @throws SftpException
     */
    void moveFile(String srcPath, String targetPath, String fileName) throws SftpException;

    /**
     * 将<code>srcPath</code>目录下的<code>fileName</code>移动到<code>targetPath</code>目录下
     * 如果<code>targetPath</code>目录下存在<code>fileName</code>文件，将会直接覆盖
     * Note: 这不是复制，是移动
     * @param srcPath
     * @param targetPath
     * @param srcFileName
     * @param targetFileName
     * @throws SftpException
     */
    void moveFile(String srcPath, String targetPath, String srcFileName, String targetFileName) throws SftpException;
}
