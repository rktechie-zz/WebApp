package webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import webapp.model.UserBean;

public class UserDAO {

	public void create(UserBean newUser) throws Exception {
		try (Connection connection = Database.getConnection()) {
			String query = "insert into rishabhk_user(email,first_name,last_name,password) values(?,?,?,?)";
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, newUser.getEmail());
			preparedStatement.setString(2, newUser.getFirstName());
			preparedStatement.setString(3, newUser.getLastName());
			preparedStatement.setString(4, newUser.getPassword());
			preparedStatement.executeUpdate();
		}
	}

	public UserBean login(String email, String password) throws Exception {
		try (Connection connection = Database.getConnection()) {
			String query = "Select * from rishabhk_user where email = ? and password = ?";
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				UserBean user = new UserBean();
				user.setEmail(resultSet.getString("email"));
				user.setFirstName(resultSet.getString("first_name"));
				user.setLastName(resultSet.getString("last_name"));

				return user;
			}
			return null;
		}
	}

	public UserBean lookup(String email) throws Exception {
		try (Connection connection = Database.getConnection()) {
			String query = "Select * from rishabhk_user where email = ?";
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				UserBean user = new UserBean();
				user.setEmail(resultSet.getString("email"));
				user.setFirstName(resultSet.getString("first_name"));
				user.setLastName(resultSet.getString("last_name"));

				return user;
			}
			return null;
		}
	}

	public List<UserBean> getAllUsers() throws Exception {
		try (Connection connection = Database.getConnection()) {
			String query = "Select email from rishabhk_user";
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<UserBean> allUsers = new ArrayList<UserBean>();
			UserBean user;
			while (resultSet.next()) {
				user = new UserBean();
				user.setEmail(resultSet.getString("email"));
				allUsers.add(user);
			}
			return allUsers;
		}
	}

	public void changePassword(UserBean userBean) throws Exception {
		try (Connection connection = Database.getConnection()) {
			String query = "update rishabhk_user set password = ? where email = ?";
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, userBean.getPassword());
			preparedStatement.setString(2, userBean.getEmail());
			preparedStatement.executeUpdate();
		}
	}

	public void initializeTables() throws Exception {
		try (Connection connection = Database.getConnection()) {
			String query = "CREATE TABLE IF NOT EXISTS `rishabhk_user` (" + "`email` varchar(100) NOT NULL,"
					+ "`first_name` varchar(50) NOT NULL," + "`last_name` varchar(50) NOT NULL,"
					+ "`password` varchar(50) NOT NULL," + "`id` int(20) PRIMARY KEY NOT NULL AUTO_INCREMENT);";

			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();

			query = "Select * from rishabhk_user";
			preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				query = "INSERT INTO `rishabhk_user` "
						+ "(`email`, `first_name`, `last_name`, `password`, `id`) VALUES "
						+ "('rishabhk@gmail.com', 'Rishabh', 'Kedia', 'rish12', 1),"
						+ "('nithin@gmail.com', 'Nithin', 'Donnipad', 'nithin123', 2),"
						+ "('aswin@gmail.com', 'Aswin', 'L', 'aswin123', 3);";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.executeUpdate();
			}
		}
	}
}
