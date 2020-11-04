package user.admin.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import user.admin.ws.security.AppProperties;

// application extend SpringBootServlet initializer is added to have a standalone apache tomcat server
@SpringBootApplication
public class UserAdminApplication {

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(UserAdminApplication.class);
//	}
	
	public static void main(String[] args) {
		SpringApplication.run(UserAdminApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder () {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}
	
	@Bean(name="AppProperties")
	public AppProperties getAppProperties() {
		return new AppProperties();
	}

}