package br.com.wm.brewer.service;

import java.io.InputStream;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wm.brewer.common.util.MultiMap;
import br.com.wm.brewer.common.util.MultiMapDataSet;
import br.com.wm.brewer.dto.PeriodoRelatorio;
import br.com.wm.brewer.repository.Vendas;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class RelatorioService {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private Vendas vendas;
	
	public byte[] gerarRelatorioVendasEmitidas2(PeriodoRelatorio periodoRelatorio) throws Exception {
		Date dataInicio = Date.from(LocalDateTime.of(periodoRelatorio.getDataInicio(), LocalTime.of(0, 0, 0))
				.atZone(ZoneId.systemDefault()).toInstant());
		Date dataFim = Date.from(LocalDateTime.of(periodoRelatorio.getDataFim(), LocalTime.of(23, 59, 59))
				.atZone(ZoneId.systemDefault()).toInstant());
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("data_inicio", dataInicio);
		parametros.put("data_fim", dataFim);
		
		InputStream inputStream = this.getClass()
				.getResourceAsStream("/relatorios/relatorio_vendas_emitidas.jasper");
		
		Connection con = this.dataSource.getConnection();
		
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, con);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} finally {
			con.close();
		}
	}
	
	public byte[] gerarRelatorioVendasEmitidas3(PeriodoRelatorio periodoRelatorio) throws Exception {		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		
		InputStream inputStream = this.getClass()
				.getResourceAsStream("/relatorios/relatorio_vendas_emitidas.jasper");
		
		List<Map<String, Object>> list = vendas.vendasEmitidas(periodoRelatorio);
		
		MultiMap map = new MultiMap(list);
		
		MultiMapDataSet ds = new MultiMapDataSet(map);
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, ds);
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}

}











