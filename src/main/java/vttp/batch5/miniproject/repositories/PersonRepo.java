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

    public void createNewAcc (String username, String accountDetails){
        redisTemplate.opsForHash().put(username, "account", accountDetails);
    }

    public String getUserDetails(String username){
        return redisTemplate.opsForHash().get(username,"account").toString();
    }

    public Boolean userExists(String username){
        return redisTemplate.opsForHash().hasKey(username,"account");
    }

    public Boolean wishListExists(String username){
        return redisTemplate.opsForHash().hasKey(username,"wishlist");
    }

    public void addToWishList (String username, String wishlist){
        redisTemplate.opsForHash().put(username, "wishlist", wishlist);
    }

    public String getWishList(String username){
        return redisTemplate.opsForHash().get(username,"wishlist").toString();
    }

    public Long deleteWishList(String username) {
        return redisTemplate.opsForHash().delete(username,"wishlist");
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