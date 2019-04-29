package test;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class IDButil1 {

	public abstract Connection getconnection() throws ClassNotFoundException, SQLException;
}
