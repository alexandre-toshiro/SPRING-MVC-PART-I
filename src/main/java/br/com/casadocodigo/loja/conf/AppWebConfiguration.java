package br.com.casadocodigo.loja.conf;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.casadocodigo.loja.controllers.HomeController;
import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.CarrinhoCompras;

//1)- Precisamos informar so spring onde encontrar o controller
//2) - Informar diretamente uma classeController, pois o Spring ja busca diretamente o pacote dessa classe, 
//onde irá encontrar todos os  controllers.
//3) - Toda vez que for adicionado um pacote novo, colocar a classe aqui para o Spring reconhecer.
// 4) - Instanciando apenas uma classe do pacote, o SPRING já reconhece todas que ali estão.
@EnableWebMvc // Habilita o usa do Spring MVC dentro do projeto.
@ComponentScan(basePackageClasses = { HomeController.class, ProdutoDAO.class, FileSaver.class, CarrinhoCompras.class })
public class AppWebConfiguration extends WebMvcConfigurerAdapter{

	@Bean // Indica que este método é gerenciado pelo Spring, ou seja, o spring irá
			// utilizar para configuração.
	public InternalResourceViewResolver internalResourceViewResolver() {
		// Método que auxília o Spring a encontrar as views.
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();

		resolver.setPrefix("/WEB-INF/views/");// Define o caminho das views.
		resolver.setSuffix(".jsp"); // Adiciona a extensão dos aquivos da views.

		resolver.setExposedContextBeanNames("carrinhoCompras");
		return resolver;// ele retorna a classe Internalresource....
	}

	@Bean
	public MessageSource messageSource() {
		//
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		
		messageSource.setBasename("/WEB-INF/messages");// onde está o arquivo de propriedades.
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1);
		return messageSource;
	}
	
	@Bean
	public FormattingConversionService mvcConversionService() {
		// Conf do formato padrão de data que vamos usar na aplicação.
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();// Conversor default.
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();// Nesse classe iremos informar o formato da data.
		registrar.setFormatter(new DateFormatter("dd/MM/yyyy") );// passando o formato.
		registrar.registerFormatters(conversionService);// Joga o formato dentro do conversor padrão.
		
		return conversionService;
		
		// Fazemos a configuração para não precisar ficar setando em casa classe, o formato de data.
	}
	
	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }
	
	
}
