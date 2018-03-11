package com.cnsyear;

import java.util.Iterator;

/**
 * 模拟简单的CAS算法
 * @author jiebaobao
 *
 */
public class TestCas {
	public static void main(String[] args) {
		CompareAndSwap compareAndSwap = new  CompareAndSwap();
		
		for (int i = 0; i < 10;i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					int expectedValue = compareAndSwap.getValue();
					//模拟设置方法
					boolean flag = compareAndSwap.compareAndSet(expectedValue, (int)(Math.random()*100));
					System.out.println(flag);
				}
			}).start();
		}
	}
}

class CompareAndSwap{
	
	private int value;//初始值
	
	/**
	 * 获取内存值
	 * @return
	 */
	public synchronized int getValue() {
		return this.value;
	}
	
	/**
	 * 比较值
	 * @param expectedValue 期望值
	 * @param newValue 新值
	 * @return
	 */
	public synchronized int compareAndSwap(int expectedValue,int newValue) {
		int oldValue = this.value;//获取一下内存值
		if(oldValue == expectedValue) {//如果内存值和期望值相等，执行修改
			this.value = newValue;
		}
		return oldValue;		
	}
	
	/**
	 * 设置值
	 * @param expectedValue
	 * @param newValue
	 * @return
	 */
	public boolean compareAndSet(int expectedValue,int newValue) {
		return expectedValue == compareAndSwap(expectedValue, newValue);
	}
	
}
