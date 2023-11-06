package org.acme.integracaoarquivo;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpConstants;
import org.apache.camel.support.builder.Namespaces;

public class IntegracaoArquivo extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		var ns = new Namespaces("ns","http://www.portalfiscal.inf.br/nfe");
		
		from("file:{{diretorioEntrada}}?delay=5000")
		.routeId("Integracao-Arquivo")
		.log("Processando o arquivo: ${file:onlyname}")
		.setProperty("CNPJ",xpath("{{xpathCnpjTransportadora}}", ns))
		.choice()
		.when(exchangeProperty("CNPJ").isEqualTo("1")).to("direct:integracaoTransportadora1")
		
		.when(exchangeProperty("CNPJ").isEqualTo("2")).to("direct:integracaoTransportadora2")
		.endChoice()
		
		.otherwise()
		.log("Transportadora não integrada ! ")
		.end();
		
		from("direct:integracaoTransportadora1")
			.routeId("integracao-arquivo-transportadora2")
		.to("file:{{diretorioTransportadora1}}?fileName=${date:now:HHmmss}_${file:name}");
		
		from("direct:integracaoTransportadora2")
			.routeId("integracao-arquivo-transportadora1")
			// throttle limita o número de mensagens que podem passar por um ponto específico de uma rota em um determinado período de tempo.
			.throttle(1).timePeriodMillis(5000).asyncDelayed()
				.setHeader(HttpConstants.HTTP_METHOD, constant("POST"))
				.setHeader(HttpConstants.HTTP_URI, constant("{{urlApiTransportadora2}}"))
				.setHeader(HttpConstants.HTTP_PATH, constant("nfes"))
				.setHeader(HttpConstants.CONTENT_TYPE, constant("application/xml"))
		.to("http:servidorTransportadora2");
	}

}
