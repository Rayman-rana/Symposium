package it.blackhat.symposium.managers;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author didacus
 */
public abstract class ConnectionManager {

    protected DataSource dataSource;
    private static Context ctx;
    private static final Log CONNECTION_LOG = LogFactory.getLog(ConnectionManager.class);

    static {
        try {
            ctx = new InitialContext();
        } catch (NamingException ex) {
            CONNECTION_LOG.fatal("Could not create connection", ex);
        }
    }

    /**
     * Create a new Connection Manager with specified DaraSource. Use it in testing environment for
     * configure a mock DB.
     *
     * @param ds datasource to pass
     */
    public ConnectionManager(DataSource ds) {
        dataSource = ds;
    }

    /**
     * Create a prebuild ConnectionManager with production Datasource configured
     */
    public ConnectionManager() {
        createDefaultDataSource();
    }
    
    /**
     * Create a default connection for develop
     */
    private synchronized void createDefaultDataSource() {
        try {
            this.dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/SymposiumDB");
        } catch (NamingException ex) {
            CONNECTION_LOG.fatal("Could not create datasource", ex);
        }
    }
}
