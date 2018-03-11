package day02;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * i++ 原子性的问题:
 *
 * 
 * @author jiebaobao
 *
 */
public class TestAtomicDemo2 {
	public static void main(String[] args) {
		MyThread2 td = new MyThread2();
		for (int i = 0; i < 10; i++) {
			new Thread(td).start();
		}
	}
}

class MyThread2 implements Runnable {

	private AtomicInteger num = new AtomicInteger(0);
	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(getNum() + "============== \t" + Thread.currentThread().getName());
	}

	/**
	 * 做++操作
	 * 
	 * @return
	 */
	public int getNum() {
		return num.getAndIncrement();
	}

}
