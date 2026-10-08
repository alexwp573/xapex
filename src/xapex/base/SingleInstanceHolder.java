package xapex.base;

public class SingleInstanceHolder<T> {

    public static class InstanceAlreadyExistsException extends RuntimeException{
        public final String INSTANCE_CLASS_NAME;
        public InstanceAlreadyExistsException(String name){this.INSTANCE_CLASS_NAME = name;}
        public String toString(){return "Instance of class: " + this.INSTANCE_CLASS_NAME + " - already exists";}
    }

    private T INSTANCE = null;
        public T instance(){return this.INSTANCE;}
    public void register(T instance) throws InstanceAlreadyExistsException {
        if(this.INSTANCE == null) this.INSTANCE = instance;
        else throw new InstanceAlreadyExistsException(instance.getClass().getName());
    }
}
