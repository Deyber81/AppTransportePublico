package AppTansporte.AppTransportepublico;


import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.mariadb.jdbc.MariaDbPoolDataSource;

@Configuration
public class MariaDbConfig {
	@Bean
	DataSource dataSource() {
		MariaDbPoolDataSource dataSource = new MariaDbPoolDataSource();

		try {
			dataSource.setUrl("jdbc:mariadb://localhost:3306/AppTransportPublic");
			dataSource.setUser("root");
			dataSource.setPassword("123");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dataSource;
	}
}