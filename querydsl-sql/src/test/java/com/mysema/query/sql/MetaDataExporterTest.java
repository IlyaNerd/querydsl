/*
 * Copyright (c) 2008 Mysema Ltd.
 * All rights reserved.
 * 
 */
package com.mysema.query.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.junit.Test;


/**
 * MetaDataExporterTest provides
 *
 * @author tiwe
 * @version $Id$
 */                
public class MetaDataExporterTest {
    
    @Test
    public void testGeneration() throws Exception{
        Connection conn = getHSQLConnection();
        Statement st = conn.createStatement();
        st.execute("drop table survey if exists");
        st.execute("create table survey (id int,name varchar(30));");
        try{
            MetaDataExporter e = new MetaDataExporter();
            e.setTargetFolder("target");
            e.setNamePrefix("Q");
            e.setPackageName("com.mysema.query.sql.domain");
            e.setCamelCase(true);
            e.export(conn.getMetaData());    
        }finally{
            if (st != null) st.close();
            if (conn != null) conn.close();
        }
        
    }
    
    private Connection getHSQLConnection() throws Exception{
        Class.forName("org.hsqldb.jdbcDriver");
        String url = "jdbc:hsqldb:data/tutorial";
        return DriverManager.getConnection(url, "sa", "");
    }
    
}
