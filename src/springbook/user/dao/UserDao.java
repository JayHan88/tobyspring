package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.domain.User;

public class UserDao {

	private ConnectionMaker connectionMaker;

	/*public UserDao() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		this.connectionMaker = context.getBean("connectionMaker", ConnectionMaker.class);
	}*/

	/*public UserDao() {
		DaoFactory daoFactory = new DaoFactory();
		this.connectionMaker = daoFactory.connectionMaker();
	}*/

	public UserDao(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}

	public void add(User user) throws ClassNotFoundException, SQLException {

		Connection c = connectionMaker.makeConnection();
		// Connection c = simpleConnectionMaker.makeNewConnection();
		// Connection c = getConnection();
		// getConnection() 메소드를 추출해서 중복 제거
		// Class.forName("com.mysql.jdbc.Driver");
		// Connection c = DriverManager.getConnection("jdbc:mysql://localhost/jay?allowPublicKeyRetrieval=true&useSSL=false", "han5517", "1234");

		PreparedStatement ps = c.prepareStatement("insert into users (id, name, password) values (?, ?, ?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeUpdate();
		ps.close();
		c.close();
	}

	public User get(String id) throws ClassNotFoundException, SQLException {

		Connection c = connectionMaker.makeConnection();
		// Connection c = simpleConnectionMaker.makeNewConnection();
		// Connection c = getConnection();
		// getConnection() 메소드를 추출해서 중복 제거
		// Class.forName("com.mysql.jdbc.Driver");
		// Connection c = DriverManager.getConnection("jdbc:mysql://localhost/jay?allowPublicKeyRetrieval=true&useSSL=false", "han5517", "1234");

		PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		rs.next();
		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));

		rs.close();
		ps.close();
		c.close();

		return user;
	}
}


