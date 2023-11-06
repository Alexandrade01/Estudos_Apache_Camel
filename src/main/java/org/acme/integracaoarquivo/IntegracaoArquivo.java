package org.acme.integracaoarquivo;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.FileConstants;
import org.apache.camel.component.http.HttpConstants;

public class IntegracaoArquivo extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("file:{{diretorioEntrada}}?delay=5000")
		.routeId("Integracao-Arquivo")
		.log("Processando o arquivo: ${file:onlyname}")
		.choice()
		.when(xpath("{{xpathCnpjTransportadora}}").isEqualTo("1"))
		.to("file:{{diretorioTransportadora1}}?fileName=${date:now:HHmmss}_${file:name}")
		.when(xpath("{{xpathCnpjTransportadora}}").isEqualTo("2"))
		.setHeader(HttpConstants.HTTP_METHOD, constant("POST"))
		.setHeader(HttpConstants.HTTP_URI, constant("{{urlApiTransportadora2}}"))
		.setHeader(HttpConstants.HTTP_PATH, constant("nfes"))
		.setHeader(HttpConstants.CONTENT_TYPE, constant("application/xml"))
		.to("http:servidorTransportadora2")
		.otherwise()
		.log("Transportadora não integrada ! ")
		.end();
	}

}
