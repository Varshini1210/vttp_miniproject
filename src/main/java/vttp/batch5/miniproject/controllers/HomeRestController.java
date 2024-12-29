package vttp.batch5.miniproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import vttp.batch5.miniproject.services.PersonService;

@RestController
@RequestMapping
public class HomeRestController {

    @Autowired
    PersonService personService;
    

    @GetMapping ("/{username}/wishlist")
    public ResponseEntity<String> getUserWishList(@PathVariable(name="username",required=true) String username){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE,"application/json");
        Boolean userExists = personService.userExists(username);
        if(userExists == true){
            
            
            JsonArray wishlist = personService.jsonWishList(username);

            JsonObject response = Json.createObjectBuilder()
                                    .add("user:",username)
                                    .add("wishlist", wishlist)
                                    .build();
                                    String responseString = response.toString();
                                    return new ResponseEntity<>(responseString,headers,HttpStatus.OK);
        }
        else{
            JsonObject response = Json.createObjectBuilder()
                                        .add("message","User "+ username + " does not exist" )
                                        .build();
            String responseString = response.toString();
            return new ResponseEntity<>(responseString,headers,HttpStatus.NOT_FOUND);
        }
    }
    
}
