package day02;

/**
 * i++ 原子性的问题:
 *
 * 
 * @author jiebaobao
 *
 */
public class TestAtomicDemo {
	public static void main(String[] args) {
		MyThread td = new MyThread();
		for (int i = 0; i < 10; i++) {
			new Thread(td).start();
		}
	}
}

class MyThread implements Runnable {

	private int num = 0;
//	private volatile int num = 0; //使用volatile也白费

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
		return num++;
	}

}
