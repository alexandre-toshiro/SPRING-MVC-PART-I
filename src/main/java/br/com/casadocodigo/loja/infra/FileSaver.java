package br.com.casadocodigo.loja.infra;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component // componente genérico do spring.
public class FileSaver {
	// Irá salvar o arquivo.
	
	@Autowired // injeta o request aqui.
	private HttpServletRequest request;
	
	
	
	
	public String write(String baseFolder, MultipartFile file) {// recebe a pasta base e o próprio arquivo em si.
		// método que irá retornar onde estará salvo o arquivo.
		try {
			String realPath = request.getServletContext().getRealPath("/" + baseFolder);
			String path = realPath + "/" + file.getOriginalFilename();
			file.transferTo(new File(path));
			return baseFolder + "/" +  file.getOriginalFilename();
			// dessa forma não retorna o caminho COMPLETO do file, mas somente o caminho relaivo
			//(que é mais curto e mais conciso).
			
			
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e);
		}
		// Após terminar este método 
	}

}
