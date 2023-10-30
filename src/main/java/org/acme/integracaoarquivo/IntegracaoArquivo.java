package org.acme.integracaoarquivo;

import org.apache.camel.builder.RouteBuilder;

public class IntegracaoArquivo extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("file:/tmp/apache_camel/entrada/")
		.routeId("Integracao-Arquivo")
		.to("file:/tmp/apache_camel/saida/");
		
	}

}
