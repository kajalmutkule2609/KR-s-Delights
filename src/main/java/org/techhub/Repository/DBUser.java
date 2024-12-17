package org.techhub.Repository;

import java.sql.*;

public class DBUser {
	DBConfig configdb = DBConfig.getInstance();
	protected Connection conn = DBConfig.getConn();
	protected ResultSet rs = DBConfig.getResult();
	protected PreparedStatement stmt = DBConfig.getStatement();
	protected CallableStatement cstmt = configdb.getCallStatement();
}
