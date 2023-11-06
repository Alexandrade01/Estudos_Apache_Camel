package org.acme.integracaoarquivo;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.FileConstants;

public class IntegracaoArquivo extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("file:{{diretorioEntrada}}?delay=5000")
		.routeId("Integracao-Arquivo")
		.process(exchange -> {
			
			systemProperty(exchange.getMessage().getBody(String.class));
			
		})
		.log("Processando o arquivo: ${file:onlyname}")
		//setando o nome do arquivo como (Hora atual)_(Nome do arquivo)
		.setHeader(FileConstants.FILE_NAME, simple("${date:now:HHmmss}_${file:name}")) // setando nome na entrada
		// .to("file:{{diretorioSaida}}?fileName=${date:now:HHmmss}_${file:name}") //setando nome na saida
		.to("file:{{diretorioSaida}}");
		
	}

}
