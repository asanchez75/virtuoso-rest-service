package fr.uness.servlets;

import java.sql.Connection;
import java.sql.SQLException;

import virtuoso.jdbc4.VirtuosoConnectionPoolDataSource;
import virtuoso.jdbc4.VirtuosoPoolStatistic;

public final class VirtuosoDatabase {

    private static final VirtuosoConnectionPoolDataSource dataSource = new  VirtuosoConnectionPoolDataSource();

    static {        
//        dataSource.setUser("dba");
//        dataSource.setPassword("dba");
//        dataSource.setPortNumber(1111);
//        dataSource.setServerName("localhost");
        dataSource.setUser("dba");
        dataSource.setPassword("ontosides");
        dataSource.setPortNumber(1111);
        dataSource.setServerName("host.docker.internal");
        dataSource.setCharset("utf-8");
        dataSource.setLog_Enable(2);
        try {
            dataSource.setMaxPoolSize(32);
            dataSource.setMinPoolSize(10);
            //System.out.println("it works amigo!");
        } catch (final SQLException e) {
            throw new RuntimeException("Unable to configure virtuoso connection pool size", e);
        }        
    }

    public VirtuosoDatabase() {
        //
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

	public static VirtuosoPoolStatistic getAll_statistics() {
		// TODO Auto-generated method stub
		return dataSource.get_statistics();
	}

}
