package org.aming.sftp.support;

import org.aming.sftp.logger.LoggerManager;
import org.aming.sftp.logger.SftpLogger;
import org.aming.sftp.pool.SftpClientPool;

/**
 * sftp属性定义
 *
 * @athur aming
 * @date 2018-06-08 15:57
 */
public class SftpAccessor {
    protected final SftpLogger logger = LoggerManager.getLogger(getClass());

    private SftpClientPool sftpClientPool;

    public SftpClientPool getSftpClientPool() {
        return sftpClientPool;
    }

    public void setSftpClientPool(SftpClientPool sftpClientPool) {
        this.sftpClientPool = sftpClientPool;
    }
}
