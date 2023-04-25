package DAO;

import static DAO.Configs.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.NoSuchElementException;
import java.sql.SQLException;

public class DAOPostgreSQLLoginners implements DAOManagment{

	private static final String URL = String.format("jdbc:postgresql://%s:%s/%s", HOST, PORT, DBNAME);
	private ConnectionFactory connectionFactory = new ConnectionFactory();
	private String tableName = "logginers";
	
	@Override
	public void add(Account account)throws SQLException {
		Connection connection = connectionFactory.createConnection(URL, USER, PASSWORD);
				Statement ps = connection.createStatement();
				ps.execute(String.format("INSERT INTO %s "
						+ "(email, name, second_name, password) VALUES('%s', '%s', '%s', '%s');", tableName,
						account.getEmail(), account.getName(), account.getSecondName(), account.getPassword()));
		connection.close();
		ps.close();
	}

	@Override
	public boolean isBusy(Account account) {
		try (Connection connection = connectionFactory.createConnection(URL, USER, PASSWORD);
				Statement statement = connection.createStatement();
				ResultSet set = statement.executeQuery(String.format("SELECT (EXISTS(SELECT email FROM %s WHERE email = '%s')) e;", tableName, account.getEmail()))){
				set.next();
				if(set.getString("e").equals("t")) {
					return true;
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	

	@Override
	public boolean autorization(Account account) {
		try {
			Account checkingAccount = getByEmail(account.getEmail());
			return account.equals(checkingAccount);
		}catch(NoSuchElementException e) {
			return false;
		}
	}

	@Override
	public Account getByEmail(String inpEmail) throws NoSuchElementException{
		try (Connection connection = connectionFactory.createConnection(URL, USER, PASSWORD);
				Statement ps = connection.createStatement();
				ResultSet resultSet = ps.executeQuery(String.format("SELECT %s FROM %s WHERE %s = '%s';", 
						"*", tableName, "email", inpEmail))){
			if(resultSet.next()) {
				int id = resultSet.getInt("id");
				String email = resultSet.getString("email");
				String name = resultSet.getString("name");
				String secondName = resultSet.getString("second_name");
				String password = resultSet.getString("password");
				return new Account(id, name, secondName, password, email);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new NoSuchElementException();
	}
}
