package br.com.wm.brewer.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.wm.brewer.service.CadastroCervejaService;

@Configuration
@ComponentScan(basePackageClasses = CadastroCervejaService.class)
public class ServiceConfig {

}
