package br.ufpe.cin.eseg.qaservice.model.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.ufpe.cin.eseg.qaservice.model.entities.QAUser;
import br.ufpe.cin.eseg.qaservice.model.repositories.QAUserRepository;

@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private QAUserRepository qaUserRepository;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		QAUser qaUser = qaUserRepository.findByEmail("admin@eseg.cin.ufpe.br");
		
		if (qaUser == null) {
			qaUser = new QAUser();
			qaUser.setUsername("Admin");
			qaUser.setEmail("admin@eseg.cin.ufpe.br");
			qaUser.setPassword(MD5Hash.md5("3s3g#eseg"));
			qaUser.setSuperUser(true);
			qaUserRepository.saveAndFlush(qaUser);
		}
	}
}
