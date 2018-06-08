package org.aming.sftp.pool;

import com.jcraft.jsch.Session;
import org.aming.sftp.config.SftpClientConfigure;
import org.aming.sftp.logger.LoggerManager;
import org.aming.sftp.logger.SftpLogger;
import org.aming.sftp.utils.SessionBuilder;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;

/**
 * @athur aming
 * @date 2018-06-08 15:25
 */
public class SftpClientFactory extends BasePooledObjectFactory<Session> {

    private final SftpLogger logger = LoggerManager.getLogger(getClass());

    private SftpClientConfigure configure;

    @Override
    public Session create() throws Exception {
        Session session = SessionBuilder.newSessionWithPrivateKey(configure.getHost(), configure.getPort(), configure.getUsername(), configure.getPrivateKey(), configure.getTimeout());
        logger.debug("session is created");
        session.connect();
        logger.debug("session is connecting server");
        return session;
    }

    @Override
    public PooledObject<Session> wrap(Session obj) {
        return null;
    }
}
