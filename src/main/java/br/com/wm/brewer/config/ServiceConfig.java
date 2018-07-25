package br.com.wm.brewer.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.wm.brewer.service.CadastroCervejaService;
import br.com.wm.brewer.storage.FotoStorage;

@Configuration
@ComponentScan(basePackageClasses = {CadastroCervejaService.class, FotoStorage.class})
public class ServiceConfig {
	
	

}
