package com.cnsyear.sevice;

import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.cnsyear.bean.Student;

/**
 * 测试cxf支持的类型
 * @author jiebaobao
 *
 */
@WebService
public interface CxfDataType {
	
	
	@WebMethod
	public boolean addStudent(Student s);

	@WebMethod
	public Student getStudentById(int id);

	@WebMethod
	public List<Student> getStudentsByPrice(float price);

	@WebMethod
	public Map<Integer, Student> getAllStudentsMap();
	
}
