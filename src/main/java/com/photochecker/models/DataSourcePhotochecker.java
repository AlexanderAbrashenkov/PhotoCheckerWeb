package com.photochecker.models;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Created by market6 on 05.04.2017.
 */
public class DataSourcePhotochecker {
    private static final Context CONTEXT = createContext();
    private static final DataSource DATA_SOURCE = createDataSource();

    public static DataSource getDataSource() {
        return DATA_SOURCE;
    }

    private static Context createContext() {
        try {
            return new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static DataSource createDataSource() {
        try {
            Context envContext = (Context) CONTEXT.lookup("java:/comp/env");
            return (DataSource) envContext.lookup("jdbc/library");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
