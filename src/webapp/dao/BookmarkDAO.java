package webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import webapp.model.BookmarkBean;

public class BookmarkDAO {

	public void create(BookmarkBean bookmarkBean) throws Exception {
		try (Connection connection = Database.getConnection()) {
			String query = "insert into rishabhk_bookmark(email,url,comment,click_count) values(?,?,?,?)";
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bookmarkBean.getEmail());
			preparedStatement.setString(2, bookmarkBean.getUrl());
			preparedStatement.setString(3, bookmarkBean.getComment());
			preparedStatement.setInt(4, 0);
			preparedStatement.executeUpdate();
		}
	}

	public void update(BookmarkBean bookmarkBean) throws Exception {
		try (Connection connection = Database.getConnection()) {
			String query = "update rishabhk_bookmark set click_count = click_count+1 where email = ? and url = ? and comment = ?";
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bookmarkBean.getEmail());
			preparedStatement.setString(2, bookmarkBean.getUrl());
			preparedStatement.setString(3, bookmarkBean.getComment());
			preparedStatement.executeUpdate();
		}
	}

	public List<BookmarkBean> getUserBookmarks(BookmarkBean bookmarkBean) throws Exception {
		List<BookmarkBean> listBookmarkBeans = new ArrayList<BookmarkBean>();
		try (Connection connection = Database.getConnection()) {
			String query = "Select * from rishabhk_bookmark where email = ?";
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bookmarkBean.getEmail());
			ResultSet resultSet = preparedStatement.executeQuery();
			BookmarkBean tempBookmarkBean = null;
			while (resultSet.next()) {
				tempBookmarkBean = new BookmarkBean();
				tempBookmarkBean.setEmail(resultSet.getString("email"));
				tempBookmarkBean.setUrl(resultSet.getString("url"));
				tempBookmarkBean.setComment(resultSet.getString("comment"));
				tempBookmarkBean.setCount(resultSet.getInt("click_count"));
				listBookmarkBeans.add(tempBookmarkBean);
			}
			return listBookmarkBeans;
		}
	}

	public List<BookmarkBean> getAllBookmarks() throws Exception {
		List<BookmarkBean> listBookmarkBeans = new ArrayList<BookmarkBean>();
		try (Connection connection = Database.getConnection()) {
			String query = "Select * from rishabhk_bookmark";
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			BookmarkBean tempBookmarkBean = null;
			while (resultSet.next()) {
				tempBookmarkBean = new BookmarkBean();
				tempBookmarkBean.setEmail(resultSet.getString("email"));
				tempBookmarkBean.setUrl(resultSet.getString("url"));
				tempBookmarkBean.setComment(resultSet.getString("comment"));
				tempBookmarkBean.setCount(resultSet.getInt("click_count"));
				listBookmarkBeans.add(tempBookmarkBean);
			}
			
			return listBookmarkBeans;
		}
	}

	public void delete(BookmarkBean bookmarkBean) throws Exception {
		try (Connection connection = Database.getConnection()) {
			String query = "delete from rishabhk_bookmark where email = ? and url = ? and comment = ?";
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bookmarkBean.getEmail());
			preparedStatement.setString(2, bookmarkBean.getUrl());
			preparedStatement.setString(3, bookmarkBean.getComment());
			preparedStatement.executeUpdate();
		}
	}

	public void initializeTables() throws Exception {
		try (Connection connection = Database.getConnection()) {
			String query = "CREATE TABLE IF NOT EXISTS `rishabhk_bookmark` ("
					+ "`id` int(20) PRIMARY KEY NOT NULL AUTO_INCREMENT," + "`email` varchar(100) NOT NULL,"
					+ "`url` varchar(100) NOT NULL," + "`comment` varchar(100) NOT NULL,"
					+ "`click_count` int(20) NOT NULL" + ")";
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement(query);
			int tableStatus = preparedStatement.executeUpdate();

			query = "Select * from rishabhk_bookmark";
			preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				query = "INSERT INTO `rishabhk_bookmark` " + "(`id`, `email`, `url`, `comment`, `click_count`) VALUES "
						+ "(1, 'rishabhk@gmail.com', 'http://google.com', 'search engine', 0),"
						+ "(2, 'rishabhk@gmail.com', 'ebay.com', 'Time to shop', 0),"
						+ "(3, 'rishabhk@gmail.com', 'cmu.com', 'College', 0),"
						+ "(4, 'rishabhk@gmail.com', 'facebook.com', 'social media', 0),"
						+ "(5, 'aswin@gmail.com', 'twitter.com', 'fun stuff', 0),"
						+ "(6, 'aswin@gmail.com', 'linkedin.com', 'networking', 0),"
						+ "(7, 'aswin@gmail.com', 'housing.com', 'buy me a house', 0),"
						+ "(8, 'aswin@gmail.com', 'amazon.com', 'shop more', 0),"
						+ "(9, 'nithin@gmail.com', 'http://snapchat.com', 'pic time', 0),"
						+ "(10, 'nithin@gmail.com', 'http://gaana.com', 'songs time', 0),"
						+ "(11, 'nithin@gmail.com', 'gmail.com', 'check your mail', 0),"
						+ "(12, 'nithin@gmail.com', 'http://yahoo.com', 'another search engine', 0);";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.executeUpdate();
			}
		}

	}

}
