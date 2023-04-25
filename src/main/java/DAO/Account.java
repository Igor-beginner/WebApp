package DAO;

public class Account {
	private int id;
	private String name;
	private String secondName;
	private String password;
	private String email;
	
	public Account(int id, String name, String secondName, String password, String email) {
		this.id = id;
		this.name = name;
		this.secondName = secondName;
		this.password = password;
		this.email = email;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getSecondName() {
		return secondName;
	}
	public String getPassword() {
		return password;
	}
	public String getEmail() {
		return email;
	}
	
	@Override
	public boolean equals(Object obj) {
		Account account =  (Account) obj;
		return email.equals(account.getEmail()) &&
				password.equals(account.getPassword());
	}
}
