package xapex.base.center.user;

import xapex.base.center.environment.ConfigurationManager;
import xapex.base.data.List;

import java.util.NoSuchElementException;

public final class User {

    private int UNSUCCESSFUL_LOGINS = 0;
        public int unsuccessfullLogins(){return this.UNSUCCESSFUL_LOGINS;}
        public int incrementUnsuccessfullLogins(){return ++this.UNSUCCESSFUL_LOGINS;}

    public static class UnsuccessfullLoginAttemptsLimitReachedException extends Exception{}
    public static class LoginAuthorized extends Exception{}
    public static class LoginDenied extends Exception{}

    public record Property<T>(String label, T value){
        public Class<?> valueClass(){return value.getClass();}}

    public final String USERNAME;
    private final List<Property<?>> PROPERTIES = new List<>();
        public Property<?> property(String label) throws NoSuchElementException {
            for(Property<?> p : this.PROPERTIES){
                if(p.label.compareTo(label) == 0)
                    return p;
            }
            throw new NoSuchElementException(label);
        }
        public Class<?> classOf(String label) throws NoSuchElementException{
            return property(label).valueClass();
        }
        public void addProperty(Property<?> p) throws ConfigurationManager.NameOccupiedException {
            try{this.property(p.label);}
            catch(NoSuchElementException noSuchElementException){
                this.PROPERTIES.add(p);
            }
            throw new ConfigurationManager.NameOccupiedException(p.label);
        }

    public User(String userName){
        this.USERNAME = userName;
    }


}
