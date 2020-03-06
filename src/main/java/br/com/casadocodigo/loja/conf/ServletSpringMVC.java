package br.com.casadocodigo.loja.conf;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/*Classe voltada para configurações básicas
 * Por exemplo dizer o SPRING irá atender a partir do path / para frente
 * localhost:8080/casadocodigo/SPRING ATENDE AQUI.
 * */

public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer {
	// Herdamos está classe, pois nela contém as configurações do SPRING.

	@Override
	protected Class<?>[] getRootConfigClasses() {

		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// Deve retornar qual a classe de configuração que vamos usar..

		return new Class[] {AppWebConfiguration.class, JPAConfiguration.class};
		
	}

	@Override
	protected String[] getServletMappings() {
		// Mapeia o servlet spring

		return new String[] {"/"};
		// Retorna a URL que deseja mapear.(do / para frente)
	}
	
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		return new Filter[] {encodingFilter};
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement(""));
		// setamos a configuração do arquivo, colocamos apenas appas, pois não queremos nenhuma configuração, apenas
		// o arquivo puro do jeito que ele está.
		
	}

}
