package spring_jdbc.obs.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@SuppressWarnings({ "unused" })
public class MyStaImpl {
	public static StaServiceBean staService = 
			(StaServiceBean) new ClassPathXmlApplicationContext("Beans.xml").getBean("StaServiceBean");
}
