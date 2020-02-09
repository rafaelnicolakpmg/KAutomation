package com.keyrus.key.core;

import org.junit.After;
import org.junit.Before;

import java.io.IOException;

import static com.keyrus.key.core.DriverFactory.killDriver;

public class BaseTest {

	protected ExecutionManager executionManager;

	@Before
	public void BaseTestBefore(){
		executionManager = new ExecutionManager();
	}

	@After
	public void finaliza() throws IOException{
		if(Propriedades.FECHAR_BROWSER) {
			killDriver();
		}
	}

}
