package com.medicine.control;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.ProcessInstance;

import com.medicine.control.handler.CountDownProcessEventListener;

public class ProcessExecution extends JbpmJUnitBaseTestCase{
	@Test
	public void testProcess() throws Exception {
		CountDownProcessEventListener countdown = new CountDownProcessEventListener("timer", 1);
		createRuntimeManager("ProcessBotox.bpmn2");
		RuntimeEngine engine = getRuntimeEngine(null);
		KieSession ksession = engine.getKieSession();
 
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("itsON", true);
        params.put("alertTemp", false);
        ProcessInstance processInstance = ksession.startProcess("medicine.control.ProcessBotox", params);
        countdown.waitTillCompleted();
        assertNodeTriggered(processInstance.getId(),"Desligado ou Removido");
        assertProcessInstanceCompleted(processInstance.getId(), ksession);
 
	}
}
