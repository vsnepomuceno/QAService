package br.ufpe.cin.eseg.qaservice.model.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.ContextLoader;

import br.ufpe.cin.eseg.qaservice.model.repositories.util.PropertyPlaceholderConfig;

@Configuration
@Import(PropertyPlaceholderConfig.class)
@ComponentScan({ "br.ufpe.cin.eseg.qaservice" })
public class AppContext {
	
	private static AppContext getAppContextBean() {
		ApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
		AppContext appc = ac.getBean(AppContext.class);
		return appc;
	}
}
