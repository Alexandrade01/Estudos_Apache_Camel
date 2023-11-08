package org.acme.integracaoarquivo;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFileOperationFailedException;
import org.apache.camel.support.builder.Namespaces;

public class IntegracaoArquivo extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		var ns = new Namespaces("ns","http://www.portalfiscal.inf.br/nfe");
		
		//Para tratar qualquer tipo de erro, podemos combinar errorHandler e onException
		errorHandler(deadLetterChannel("file:{{diretorioTransportadora1Erro}}"));
		
		//2 alternativa - Para erros especificos onException
		onException(GenericFileOperationFailedException.class)
				.useOriginalMessage()
				.handled(true)
				.maximumRedeliveries(5)
				.redeliveryDelay(2000);
		
		from("file:{{diretorioEntrada}}?delay=5000")
		.routeId("Integracao-Arquivo")
		.log("Processando o arquivo: ${file:onlyname}")
		.setProperty("CNPJ",xpath("{{xpathCnpjTransportadora}}", ns))
		.choice()
		.when(exchangeProperty("CNPJ").isEqualTo("1")).to("direct:integracaoTransportadora1")
		
		.when(exchangeProperty("CNPJ").isEqualTo("2")).to("direct:integracaoTransportadora")
		
		.otherwise()
		.log("Transportadora não integrada ! ")
		.endChoice()
		.end()

		.log("Processamento do arquivo ${file:name} foi concluido");
	
	}

}
