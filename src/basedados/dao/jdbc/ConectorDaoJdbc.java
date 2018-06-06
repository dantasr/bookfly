package basedados.dao.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import basedados.BaseDadosException;
import basedados.ConectorJDBC;

public class ConectorDaoJdbc extends ConectorJDBC {

	private static String PASSWORD;
	private static String USER;
	private static String HOST;
	private static String DB_NAME;
	private static DB DATABASE;
	protected static boolean jaCriouBD;
	
	public ConectorDaoJdbc() throws BaseDadosException {
		Properties props = new Properties();
		FileInputStream file;
		try {
			file = new FileInputStream("database_config.properties");
			props.load(file);
			PASSWORD = props.getProperty("password");
			HOST = props.getProperty("host");
			USER = props.getProperty("user");
			DB_NAME = props.getProperty("dbName");
			DATABASE = props.getProperty("database").equals("mysql") ? DB.MYSQL
					: DB.POSTGRES;
		} catch (IOException e) {
			throw new BaseDadosException(
					"Problemas ao tentar ler o arquivo database_config.properties");
		}
	}

	@Override
	protected String getUser() {
		return USER;
	}

	@Override
	protected String getPassword() {
		return PASSWORD;
	}

	@Override
	protected String getDbHost() {
		return HOST;
	}

	@Override
	protected String getDbName() {
		return jaCriouBD ? DB_NAME : "";
	}

	@Override
	protected DB getDB() {
		return DATABASE;
	}
}
