package org.aming.sftp.config;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * sftp连接客户端配置类
 *
 * @athur aming
 * @date 2018-06-08 15:27
 */
public class SftpClientConfigure implements Serializable {

    private static final long serialVersionUID = -5484451341172452107L;

    private static final int DEFAULT_TIMEOUT = 60000;
    private static final int DEFAULT_PORT = 22;

    private String host;
    private int port = DEFAULT_PORT;
    private String username;
    private String password;
    private int timeout = DEFAULT_TIMEOUT;
    private String privateKey;

    public String getHost() {
        return host;
    }

    public SftpClientConfigure setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public SftpClientConfigure setPort(int port) {
        this.port = port;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public SftpClientConfigure setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SftpClientConfigure setPassword(String password) {
        this.password = password;
        return this;
    }

    public int getTimeout() {
        return timeout;
    }

    public SftpClientConfigure setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public SftpClientConfigure setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    public SftpClientConfigure() {
        super();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
