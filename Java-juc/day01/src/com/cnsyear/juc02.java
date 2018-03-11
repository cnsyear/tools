package com.cnsyear;

/**
 * volatile关键字
 * 
 * 解决方法一：synchronized 强烈不推荐
 * 
 * @author jiebaobao
 *
 */
public class juc02 {

	/**
	 * Main线程
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MyThread2 td = new MyThread2();
		new Thread(td).start();//开启一个子线程执行写的操作
		
		
		while(true) {
			synchronized (td) {//synchronized每次都会去主内存中
				if(td.isFlag()) {
					System.out.println("Main线程执行==="+td.isFlag());
					break;
				}
			}
		}
				
	}

}


/**
 * 定义一个线程
 * 
 * @author jiebaobao
 *
 */
class MyThread2 implements Runnable {
	private boolean flag = false;// 一个变量默认false

	@Override
	public void run() {
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//这里执行写的操作
		flag = true;
		System.out.println("线程一执行==="+flag);
	}
	

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}

