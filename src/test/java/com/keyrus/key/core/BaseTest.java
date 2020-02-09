package com.keyrus.key.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

import java.io.IOException;

import static com.keyrus.key.core.DriverFactory.killDriver;

public class BaseTest {

	protected ExecutionManager executionManager;

	@Rule
	public TestName name = new TestName();

	@Before
	public void BaseTestBefore(){
		executionManager = new ExecutionManager();
		String methodName = name.getMethodName();
		executionManager.setRunProperties(getClass().getPackageName().split("[.]") [4], methodName);
		executionManager.startExecution();
	}

	@After
	public void finaliza() throws IOException{
		if(Propriedades.FECHAR_BROWSER) {
			killDriver();
		}
	}

}
