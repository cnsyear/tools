package com.cnsyear.client;

import java.util.List;

import com.cnsyear.client.GetAllStudentsMapResponse.Return;
import com.cnsyear.client.GetAllStudentsMapResponse.Return.Entry;

/**
 * 
 * 客户端测试
 * @author jiebaobao
 *
 */
public class Mainclient {
	public static void main(String[] args) {
		CxfDataTypeImplService factorty = new CxfDataTypeImplService();
		CxfDataType cxfDataType = factorty.getCxfDataTypeImplPort();
		
		boolean success = cxfDataType.addStudent(new Student());
		System.out.println("client "+success);
		
		Student s =cxfDataType.getStudentById(1);
		System.out.println(s);
		List<Student> list = cxfDataType.getStudentsByPrice(34);
		System.out.println(list);
		
		Return r = cxfDataType.getAllStudentsMap();
		List<Entry> entrys = r.getEntry();
		for(Entry entry : entrys) {
			Integer id = entry.getKey();
			Student student = entry.getValue();
			System.out.println(id+"_"+student);
		}
		
	}
}
