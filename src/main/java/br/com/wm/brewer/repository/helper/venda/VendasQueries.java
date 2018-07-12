package br.com.wm.brewer.repository.helper.venda;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.wm.brewer.dto.PeriodoRelatorio;
import br.com.wm.brewer.dto.VendaMes;
import br.com.wm.brewer.dto.VendaOrigem;
import br.com.wm.brewer.model.Venda;
import br.com.wm.brewer.repository.filter.VendaFilter;

public interface VendasQueries {

	public Page<Venda> filtrar(VendaFilter filtro, Pageable pageable);
	
	public Venda buscarComItens(Long codigo);
	
	public BigDecimal valorTotalNoAno();
	
	public BigDecimal valorTotalNoMes();
	
	public BigDecimal valorTicketMedioNoAno();
	
	public List<VendaMes> totalPorMes();
	
	public List<VendaOrigem> totalPorOrigem();
	
	public List<Map<String, Object>> vendasEmitidas(PeriodoRelatorio periodoRelatorio);
	
}
