package Junit.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gdhdcy.bean.Person;
import com.gdhdcy.service.PersonService;
import com.gdhdcy.service.impl.PersonServiceBean;

public class test {

	private static PersonService personService;
	@BeforeClass
	public static void setUpBeforeClass()throws Exception{
		try{
			 ApplicationContext cxt=new ClassPathXmlApplicationContext("beans.xml");
			 System.out.println("1");
			 personService=(PersonService)cxt.getBean("personService");
		}catch(RuntimeException e){
			e.printStackTrace();
		}
	}
	@Test
	public void testSave() {
		for(int i=0; i<5; i++)
			{
				Person p=new Person();
			    p.setName("wang"+i);
				personService.save(p);
			}
		    
	 
	}
	@Test public void getPerson(){
		Person person = personService.getPerson(2);
		System.out.println(person.getName());
	}
	
	@Test public void update(){
		Person person = personService.getPerson(3);
		person.setName("张xx");
		personService.update(person);
	}
	
	@Test public void delete(){
		personService.delete(1);
	}
	@Test public void getBeans(){
		for(Person person : personService.getPersons()){
			System.out.println(person.getName());
		}
	}
	

}
