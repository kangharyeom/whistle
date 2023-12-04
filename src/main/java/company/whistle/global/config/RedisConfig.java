package company.whistle.global.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    /*
    * For AWS server
    */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisSentinelConfiguration config = new RedisSentinelConfiguration()
                .master("mymaster")
                .sentinel(host, 26379)
                .sentinel(host, 26380)
                .sentinel(host, 26381);
        config.setPassword(password);

        return new LettuceConnectionFactory(config);
    }

    /*
     * For Local
     */

//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory(host, port);
//    }
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//
//        return redisTemplate;
//    }
}