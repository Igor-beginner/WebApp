package DAO;

import java.sql.SQLException;

public interface DAOManagment {
	void add(Account account) throws SQLException;
	boolean isBusy(Account account);
	boolean autorization(Account account);
	Account getByEmail(String email);
}
