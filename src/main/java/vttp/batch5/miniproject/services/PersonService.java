package vttp.batch5.miniproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.batch5.miniproject.constant.Constant;
import vttp.batch5.miniproject.models.NewPerson;
import vttp.batch5.miniproject.models.User;
import vttp.batch5.miniproject.repositories.PersonRepo;

@Service
public class PersonService {
    @Autowired 
    PersonRepo personRepo;

    public Boolean userExists(String username){
        return personRepo.userExists(Constant.keyUser,username);

    }

    public Boolean passwordMatch(NewPerson np){
        return (np.getPassword().equals(np.getPassword2()));
    }

    public void createNewAcc(NewPerson np){
        String account = np.toString();
        personRepo.createNewAcc(Constant.keyUser, np.getUsername(), account);
    }

    public Boolean verifyPassword(User user){
        String details = personRepo.getUserDetails(Constant.keyUser, user.getUsername()).toString();
        String[] accDetails = details.split(",");
        String userPassword = accDetails[2];

        return (user.getPassword()).equals(userPassword);

    }
    
}
