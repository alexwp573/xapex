package xapex.base.center.interfaces.inout;

import xapex.Xapex;
import xapex.base.Center;
import xapex.base.SingleInstanceHolder;
import xapex.base.Status;
import xapex.base.center.InterfacesCenter;
import xapex.base.center.environment.ConfigurationManager;
import xapex.base.center.environment.configuration.Configuration;
import xapex.base.center.environment.logs.Logger;
import xapex.base.center.interfaces.inout.text.Command;
import xapex.base.data.List;
import xapex.base.data.SignedElement;

import java.util.Scanner;

public class TextUserInterfaceManager extends Center {

    public interface TuiListener{void request();}
    public interface TuiElement{String getView();}

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

    public Status configure() {
        super.configure();
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
            this.TASKER.addTask(() -> {
                    Xapex.EC.LM.logger("Console").log("INPUT_FOUND");
                    if(cmnd.TARGET == null) {
                        if (ACTIVE_LISTENER == null) {
                            ACTIVE_LISTENER = new SignedElement("TUI", THIS);
                        }


                        //((InterfacesCenter.UserInputListener)(ACTIVE_LISTENER.element())).input(cmnd);


                    }
                    else if(ACTIVE_LISTENER != null && ACTIVE_LISTENER.name().compareTo(cmnd.TARGET) != 0){
                        boolean found = false;
                        for(SignedElement se : LISTENERS){
                            if(se.name().compareTo(cmnd.TARGET) == 0){
                                found = true;
                                ACTIVE_LISTENER = se;
                            }
                        }
                        if(!found){
                            //input(InterfacesCenter.Command.of("[TUI] error < target_not_found=" + cmnd.TARGET + " >"));
                        }
                    }
                    else if(ACTIVE_LISTENER != null && ACTIVE_LISTENER.name().compareTo(cmnd.TARGET) == 0){


                        //((InterfacesCenter.UserInputListener)(ACTIVE_LISTENER.element())).input(cmnd);


                    }
            });

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
