package com.cnsyear;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException; 
import java.util.concurrent.FutureTask;

/**
 * 实现线程的第三种方法 -- 使用Callable接口创建多线程
 * 
 * @author jiebaobao
 *
 */
public class TestCallable {
	public static void main(String[] args) {
		MyCallable myCallable = new MyCallable();
		//1.执行Callable，需要FutureTask实现类的支持，用于接收运算结果。
		FutureTask<Integer> future = new FutureTask<>(myCallable);
		
		new Thread(future).start();
		
		try {
			System.out.println("-----------在没有执行完线程的时候，主线程在等待。------------");
			int sum = future.get();//获取值
		
			System.out.println("总和："+sum);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

/**
 * 自定义实现Callable线程类 又返回值 能抛异常
 * 
 * @author jiebaobao
 *
 */
class MyCallable implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		int sum = 0;
Thread.sleep(5000);
		for (int i = 0; i < 100000; i++) {
			sum += i;
		}
		System.out.println(Thread.currentThread().getName() + "-----计算完成！");
		return sum;
	}
}