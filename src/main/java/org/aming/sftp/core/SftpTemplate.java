package org.aming.sftp.core;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import org.aming.sftp.exceptions.ExceptionBuilder;
import org.aming.sftp.exceptions.SftpException;
import org.aming.sftp.pool.SftpClientPool;
import org.aming.sftp.support.SftpAccessor;
import org.aming.sftp.support.SftpOperations;
import org.aming.sftp.utils.Assert;
import org.aming.sftp.utils.FileUtils;
import org.aming.sftp.utils.SessionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * @athur aming
 * @date 2018-06-08 16:01
 */
public class SftpTemplate extends SftpAccessor implements SftpOperations {

    @Override
    public File downloadFile(String remotePath, String fileName) throws SftpException {
        Assert.notNull(remotePath, "remote path is required");
        Assert.notNull(fileName, "fileName is required");


        logger.debug("download file [{}] from sftp server [{}]", fileName,remotePath);


        Session session = null;
        ChannelSftp channel = null;
        try{
            session = SessionUtils.getSession(getSftpClientPool());
            channel = SessionUtils.openChannel(session);

            String[] paths = StringUtils.split(remotePath, File.separator);
            for(String path : paths) {
                channel.cd(path);
            }

            String prefix = FileUtils.getPrefix(fileName);
            String suffix = FileUtils.getSuffix(fileName);
            // 创建临时文件
            File file = FileUtils.createTempFile(prefix, suffix);

            try(InputStream is = channel.get(fileName)){
                FileUtils.copyInputStreamToFile(is,file);
            }
            return file;
        } catch (IOException ex) {
            SessionUtils.releaseSftpChannel(channel, session, getSftpClientPool());
            throw ExceptionBuilder.buildException(ex.getMessage(), ex);
        } catch (Exception ex) {
            SessionUtils.releaseSftpChannel(channel, session, getSftpClientPool());
            throw ExceptionBuilder.buildException("fail to download file from sftp server", ex);
        } finally {
            SessionUtils.releaseSftpChannel(channel, session, getSftpClientPool());
        }
    }

    @Override
    public void downloadFile(String remotePath, String fileName, Path localPath) throws SftpException {
        Assert.notNull(remotePath, "remote path is required");
        Assert.notNull(fileName, "fileName is required");


            logger.debug("download file [{}] from sftp server [{}]",fileName,remotePath);


        Session session = null;
        ChannelSftp channel = null;
        try{
            session = SessionUtils.getSession(getSftpClientPool());
            channel = SessionUtils.openChannel(session);

            String[] paths = StringUtils.split(remotePath,File.separator);
            for(String path : paths) {
                channel.cd(path);
            }

            Files.copy(channel.get(fileName), localPath, StandardCopyOption.ATOMIC_MOVE);

        } catch (IOException ex) {
            SessionUtils.releaseSftpChannel(channel, session, getSftpClientPool());
            throw ExceptionBuilder.buildException(ex.getMessage(), ex);
        } catch (Exception ex) {
            SessionUtils.releaseSftpChannel(channel, session, getSftpClientPool());
            throw ExceptionBuilder.buildException("fail to download file from sftp server", ex);
        } finally {
            SessionUtils.releaseSftpChannel(channel, session, getSftpClientPool());
        }
    }

    @Override
    public void uploadFile(String remotePath, File file) throws SftpException {
        Assert.notNull(remotePath, "remote path is required");
        Assert.notNull(file, "file is required");


            logger.debug("upload file [{}] to sftp server [{}]",file.getName(),remotePath);


        Session session = null;
        ChannelSftp channel = null;
        try {
            session = SessionUtils.getSession(getSftpClientPool());
            channel = SessionUtils.openChannel(session);

            String[] paths = StringUtils.split(remotePath,File.separator);
            for(String path : paths) {
                channel.cd(path);
            }

            try(InputStream is = new FileInputStream(file)) {
                channel.put(is,file.getName());
            }
        } catch (IOException ex) {
            SessionUtils.releaseSftpChannel(channel, session, getSftpClientPool());
            throw ExceptionBuilder.buildException(ex.getMessage(), ex);
        } catch (Exception ex) {
            SessionUtils.releaseSftpChannel(channel, session, getSftpClientPool());
            throw ExceptionBuilder.buildException("fail to download file from sftp server", ex);
        } finally {
            SessionUtils.releaseSftpChannel(channel, session, getSftpClientPool());
        }
    }

    @Override
    public void deleteFile(String remotePath, String fileName) throws SftpException {
        Assert.notNull(remotePath, "remote path is required");
        Assert.notNull(fileName, "fileName is required");


            logger.debug("delete file [{}] in sftp server [{}]",fileName,remotePath);


        Session session = null;
        ChannelSftp channel = null;
        try {
            session = SessionUtils.getSession(getSftpClientPool());
            channel = SessionUtils.openChannel(session);

            String[] paths = StringUtils.split(remotePath,File.separator);
            for(String path : paths) {
                channel.cd(path);
            }

            channel.rm(fileName);
        } catch (Exception ex){
            SessionUtils.releaseSftpChannel(channel, session, getSftpClientPool());
            throw ExceptionBuilder.buildException("fail to delete file in sftp server",ex);
        } finally {
            SessionUtils.releaseSftpChannel(channel, session, getSftpClientPool());
        }
    }

    @Override
    public Set<String> listFileNames(String remotePath) throws SftpException {
        Assert.notNull(remotePath, "remote path is required");

        logger.debug("list file in sftp server [{}]",remotePath);

        Session session = null;
        ChannelSftp channel = null;
        try {
            session = SessionUtils.getSession(getSftpClientPool());
            channel = SessionUtils.openChannel(session);

            String[] paths = StringUtils.split(remotePath,File.separator);
            for(int i=0;i < paths.length - 1;i++ ) {
                channel.cd(paths[i]);
            }

            Vector<?> vector = channel.ls(paths[paths.length -1]);
            Set<String> result = new HashSet<>(vector.size());
            for(Object obj : vector) {
                if(obj instanceof ChannelSftp.LsEntry) {
                    ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry)obj;
                    // 可能存在bug
                    if(!lsEntry.getAttrs().isDir()) {
                        result.add(lsEntry.getFilename());
                    }
                }
            }
            return result;
        } catch (Exception ex){
            SessionUtils.releaseSftpChannel(channel, session, getSftpClientPool());
            throw ExceptionBuilder.buildException("fail to list file in sftp server", ex);
        } finally {
            SessionUtils.releaseSftpChannel(channel, session, getSftpClientPool());
        }
    }

    @Override
    public Set<String> listFileNames(String remotePath, String suffix) throws SftpException {
        Assert.notNull(remotePath, "remote path is required");
        Assert.notNull(suffix, "suffix is required");

        logger.debug("list file in sftp server [{}]",remotePath);

        Session session = null;
        ChannelSftp channel = null;
        try {
            session = SessionUtils.getSession(getSftpClientPool());
            channel = SessionUtils.openChannel(session);

            String[] paths = StringUtils.split(remotePath,File.separator);
            for(int i=0;i < paths.length - 1;i++ ) {
                channel.cd(paths[i]);
            }

            Vector<?> vector = channel.ls(paths[paths.length -1]);
            Set<String> result = new HashSet<>(vector.size());
            for(Object obj : vector) {
                if(obj instanceof ChannelSftp.LsEntry) {
                    ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry)obj;
                    // 可能存在bug
                    if(lsEntry.getAttrs().isDir() ) {
                        continue;
                    }
                    if(suffix.equalsIgnoreCase(FileUtils.getSuffix(lsEntry.getFilename()))) {
                        result.add(lsEntry.getFilename());
                    }
                }
            }
            return result;
        } catch (Exception ex){
            SessionUtils.releaseSftpChannel(channel, session, getSftpClientPool());
            throw ExceptionBuilder.buildException("fail to list file in sftp server", ex);
        } finally {
            SessionUtils.releaseSftpChannel(channel, session, getSftpClientPool());
        }
    }

    @Override
    public void moveFile(String srcPath, String targetPath, String fileName) throws SftpException {
        moveFile(srcPath, targetPath, fileName, fileName);
    }

    @Override
    public void moveFile(String srcPath, String targetPath, String srcFileName, String targetFileName) throws SftpException {
        Assert.notNull(srcPath, "src path is required");
        Assert.notNull(targetPath, "target path is required");
        Assert.notNull(srcFileName, "src file name is required");
        Assert.notNull(targetFileName, "target file name is required");

        Session session = null;
        ChannelSftp channel = null;
        try {
            session = SessionUtils.getSession(getSftpClientPool());
            channel = SessionUtils.openChannel(session);

            List<String> cds = new ArrayList<>();
            StringBuffer newSrcPath = new StringBuffer();
            StringBuffer newTarPath = new StringBuffer();

            processPath(srcPath, targetPath, cds, newSrcPath, newTarPath, srcFileName, targetFileName);

            for(String cd:cds) {
                channel.cd(cd);
            }

            channel.rename(newSrcPath.toString(),newTarPath.toString());

        } catch (Exception ex) {
            SessionUtils.releaseSftpChannel(channel, session, getSftpClientPool());
            throw ExceptionBuilder.buildException("fail to move file in sftp server", ex);
        } finally {
            SessionUtils.releaseSftpChannel(channel, session, getSftpClientPool());
        }
    }

    private void processPath(String srcPath, String tarPath, List<String> cds, StringBuffer newSrcPath, StringBuffer newTarPath, String srcFileName, String targetFileName) throws SftpException {
        try {
            String[] srcPathArrays = StringUtils.split(srcPath,"\\\\");
            String[] tarPathArrays = StringUtils.split(tarPath,"\\\\");

            int length = srcPathArrays.length > tarPathArrays.length ? tarPathArrays.length : srcPathArrays.length;

            int n = length;
            for(int i=0;i < length;i++) {
                if(srcPathArrays[i].equals(tarPathArrays[i])) {
                    cds.add(srcPathArrays[i]);
                } else {
                    n = i;
                    break;
                }
            }

            for(int i=n;i< srcPathArrays.length;i++ ) {
                newSrcPath.append(srcPathArrays[i]).append("/");
            }
            newSrcPath.append(srcFileName);
            for(int i=n;i< tarPathArrays.length;i++ ) {
                newTarPath.append(tarPathArrays[i]).append("/");
            }
            newTarPath.append(targetFileName);
        } catch (Exception ex) {
            throw ExceptionBuilder.buildException("fail to parse path :" + srcPath + "and" + tarPath,ex);
        }
    }

    public SftpTemplate() {
        super();
    }

    public SftpTemplate(SftpClientPool sftpClientPool) {
        super();
        this.setSftpClientPool(sftpClientPool);
    }
}
