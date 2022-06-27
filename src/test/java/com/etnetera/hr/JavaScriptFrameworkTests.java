package com.etnetera.hr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.etnetera.hr.controller.JavaScriptFrameworkController;
import com.etnetera.hr.data.JavaScriptFramework;

/**
 * Class used for Spring Boot/MVC based tests.
 * 
 * @author Etnetera
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaScriptFrameworkTests {

	@Autowired
	private JavaScriptFrameworkController controller;

	@Test
	public void contextLoads() throws Exception {
		assertNotNull(controller);
	}

	/**
	 * Get count of items.
	 * 
	 * @return
	 */
	private int iterableSize(Iterable<?> frameworks) {
		int counter = 0;
		Iterator<?> iter = frameworks.iterator(); 
		for (; iter.hasNext(); counter++) {
			iter.next();
		}
		return counter;
	}

	@Test
	public void crudTest() throws Exception {
		Calendar cal = Calendar.getInstance();
		JavaScriptFramework jsfAngular = new JavaScriptFramework("Angular", "1.0", null, 10);

		cal.set(2018, Calendar.JULY, 23);
		JavaScriptFramework jsfReact = new JavaScriptFramework("React", "2.0", cal.getTime(), 20);

		cal.set(2019, Calendar.JUNE, 24);
		JavaScriptFramework jsfNodejs = new JavaScriptFramework("Node.js", null, cal.getTime(), 30);

		cal.set(2020, Calendar.JUNE, 25);
		JavaScriptFramework jsfAngular2 = new JavaScriptFramework("Angular", "2.0", cal.getTime(), 40);

		assertNotNull(controller.frameworks());

		for (JavaScriptFramework jsf : controller.frameworks()) {
			fail("List of frameworks should be empty. " + jsf);
		}

		// save frameworks
		JavaScriptFramework jsfAngularTest = controller.save(jsfAngular);

		assertEquals(jsfAngular.getName(), jsfAngularTest.getName());
		assertEquals(jsfAngular.getVersion(), jsfAngularTest.getVersion());
		assertEquals(jsfAngular.getDeprecationDate(), jsfAngularTest.getDeprecationDate());
		assertEquals(jsfAngular.getHypeLevel(), jsfAngularTest.getHypeLevel());

		controller.save(jsfReact);
		controller.save(jsfNodejs);
		controller.save(jsfAngular2);
		assertEquals(4, controller.count());

		// delete on framework
		controller.delete(jsfNodejs.getId());
		assertEquals(3, iterableSize(controller.frameworks()));

		// update and check returned framework
		JavaScriptFramework jsfAngularUpdate = new JavaScriptFramework("Angular", "1.1", null, 50);
		JavaScriptFramework jsfAngularUpdateTest = controller.update(jsfAngularTest.getId(), jsfAngularUpdate);

		assertEquals(jsfAngularUpdate.getName(), jsfAngularUpdateTest.getName());
		assertEquals(jsfAngularUpdate.getVersion(), jsfAngularUpdateTest.getVersion());
		assertEquals(jsfAngularUpdate.getDeprecationDate(), jsfAngularUpdateTest.getDeprecationDate());
		assertEquals(jsfAngularUpdate.getHypeLevel(), jsfAngularUpdateTest.getHypeLevel());

		// get Angular framework with ID
		jsfAngularUpdateTest = controller.get(jsfAngularTest.getId());

		assertEquals(jsfAngularUpdate.getName(), jsfAngularUpdateTest.getName());
		assertEquals(jsfAngularUpdate.getVersion(), jsfAngularUpdateTest.getVersion());
		assertEquals(jsfAngularUpdate.getDeprecationDate(), jsfAngularUpdateTest.getDeprecationDate());
		assertEquals(jsfAngularUpdate.getHypeLevel(), jsfAngularUpdateTest.getHypeLevel());

		// search
		Iterable<JavaScriptFramework> frameworks = controller.search(jsfAngular.getName());
		assertEquals(2, iterableSize(frameworks));
		for (JavaScriptFramework jsf : frameworks) {
			assertEquals(jsfAngular.getName(), jsf.getName());
		}
		
		// delete all
		controller.delete();
		assertEquals(0, controller.count());
	}

}
