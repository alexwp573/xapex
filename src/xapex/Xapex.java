package xapex;

import xapex.base.Center;
import xapex.base.Status;
import xapex.base.Thread;
import xapex.base.center.*;
import xapex.base.center.environment.logs.Logger;

// TEST IMPORTS
import xapex.base.Tasker;

public class Xapex{
	
	private static boolean RUNNING = false;
		public static boolean isRunning(){return Xapex.RUNNING;}

    //<3 :)

    public static final DatabaseCenter DC = new DatabaseCenter();
        public static final DatabaseCenter DATABASE_CENTER = Xapex.DC;
    public static final EnvironmentCenter EC = new EnvironmentCenter();
        public static final EnvironmentCenter ENVIRONMENT_CENTER = Xapex.EC;
    public static final InterfacesCenter IC = new InterfacesCenter();
        public static final InterfacesCenter INTERFACES_CENTER = Xapex.IC;
    public static final NetworkCenter NC = new NetworkCenter();
        public static final NetworkCenter NETWORK_CENTER = Xapex.NC;
    public static final SecurityCenter SC = new SecurityCenter();
        public static final SecurityCenter SECURITY_CENTER = Xapex.SC;
    public static final UserCenter UC = new UserCenter();
        public static final UserCenter USER_CENTER = Xapex.UC;
    public static final ModuleCenter MC = new ModuleCenter();
		public static final ModuleCenter MODULE_CENTER = Xapex.MC;

    private static Logger LOGGER;

    public static void main(String[] args) throws Center.CenterStatusNotMatchingException {
		Xapex.RUNNING = true;
		if(args.length == 0){

	        /*
	        * configuring - checking the system requirements setting base variables in own-environment
	        * personalizing - configuration that asks other center for data/task
	        * starting - center ready for tasking
	        *
	        * */
	
	        Xapex.EC.configure();
	        Xapex.UC.configure();
	        Xapex.IC.configure();
	        Xapex.SC.configure(); Xapex.SC.configure();
	        Xapex.NC.configure();
	        Xapex.MC.configure();
	        //Xapex.DC.configure(); Xapex.DC.configure();
	
	        Xapex.LOGGER = Xapex.EC.LM.registerFileLogger("Xapex");
	        Xapex.LOGGER.inform("All centres configured", "CONFIGURATION", new Xapex());
	
	        Xapex.EC.personalize();
	        Thread.waitFor(() -> Xapex.EC.status() == Status.PERSONALIZED);
	        Xapex.EC.start();
	        Xapex.LOGGER.inform("EC started", "RUN", Xapex.EC);
	
	        Xapex.UC.personalize();
	        Thread.waitFor(() -> Xapex.UC.status() == Status.PERSONALIZED);
	        Xapex.UC.start();
	        Xapex.LOGGER.inform("UC started", "RUN", Xapex.UC);
	
	        Xapex.SC.personalize(); Xapex.SC.personalize();
	        Thread.waitFor(() -> Xapex.SC.status() == Status.PERSONALIZED);
	        Xapex.SC.start();
	        Xapex.LOGGER.inform("SC started", "RUN", Xapex.SC);
	
	        Xapex.NC.personalize(); Xapex.NC.personalize();
	        Thread.waitFor(() -> Xapex.NC.status() == Status.PERSONALIZED);
	        Xapex.NC.start();
	        Xapex.LOGGER.inform("NC started", "RUN", Xapex.NC);
	
	        Xapex.IC.personalize();
	        Thread.waitFor(() -> Xapex.IC.status() == Status.PERSONALIZED);
	        Xapex.IC.start();
	        Xapex.LOGGER.inform("IC started", "RUN", Xapex.IC);
	
	        Xapex.MC.personalize();
	        Thread.waitFor(() -> Xapex.MC.status() == Status.PERSONALIZED);
	        Xapex.MC.start();
	        Xapex.LOGGER.inform("MC started", "RUN", Xapex.MC);
	
	        //Xapex.DC.personalize(); Xapex.DC.personalize();
	        //Thread.waitFor(() -> Xapex.DC.status() == Status.PERSONALIZED);
	        //Xapex.DC.start();
	        //Xapex.LOGGER.inform("DC started", "RUN", Xapex.DC);
	        
		}
		else{
			switch(args[0]){
				case "TEST":
					System.out.println("Welcome to test module!");
					Xapex.test();
					break;
				case "TEST2":
					System.out.println("Welcome to test v2 module!");
					Xapex.test2();
					break;
				case "TEST3":
					System.out.println("Welcome to test v3 module!");
					Xapex.test3();
					break;
			}
		}

    }
    
    public static void test(){
		System.out.println("DECLARATING AND INITIALIZING TASKER");
		final Tasker TASKER = new Tasker();
		System.out.println("DECLARATED AND INITIALIZED TASKER");
		System.out.println("STARTING TASKER");
		TASKER.start();
		System.out.println("STARTED TASKER");
		TASKER.addTask(() -> {
			System.out.println("HELLO! I am task!");
		});
	}
    public static void test2(){
		
	}
    public static void test3(){
		
	}

}
