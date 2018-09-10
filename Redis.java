import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public enum Redis {
    /* Singleton Redis class based on Jedis. */
    INSTANCE;

    private final JedisPool pool = new JedisPool(new JedisPoolConfig(), "<redis server>");

    final Logger logger = LoggerFactory.getLogger(Redis.class);

    public void putValue(String key, String value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.set(key, value);
        }
    }

    public String getValue(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.get(key);
        }
    }

    public long rPush(String list, String value) {
        long res = -1;
        try (Jedis jedis = pool.getResource()){
            res = jedis.rpush(list, value);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return res;
    }

    public void closeDb() {
        pool.destroy();
    }

    public static void main(String[] args) {
        Redis.INSTANCE.putValue("aa", "abcd");
        System.out.println(Redis.INSTANCE.getValue("aa"));
        Redis.INSTANCE.rPush("list", "kumar");
    }
}
