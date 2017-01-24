package spring_jdbc.obs.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings({ "unused" })
public class MyObsImpl {
	public static ObsServiceBean obsService = 
			(ObsServiceBean) new ClassPathXmlApplicationContext("Beans.xml").getBean("ObsServiceBean");
}
