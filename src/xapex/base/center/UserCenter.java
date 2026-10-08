package xapex.base.center;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import xapex.base.Center;
import xapex.base.Status;
import xapex.base.center.environment.ConfigurationManager;
import xapex.base.center.user.User;
import xapex.base.data.List;

import java.util.NoSuchElementException;

public class UserCenter extends Center {

    private final List<User> USERS = new List<>();
        public void addUser(User user) throws ConfigurationManager.NameOccupiedException {
            for(User u : this.USERS){
                if(((String)(u.property("username").value())).compareTo((String)user.property("username").value()) == 0)
                    throw new ConfigurationManager.NameOccupiedException((String)user.property("username").value());
            }
            this.USERS.add(user);
        }
        public void removeUser(String username){
            try{this.removeUser(this.user(username));}
            catch(NoSuchElementException noSuchElementException){;}
        }
        public void removeUser(User user){
            this.USERS.get(user);
        }
        public User user(String username) throws NoSuchElementException{
            for(User u : this.USERS){
                if(((String)(u.property("username").value())).compareTo(username) == 0){
                    return u;
                }
            }
            throw new NoSuchElementException(username);
        }

    private User USER;
        public User user(){return this.USER;}

    public void authenticate(User user, char[] password) throws User.UnsuccessfullLoginAttemptsLimitReachedException, User.LoginAuthorized, User.LoginDenied {
        if(user.unsuccessfullLogins() >= 2) throw new User.UnsuccessfullLoginAttemptsLimitReachedException();
        Argon2 argon = Argon2Factory.create();
        // connect to server to get password
        String hashedPassword = "test";
        try{
            boolean isAuthenticated = argon.verify(hashedPassword, password);
            if(!isAuthenticated){
                user.incrementUnsuccessfullLogins();
                throw new User.LoginDenied();}
            else{
                throw new User.LoginAuthorized();
            }
        } finally {
            argon.wipeArray(password);
        }
    }

    public Status configure(){
        super.configure();



        return super.configure();
    }

    public Status personalize(){
        super.personalize();



        return super.personalize();
    }

    public void run(){

    }

    public Status save(){
        super.save();



        return super.save();
    }

    public Status close(){
        super.close();



        return super.close();
    }
}
