package com.gdhdcy.service;

import java.util.List;

import com.gdhdcy.bean.Person;

public interface PersonService {
	public void save(Person person);//保存用户进入数据库
	public void update(Person person);//更新
	public Person getPerson(Integer personid); //查询
	public List<Person> getPersons();
	public void delete(Integer personid);
}
