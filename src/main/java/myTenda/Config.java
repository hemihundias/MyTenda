/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myTenda;

/**
 *
 * @author Hemihundias
 */

public class Config {
    private dbConnection dbConnection;
    private hibernate hibernate;

    Config(dbConnection dbConnection, hibernate hibernate) {
        this.dbConnection = dbConnection;
        this.hibernate = hibernate;
    }

    dbConnection getDbConnection() {
        return dbConnection;
    }

    void setDbConnection(dbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    hibernate getHibernate() {
        return hibernate;
    }

    void setHibernate(hibernate hibernate) {
        this.hibernate = hibernate;
    }
    
    
    class dbConnection{
        private String address,name,user,password;
        private int port;

        public dbConnection() {
        }

        public dbConnection(String address, String name, String user, String password, int port) {
            this.address = address;
            this.name = name;
            this.user = user;
            this.password = password;
            this.port = port;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
        
        
    }
    
    class hibernate{
        private String driver,dialect,HBM2DDL_AUTO;
        private boolean SHOW_SQL;

        public hibernate() {
        }

        public hibernate(String driver, String dialect, String HBM2DDL_AUTO, boolean SHOW_SQL) {
            this.driver = driver;
            this.dialect = dialect;
            this.HBM2DDL_AUTO = HBM2DDL_AUTO;
            this.SHOW_SQL = SHOW_SQL;
        }

        public String getDriver() {
            return driver;
        }

        public void setDriver(String driver) {
            this.driver = driver;
        }

        public String getDialect() {
            return dialect;
        }

        public void setDialect(String dialect) {
            this.dialect = dialect;
        }

        public String getHBM2DDL_AUTO() {
            return HBM2DDL_AUTO;
        }

        public void setHBM2DDL_AUTO(String HBM2DDL_AUTO) {
            this.HBM2DDL_AUTO = HBM2DDL_AUTO;
        }

        public boolean getSHOW_SQL() {
            return SHOW_SQL;
        }

        public void setSHOW_SQL(boolean SHOW_SQL) {
            this.SHOW_SQL = SHOW_SQL;
        }
        
        
    }
}
