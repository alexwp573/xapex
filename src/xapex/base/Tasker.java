package xapex.base;

import xapex.base.data.Chain;
import xapex.base.data.List;

public class Tasker implements Runnable {

    public interface Task extends Runnable {}

    private final Chain<Thread<Task>> TASKS_QUEUE = new Chain<>();
    private final List<Thread<Task>> TASKS = new List<>();
    public void addTask(Task task){
        Thread<Task> temp = new Thread<>(task);
        synchronized(this) {
            this.TASKS_QUEUE.add(temp);
            this.TASKS.add(temp);
        }
    }

    public final Thread<Tasker> THREAD = new Thread<>(this);
        public void start(){this.RUNNING = true; this.THREAD.start();}
        private boolean RUNNING = false;
        public void stop(){this.RUNNING = false;}
        public void kill(){this.RUNNING = false; this.THREAD.kill();}

    public void run(){
        this.addTask(() -> {
            while(RUNNING)
                synchronized(this) {
                    for (Thread<Task> t : TASKS) {
                        if(!t.isRunning() && t.started()) TASKS.get(t);
                    }
                }
        });
        while(this.RUNNING){
            synchronized(this){
                if(this.TASKS_QUEUE.isEmpty()){
                    this.TASKS_QUEUE.get().start();
                }
            }
        }
    }

}
