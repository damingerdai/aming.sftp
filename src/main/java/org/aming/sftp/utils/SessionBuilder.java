package org.aming.sftp.utils;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.aming.sftp.exceptions.ExceptionBuilder;
import org.aming.sftp.exceptions.SftpException;

import java.util.Properties;

/**
 * Session创建者
 *
 * @athur aming
 * @date 2018-06-08 13:59
 */
public class SessionBuilder {

    public static Session newSessionWithPassword(String host, int port, String username, String password, int timeout) throws SftpException {
        try {
            Session session = newSession(username, host, port, timeout);
            session.setPassword(password);
            return session;
        } catch (JSchException ex) {
           throw ExceptionBuilder.buildException(ex.getMessage(), ex);
        }
    }

    public static Session newSessionWithPassword(String host, int port, String username, byte[] password, int timeout) throws SftpException {
        try {
            Session session = newSession(username, host, port, timeout);
            session.setPassword(password);
            return session;
        } catch (JSchException ex) {
            throw ExceptionBuilder.buildException(ex.getMessage(), ex);
        }
    }

    public static Session newSessionWithPrivateKey(String host, int port, String username, String privateKey, int timeout) throws SftpException {
        try {
            JSch jSch = newJSch(privateKey);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            return newSession(jSch,username, host, port, timeout, config);
        } catch (JSchException ex) {
            throw ExceptionBuilder.buildException(ex.getMessage(), ex);
        }
    }


    private static JSch newJSch() {
        return new JSch();
    }

    private static JSch newJSch(String privateKey) throws JSchException {
        JSch jSch = newJSch();
        jSch.addIdentity(privateKey);
        return jSch;
    }

    private static Session newSession(String username, String host, int port, int timeout) throws JSchException {
        JSch jSch = newJSch();
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        return newSession(jSch, username, host, port, timeout, config);
    }

    private static Session newSession(JSch jSch, String username, String host, int port, int timeout, Properties config)
            throws JSchException {
        Session session = jSch.getSession(username, host, port);
        session.setTimeout(timeout);
        session.setConfig(config);
        return session;
    }
}
