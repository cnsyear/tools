package com.cnsyear.sevice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import com.cnsyear.bean.Student;

/**
 * 测试cxf类型的实现类
 * @author jiebaobao
 *
 */
@WebService
public class CxfDataTypeImpl implements CxfDataType{

	@Override
	public boolean addStudent(Student s) {
		System.out.println("addStudent:"+s);
		return false;
	}

	@Override
	public Student getStudentById(int id) {
		System.out.println("getStudentById:"+id);
		return new Student();
	}

	@Override
	public List<Student> getStudentsByPrice(float price) {
		System.out.println("getStudentsByPrice:"+price);
		List<Student> list = new ArrayList<>();
		list.add(new Student());
		list.add(new Student());
		list.add(new Student());
		list.add(new Student());
		return list;
	}

	@Override
	public Map<Integer, Student> getAllStudentsMap() {
		System.out.println("getAllStudentsMap---");
		Map<Integer,Student> map = new HashMap<>();
		map.put(111, new Student());
		map.put(222, new Student());
		map.put(333, new Student());
		map.put(444, new Student());
		return map;
	}
	
}
