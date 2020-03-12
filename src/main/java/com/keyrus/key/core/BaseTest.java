package com.keyrus.key.core;

import org.junit.*;
import org.junit.rules.TestName;

import java.io.IOException;

public class BaseTest {

	protected ExecutionManager executionManager;
	protected DataManager dataManager;

	@Rule
	public TestName name = new TestName();

	@Before
	public void BaseTestBefore(){
		String scenario = getClass().getSimpleName();
		String testCase = name.getMethodName();
		executionManager = new ExecutionManager(scenario, testCase);
		dataManager = executionManager.getDataManager();
	}

	@After
	public void finaliza() throws IOException{
		this.executionManager.finishExecution();
	}

}
