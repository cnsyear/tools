package redis;

import org.junit.Test;

/**
 * Jedis测试
 * @author jiebaobao
 *
 */
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class TestRedis {
	/**
	 * 测试连接
	 */
	@Test
	public void testConn() {

		// 获取jedis对象 设置ip和端口号
		Jedis jedis = new Jedis("192.168.2.102", 6379);
		System.out.println("连接成功。。。");
		jedis.set("test", "Hello Jedis!");
		String test = jedis.get("test");
		System.out.println(test);

	}
	
	
	/**
	 * 测试连接池
	 */
	@Test
	public void testPool() {
		// 获取连接池的配置对象
		JedisPoolConfig conf = new JedisPoolConfig();
		// 设置最大连接数
		conf.setMaxTotal(100);
		// 设置最大空闲连接数
		conf.setMaxIdle(10);
		JedisPool jedisPool = new JedisPool(conf, "192.168.2.102", 6379);
		//获取核心对象
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set("test1", "Hello JedisPool!");
			
			String str = jedis.get("test1");
			System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(jedis!=null) {
				jedis.close();
			}
		}
		
	}
}
