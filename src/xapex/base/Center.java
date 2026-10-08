package xapex.base;

public abstract class Center implements Runnable {

    public static class CenterStatusNotMatchingException extends Exception{
        public final Status EXPECTED_STATUS;
        public final Status ACTUAL_STATUS;
        public CenterStatusNotMatchingException(Status act, Status exp){super(); this.ACTUAL_STATUS = act; this.EXPECTED_STATUS = exp;}
        public String toString(){return "Center status: " + this.ACTUAL_STATUS.NAME + ", Expected: " + this.EXPECTED_STATUS.NAME;}
    }

    protected Status STATUS = Status.INITIALIZED;
        public Status status(){return this.STATUS;}

    protected final Tasker TASKER = new Tasker();
        public void addTask(Tasker.Task task){this.TASKER.addTask(task);}

    public final Thread<Center> THREAD = new Thread<>(this);
        protected boolean RUNNING = false;
        public void stop(){this.RUNNING = false; this.TASKER.stop();}
        public void kill(){this.RUNNING = false; this.TASKER.kill(); this.THREAD.kill();}

    public void run(){

    }

    public Status configure(){
        switch(this.STATUS){
            case INITIALIZED: this.STATUS = Status.CONFIGURING; break;
            case CONFIGURING: this.STATUS = Status.CONFIGURED; break;
        }
        return this.STATUS;
    }
    public Status personalize(){
        switch(this.STATUS){
            case LOGGED: this.STATUS = Status.PERSONALIZING; break;
            case PERSONALIZING: this.STATUS = Status.PERSONALIZED; break;
        }
        return this.STATUS;
    }
    public void start() throws CenterStatusNotMatchingException {
            if(this.STATUS == Status.PERSONALIZED) throw new CenterStatusNotMatchingException(this.STATUS, Status.PERSONALIZED);
            this.RUNNING = true; this.THREAD.start();
            this.TASKER.start();}
    public Status save(){
        switch(this.STATUS){
            case RUNNING: this.STATUS = Status.SAVING; break;
            case SAVING: this.STATUS = Status.SAVED; break;
            case SAVED: this.STATUS = Status.RUNNING; break;
        }
        return this.STATUS;
    }
    public Status close(){
        switch(this.STATUS){
            case RUNNING: case SAVING: this.save(); break;
            case SAVED: this.STATUS = Status.CLOSING; break;
            case CLOSING: this.STATUS = Status.CLOSED; break;
        }
        return this.STATUS;
    }

}
