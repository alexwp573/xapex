package xapex.base;

public class Thread<T> {

    public static interface Waiter{
        boolean waitFor();
    }
    public static void waitFor(Waiter waitFor){
        while(waitFor.waitFor()) {
            try {
                java.lang.Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String name = null;

    public String name(){return this.name;}
        public void name(String name){this.name = name;}
    private final Id id = new Id("ID");
        public Id id(){return this.id;}

    private final T object;
        public T object(){return this.object;}
    private final java.lang.Thread THREAD;
        public java.lang.Thread thread(){return this.THREAD;}

    public void start(){this.THREAD.start();}
    public boolean started(){return this.THREAD.getState() != java.lang.Thread.State.NEW;}
    public boolean isRunning(){
        return !(this.THREAD.getState() == java.lang.Thread.State.NEW || this.THREAD.getState() == java.lang.Thread.State.TERMINATED || java.lang.Thread.interrupted());
    }
    public void kill(){this.THREAD.interrupt();}

    public Thread(T object){
        this.object = object;
        this.THREAD = new java.lang.Thread((Runnable)this.object);}

}
