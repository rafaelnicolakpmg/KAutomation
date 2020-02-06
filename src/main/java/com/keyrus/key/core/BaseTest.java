package com.keyrus.key.core;

import org.junit.After;

import java.io.IOException;

import static com.keyrus.key.core.DriverFactory.killDriver;

public class BaseTest {
	
	@After
	public void finaliza() throws IOException{
		if(Propriedades.FECHAR_BROWSER) {
			killDriver();
		}
	}

}
