package vttp.batch5.miniproject.repositories;

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

   

  
   

   

    
}