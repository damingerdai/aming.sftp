package org.aming.sftp.pool;

import com.jcraft.jsch.Session;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @athur aming
 * @date 2018-06-08 15:25
 */
public class SftpClientPool extends GenericObjectPool<Session> {

    @Override
    public Session borrowObject() throws Exception {
        return this.borrowObject(getMaxWaitMillis());
    }

    @Override
    public Session borrowObject(long borrowMaxWaitMillis) throws Exception {
        Session session = super.borrowObject(borrowMaxWaitMillis);
        if(session.isConnected()) {
            return session;
        } else {
            super.addObject();
            return getFactory().makeObject().getObject();
        }
    }

    @Override
    public void returnObject(Session session)  {
        if(session.isConnected()) {
            super.returnObject(session);
        } else {
            try {
                super.addObject();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            session = null;
        }

    }

    public SftpClientPool(SftpClientFactory factory, GenericObjectPoolConfig config) {
        super(factory, config);
    }
}
