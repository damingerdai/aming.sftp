package org.aming.sftp.utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import org.aming.sftp.exceptions.SftpException;
import org.aming.sftp.logger.LoggerManager;
import org.aming.sftp.logger.SftpLogger;
import org.aming.sftp.pool.SftpClientPool;

import java.util.Objects;

/**
 * Session工具类
 *
 * @athur aming
 * @date 2018-06-08 15:19
 */
public class SessionUtils {

    private static final SftpLogger logger = LoggerManager.getLogger(SessionUtils.class);

    public static Session getSession(SftpClientPool pool) throws SftpException {
        Assert.notNull(pool, "'pool' is required");

        try {
            Session session = pool.borrowObject();
            logger.debug("get session : '{}'", session);
            return session;
        } catch (Exception ex) {
            throw buildSftpException("fail to get a session from pool", ex);
        }
    }

    public static ChannelSftp openChannel(Session session) throws SftpException {
        Assert.notNull(session, "'session' is required");

        try {
            logger.debug("opening channel from '{}'", session);
            ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
            if(!channel.isConnected()) {
                channel.connect();
            }
            logger.debug("channel '{}' is open successfully", channel);
            return channel;
        } catch (Exception ex) {
            throw buildSftpException("fail to open channel from session", ex);
        }
    }

    public static void releaseSftpChannel(ChannelSftp channel, Session session, SftpClientPool pool) {
        if (Objects.nonNull(channel)) {
            channel.quit();
            channel = null;
        }

        try {
            if (Objects.nonNull(session)) {
                pool.returnObject(session);
                session = null;
            }
        } catch (Exception ex) {
            if (Objects.nonNull(session)) {
                session.disconnect();
                session = null;
            }
            logger.error("fail to return session to pool", ex);

        }
    }

    private static SftpException buildSftpException(String message, Throwable cause) {
        logger.error(message, cause);
        return new SftpException(message, cause);
    }
}
