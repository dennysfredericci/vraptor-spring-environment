package br.com.caelum.vraptor.environment;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.web.context.ServletContextAware;

/**
 * Esta classe representa um {@link PropertyPlaceholderConfigurer} do Sring
 * integrado com o vraptor-environment. Coloque no xml do Spring:
 * 
 * <bean
 * class="br.com.caelum.vraptor.environment.EnvironmentPropertyPlaceholder" />
 * 
 * Com esta expressão abaixo com os valores armazenados no vraptor-environment.
 * 
 * <constructor-arg value="${mongodb.database}" />
 * 
 * @author Dennys Fredericci
 */
public class EnvironmentPropertyPlaceholder extends PropertyPlaceholderConfigurer implements ServletContextAware {

	private Environment environment;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
	 * #resolvePlaceholder(java.lang.String, java.util.Properties)
	 */
	@Override
	protected String resolvePlaceholder(String placeholder, Properties props) {
		String value = super.resolvePlaceholder(placeholder, props);

		if (value == null) {
			value = environment.get(placeholder);
		}
		return value;
	}

	/*
	 * 
	 * @see
	 * org.springframework.web.context.ServletContextAware#setServletContext
	 * (javax.servlet.ServletContext)
	 */
	@Override
	public void setServletContext(ServletContext servletContext) {
		try {
			this.environment = new DefaultEnvironment(servletContext);
		} catch (IOException e) {
			throw new IllegalArgumentException("Error starting vraptor environment plugin", e);
		}
	}

}
