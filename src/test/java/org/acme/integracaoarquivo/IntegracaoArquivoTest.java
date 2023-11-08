package org.acme.integracaoarquivo;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.camel.main.Main;
import org.apache.camel.test.main.junit5.CamelMainTest;
import org.junit.jupiter.api.Test;

@CamelMainTest(
		
		mainClass = Main.class,
		replaceRouteFromWith = {"Integracao-Arquivo=direct:inicio"} //redireciona a rota de integracao-arquivo para o componente inicio
		
		)



class IntegracaoArquivoTest {

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
