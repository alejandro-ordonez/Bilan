package org.bilan.co;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.entities.Roles;
import org.bilan.co.domain.entities.UserInfo;
import org.bilan.co.domain.enums.DocumentType;
import org.bilan.co.infraestructure.persistance.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
public class BilanBackendApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BilanBackendApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(BilanBackendApplication.class, args);
	}

	@Autowired
	private UserInfoRepository userInfoRepository;

	@EventListener
	public void seedAdmin(ContextRefreshedEvent evet){
		if(userInfoRepository.existsById("1024506030"))
			return;

		UserInfo userInfo = new UserInfo();
		userInfo.setDocument("1024506030");
		userInfo.setName("David");
		userInfo.setLastName("Avendaño");
		userInfo.setPositionName("MEN");
		userInfo.setPassword("$2a$12$OSEerOKttf9biSyWnTbXYO/juup8Ax2l17ACO4n3A7cEdW5OMP0ai");
		userInfo.setDocumentType(DocumentType.CC);

		Roles role = new Roles();
		role.setId(5);

		userInfo.setRole(role);
		userInfo.setIsEnabled(true);
		userInfo.setConfirmed(false);

		userInfoRepository.save(userInfo);
	}
}
