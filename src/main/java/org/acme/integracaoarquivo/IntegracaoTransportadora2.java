package org.acme.integracaoarquivo;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpConstants;

public class IntegracaoTransportadora2 extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("direct:integracaoTransportadora2")
		.routeId("integracao-arquivo-transportadora2")
				// throttle limita o número de mensagens que podem passar por um ponto específico de uma rota em um determinado período de tempo.
				.throttle(1).timePeriodMillis(5000).asyncDelayed()
				.setHeader(HttpConstants.HTTP_METHOD, constant("POST"))
				.setHeader(HttpConstants.HTTP_URI, constant("{{urlApiTransportadora2}}"))
				.setHeader(HttpConstants.HTTP_PATH, constant("nfes"))
				.setHeader(HttpConstants.CONTENT_TYPE, constant("application/xml"))
		.to("http:servidorTransportadora2");

	}

}
