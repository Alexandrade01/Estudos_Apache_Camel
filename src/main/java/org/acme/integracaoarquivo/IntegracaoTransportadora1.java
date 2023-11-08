package org.acme.integracaoarquivo;

import org.apache.camel.builder.RouteBuilder;

public class IntegracaoTransportadora1 extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
//		//Para tratar qualquer tipo de erro, podemos combinar errorHandler e onException
//		errorHandler(deadLetterChannel("file:{{diretorioTransportadora1Erro}}").useOriginalMessage().maximumRedeliveries(2));
//		
//		//2 alternativa - Para erros especificos onException
////		onException(GenericFileOperationFailedException.class)
////				.useOriginalMessage()
////				.handled(true)
////				.maximumRedeliveries(5)
////				.redeliveryDelay(2000)
////				.to("file:{{diretorioTransportadora1Erro}}");

		from("direct:integracaoTransportadora1")
			.routeId("integracao-arquivo-transportadora1")
			.log("Integrando com a transportadora 1")
			.errorHandler(noErrorHandler())
			.setBody(constant("Um teste"))
			.to("file:{{diretorioTransportadora1}}?fileName=${date:now:HHmmss}_${file:name}");

	}

}
