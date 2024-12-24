package vttp.batch5.miniproject.repositories;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PersonRepo {
    
    @Autowired
    @Qualifier("template01")
    RedisTemplate<String, String> redisTemplate;

    public void createNewAcc (String redisKey, String hashKey, String hashValue){
        redisTemplate.opsForHash().put(redisKey, hashKey, hashValue);
    }

    public Object getUserDetails(String redisKey, String hashKey){
        return redisTemplate.opsForHash().get(redisKey,hashKey);
    }

    public Long delete(String redisKey, String hashKey) {
        return redisTemplate.opsForHash().delete(redisKey,hashKey);
    }

    public Boolean userExists(String redisKey, String hashKey){
        return redisTemplate.opsForHash().hasKey(redisKey,hashKey);
    }

    public Map<Object, Object> getEntries(String redisKey){
        return redisTemplate.opsForHash().entries(redisKey);
        
    }

    public Set<Object> getKeys (String redisKey){
        return redisTemplate.opsForHash().keys(redisKey);
    }

    public List<Object> getValues(String redisKey){
        return redisTemplate.opsForHash().values(redisKey);
    }

    public long size(String redisKey){
        return redisTemplate.opsForHash().size(redisKey);

    }

    public void expire(String redisKey, long expireValue){

        Duration expireDuration = Duration.ofSeconds(expireValue);
        redisTemplate.expire(redisKey, expireDuration);
    }
}