package xapex.base.center.interfaces.inout;

import xapex.Xapex;
import xapex.base.Center;
import xapex.base.SingleInstanceHolder;
import xapex.base.Status;
import xapex.base.center.InterfacesCenter;
import xapex.base.center.environment.ConfigurationManager;
import xapex.base.center.environment.configuration.Configuration;
import xapex.base.center.environment.logs.Logger;
import xapex.base.center.interfaces.Command;
import xapex.base.data.List;
import xapex.base.data.Chain;
import xapex.base.data.SignedElement;
import xapex.base.Thread;
import xapex.base.center.interfaces.Api;

import java.util.Scanner;

public class TextUserInterfaceManager extends Center {

	public interface Listener{void request();}
    public interface Element{String getView();}
    public interface Agent{Agent getParent();}

    private Logger LOGGER;
    private Configuration CONFIGURATION;
    private final TextUserInterfaceManager THIS = this;

    protected static final SingleInstanceHolder<TextUserInterfaceManager> INSTANCE = new SingleInstanceHolder<>();
    public TextUserInterfaceManager() throws SingleInstanceHolder.InstanceAlreadyExistsException {
        TextUserInterfaceManager.INSTANCE.register(this);
    }

    private final List<SignedElement> LISTENERS = new List<>();
    private SignedElement ACTIVE_LISTENER = null;
    private final Scanner SCANNER = new Scanner(System.in);
    private boolean WAITING_FOR_INPUT = false;
    private final Chain<String> QUEUED_TASKS = new Chain<>();
		public void show(String info){
			synchronized(this){
				this.QUEUED_TASKS.add(info);
			}
		}
	
    public Status configure() {
        super.configure();
        
        this.THREAD.name("Text User Interface Manager");
        
        this.LOGGER = Xapex.EC.LM.logger("InterfacesCenter");
        return super.configure();
    }

    public Status personalize() {
        super.personalize();

        try {
            this.CONFIGURATION = Xapex.EC.CM.newConfigurationFromFile("TextUserInterfaceManager");
        } catch (ConfigurationManager.NameOccupiedException e) {
            throw new RuntimeException(e);
        }

        // ---------------------------------- add configuration object with for example indicator for commands

        return super.personalize();
    }

    public void run() {
        while(this.RUNNING){
			
			System.out.print(this.CONFIGURATION.configurationLine("indicator").value());
            Command cmnd = Command.of(SCANNER.nextLine());
            
			try{Xapex.IC.API.request(cmnd);}
			catch(Api.UnknownRequestException ure){this.show(ure.toString());}
			
			synchronized(this){
				while(!this.QUEUED_TASKS.isEmpty()){
					System.out.println(this.QUEUED_TASKS.get());
				}
			}
        }
    }

    public Status save() {
        super.save();
        return super.save();
    }

    public Status close() {
        super.close();
        return super.close();
    }

}
