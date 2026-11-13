package xapex.base;

import xapex.base.data.List;

public class Thread<T> {
	
	public static final List<Thread> THREADS = new List<>();
	public static String listAllThreads(){
		StringBuilder strBld = new StringBuilder("\nRUNNING THREADS:");
		for(Thread t : Thread.THREADS){
			if(t.isRunning() && t.started()) strBld.append("\n\t").append(t.toString());
		}
		strBld.append("\nENDED THREADS:");
		for(Thread t : Thread.THREADS){
			if(!t.isRunning() && t.started()) strBld.append("\n\t").append(t.toString());
		}
		strBld.append("\nNEW THREADS:");
		for(Thread t : Thread.THREADS){
			if(!t.isRunning() && !t.started()) strBld.append("\n\t").append(t.toString());
		}
		return strBld.toString();
	}
	
	public static final Boolean MILISECONDS = null;
	public static final Boolean SECONDS = false;
	public static final Boolean MINUTES = true;

	public static final int WAIT_TIME_1 = 16;
	public static final int WAIT_TIME_2 = 16;
	public static final int WAIT_TIME_3 = 16;
	public static final int WAIT_TIME_4 = 16;

    public static interface Waiter{
        boolean waitFor();
    }
    public static void waitFor(Waiter waitFor){
		int reps = 0;
        while(!waitFor.waitFor()) {
            try {
				if(reps < Thread.WAIT_TIME_1)
	                java.lang.Thread.sleep(Thread.WAIT_TIME_1);
	            else if(reps < Thread.WAIT_TIME_2)
	                java.lang.Thread.sleep(Thread.WAIT_TIME_2);
	            else if(reps < Thread.WAIT_TIME_3)
	                java.lang.Thread.sleep(Thread.WAIT_TIME_3);
	            else java.lang.Thread.sleep(Thread.WAIT_TIME_4);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            reps++;
        }
    }
    public static void waitFor(int time, Boolean unit){
		try{
			if(unit == null) java.lang.Thread.sleep(time);
			else if(unit == false) java.lang.Thread.sleep(time * 1000);
			else if(unit == true) java.lang.Thread.sleep(time * 60000);
		}
		catch(InterruptedException ie){throw new RuntimeException(ie);}
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
        this.THREAD = new java.lang.Thread((Runnable)this.object);
        Thread.THREADS.add(this);}
        
    public String toString(){
		if(this.name != null) return this.name;
		else return ("STATUS: "
		+ ((this.started() && this.isRunning()) ?
			"RUN":
			(this.started() && !this.isRunning()) ?
				"END" : "NEW")
		+ " Thread running the: "
		+ this.object.toString());
	}

}
