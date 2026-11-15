package xapex.base.center;

import java.util.NoSuchElementException;

import xapex.Xapex;
import xapex.base.Center;
import xapex.base.SingleInstanceHolder;
import xapex.base.Status;
import xapex.base.Thread;
import xapex.base.center.environment.ConfigurationManager;
import xapex.base.center.environment.configuration.Configuration;
import xapex.base.center.environment.logs.Logger;
import xapex.base.center.interfaces.*;
import xapex.base.center.interfaces.inout.*;
import xapex.base.center.interfaces.input.*;
import xapex.base.center.interfaces.output.*;
import xapex.base.center.module.Module;

import xapex.base.data.Calendar;

public class InterfacesCenter extends Center {

    private Logger LOGGER;
    private Configuration CONFIGURATION;
	public final Api API = new Api(){
		
		/*
		
		X - XAPEX
			exit
			exit < 0 >
			module < "ATLAS PLANNER" >
			save
			close
		DBC - DATABASE
			???
		ENV - ENVIRONMENT
			log < type=critical title="test a" msg="" >
			openFile < path="path/to/file" fileName="file.name" >
		IFC - INTERFACES
			gui
			tui
			remote < onConnectionTitled="test a" >
			keyboard < true/false >
			voice < true/false >
			sound < true/false >
		NET - NETWORK
			connect < IP=127.0.0.1 port=12345 titled="test a" >
		SEC - SECURITY
			authenticate
		USR - USER
			register < userName=alex >
			login < userName=alex >
			logout
			check
			check < userName=alex >
		
		*/
		
		public void request(Command cmnd) throws UnknownRequestException{
			if(cmnd.COMMANDS.check(0).COMMAND().compareTo("quit") == 0 || cmnd.COMMANDS.check(0).COMMAND().compareTo("q") == 0)
				ACTIVE_MODULE = null;
			else{
				if(cmnd.target() != null)
					switch(cmnd.target()){
						case "[X]": case "[XAPEX]": {
								while(cmnd.COMMANDS.count() > 0){
									Command.CommandPair cp = cmnd.COMMANDS.pull();
									switch(cp.COMMAND()){
										case "exit": case "EXIT":{
												if(cp.ARGUMENTS().size() == 0){
													Xapex.IC.TUI.show("Exiting the Xapex frame-work with code 0...");
													Xapex.DC.close();
													Xapex.EC.close();
													Xapex.IC.save();
													Xapex.MC.close();
													Xapex.NC.close();
													Xapex.SC.close();
													Xapex.UC.close();
													Xapex.IC.addTask(() -> System.exit(0));}
												else{final Integer errorCode;
													try{errorCode = Integer.parseInt(cp.ARGUMENTS().check(0));
														Xapex.IC.TUI.show("Exiting the Xapex frame-work with code " + errorCode.toString() + "...");
														Xapex.DC.close();
														Xapex.EC.close();
														Xapex.IC.save();
														Xapex.MC.close();
														Xapex.NC.close();
														Xapex.SC.close();
														Xapex.UC.close();
														Xapex.IC.addTask(() -> System.exit(errorCode));}
													catch(Exception e){throw new UnknownRequestException(cmnd);}
												}
											}
											break;
										case "quit": case "QUIT":{
											Xapex.IC.TUI.show("Quiting the Xapex frame-work...");
											Xapex.DC.close();
											Xapex.EC.close();
											Xapex.IC.save();
											Xapex.MC.close();
											Xapex.NC.close();
											Xapex.SC.close();
											Xapex.UC.close();
											Xapex.IC.addTask(() -> System.exit(0));}
											break;
										case "list": case "LIST":{
												Command.CommandPair cp2 = cmnd.COMMANDS.pull();
												if(cp2 != null){
													switch(cp2.COMMAND()){
														case "threads": case "THREADS":{
															Xapex.IC.TUI.show(Thread.listAllThreads());}
															break;
														case "modules": case "MODULES":{
															Xapex.IC.TUI.show(Xapex.MC.listAllModules());
															}
															break;
														default:{
															throw new UnknownRequestException(cmnd);
														}
													}
												}
												else{
													throw new UnknownRequestException(cmnd);
												}
											}
											break;
										case "module": case "MODULE":{
												String newActvMod = cp.ARGUMENTS().pull();
												try{ACTIVE_MODULE = Xapex.MC.getModule(newActvMod);}
												catch(NoSuchElementException nsee){Xapex.EC.LM.console().error(nsee.toString(), "EXCEPTION");}
												cp.ARGUMENTS().erase();
											}
											break;
										case "test": case "TEST":{
												Command.CommandPair cp2 = cmnd.COMMANDS.pull();
												if(cp2 != null){
													switch(cp2.COMMAND()){
														case "tasker": case "TASKER": {
															Xapex.IC.TUI.show(
															"Tasker is "
															+ (Center.TASKER.THREAD.isRunning() ? "RUNNING" : "AWAITING")
															+ " Tasker started? "
															+ (Center.TASKER.THREAD.started() ? "YES" : "NO"));
															Xapex.IC.addTask(() -> Xapex.IC.TUI.show("Testing TASKER..."));
															Thread.waitFor(2, false);
															Xapex.IC.TUI.show(
															"Tasker is "
															+ (Center.TASKER.THREAD.isRunning() ? "RUNNING" : "AWAITING")
															+ " Tasker started? "
															+ (Center.TASKER.THREAD.started() ? "YES" : "NO"));
															}
															break;
														case "date": case "DATE":{
																int y = Integer.parseInt(cp2.ARGUMENTS().check(0));
																int m = Integer.parseInt(cp2.ARGUMENTS().check(1));
																int d = Integer.parseInt(cp2.ARGUMENTS().check(2));
																Calendar.Date now = Calendar.now();
																try{
																	Calendar.Date testDate = Calendar.dateOf(y, m, d);
																	Xapex.IC.TUI.show(testDate.toString());
																	Xapex.IC.TUI.show(testDate.toString("cc/yy/mm/dd/mm/yyyy"));
																}
																catch(Calendar.DateDoesNotExistsException ddnee){Xapex.IC.TUI.show(ddnee.toString());}
															}
															break;
														default:{
															throw new UnknownRequestException(cmnd);
														}
													}
												}
												else{
													throw new UnknownRequestException(cmnd);
												}
											}
											break;
										default: throw new UnknownRequestException(cmnd);
									}
								}
							}
							break;
						case "[DBC]": case "[DATABASE]": {
							}
							break;
						case "[ENV]": case "[ENVIRONMENT]": {
							}
							break;
						case "[IFC]": case "[INTERFACE]": {
							}
							break;
						case "[NET]": case "[NETWORK]": {
							}
							break;
						case "[SEC]": case "[SECURITY]": {
							}
							break;
						case "[USR]": case "[USER]": {
							}
							break;
						default: throw new UnknownRequestException(cmnd);
					}
				else{
					if(ACTIVE_MODULE == null){ cmnd.target("X"); this.request(cmnd);}
					else{ACTIVE_MODULE.api().request(cmnd);}
				}
			}
		}
		
	};

    public final GraphicUserInterfaceManager GUI = new GraphicUserInterfaceManager();
    public final TextUserInterfaceManager TUI = new TextUserInterfaceManager();
    public final RemoteUserInterfaceManager RUI = new RemoteUserInterfaceManager();

    public final KeyboardUserInputManager KUI = new KeyboardUserInputManager();
    public final VoiceUserInputManager VUI = new VoiceUserInputManager();

    public final SoundUserOutputManager SUO = new SoundUserOutputManager();

    protected static final SingleInstanceHolder<InterfacesCenter> INSTANCE = new SingleInstanceHolder<>();
    public InterfacesCenter() throws SingleInstanceHolder.InstanceAlreadyExistsException {
        InterfacesCenter.INSTANCE.register(this);
    }

	private Module ACTIVE_MODULE = null;

    public Status configure() {
        super.configure();
        
        this.THREAD.name("Interfaces Center");

        this.LOGGER = Xapex.EC.LM.registerFileLogger("InterfacesCenter");
        try {this.CONFIGURATION = Xapex.EC.CM.newConfigurationFromFile("InterfaceCenter");}
        catch (ConfigurationManager.NameOccupiedException e) {this.LOGGER.error("Couldn't load configuration: " + e, "CONFIGURATION", this);}

        switch((String)(this.CONFIGURATION.configurationLine("main").value())){
            case "graphic": this.GUI.configure(); break;
            case "text": this.TUI.configure(); break;
        }

        if(((String)(this.CONFIGURATION.configurationLine("remote").value())).compareTo("true") == 0) this.RUI.configure();
        if(((String)(this.CONFIGURATION.configurationLine("key").value())).compareTo("true") == 0) this.KUI.configure();
        if(((String)(this.CONFIGURATION.configurationLine("voice").value())).compareTo("true") == 0) this.VUI.configure();
        if(((String)(this.CONFIGURATION.configurationLine("sound").value())).compareTo("true") == 0) this.SUO.configure();

        return super.configure();
    }

    public Status personalize() {
        super.personalize();

        switch((String)(this.CONFIGURATION.configurationLine("main").value())){
            case "graphic":
                this.GUI.personalize();
                xapex.base.Thread.waitFor(() -> this.GUI.status() == Status.PERSONALIZED);
                try {this.GUI.start();}
                catch (CenterStatusNotMatchingException e) {this.LOGGER.error("Cannot start GUI: " + e, "RUN", this.GUI);}
                break;
            case "text":
                this.TUI.personalize();
                xapex.base.Thread.waitFor(() -> this.TUI.status() == Status.PERSONALIZED);
                try {this.TUI.start();}
                catch (CenterStatusNotMatchingException e) {this.LOGGER.error("Cannot start TUI: " + e, "RUN", this.TUI);}
                break;
        }

        if(((String)(this.CONFIGURATION.configurationLine("remote").value())).compareTo("true") == 0){
            this.RUI.personalize();
            xapex.base.Thread.waitFor(() -> this.RUI.status() == Status.PERSONALIZED);
            try {this.RUI.start();}
            catch (CenterStatusNotMatchingException e) {this.LOGGER.error("Cannot start RUI: " + e, "RUN", this.RUI);}
        }

        if(((String)(this.CONFIGURATION.configurationLine("key").value())).compareTo("true") == 0){
            this.KUI.personalize();
            xapex.base.Thread.waitFor(() -> this.KUI.status() == Status.PERSONALIZED);
            try {this.KUI.start();}
            catch (CenterStatusNotMatchingException e) {this.LOGGER.error("Cannot start KUI: " + e, "RUN", this.KUI);}
        }

        if(((String)(this.CONFIGURATION.configurationLine("voice").value())).compareTo("true") == 0){
            this.VUI.personalize();
            xapex.base.Thread.waitFor(() -> this.VUI.status() == Status.PERSONALIZED);
            try {this.VUI.start();}
            catch (CenterStatusNotMatchingException e) {this.LOGGER.error("Cannot start VUI: " + e, "RUN", this.VUI);}
        }

        if(((String)(this.CONFIGURATION.configurationLine("sound").value())).compareTo("true") == 0){
            this.SUO.personalize();
            xapex.base.Thread.waitFor(() -> this.SUO.status() == Status.PERSONALIZED);
            try {this.SUO.start();}
            catch (CenterStatusNotMatchingException e) {this.LOGGER.error("Cannot start SUO: " + e, "RUN", this.SUO);}
        }

        return super.personalize();
    }

    public void run() {

        if(((String)(this.CONFIGURATION.configurationLine("remote").value())).compareTo("true") == 0){
            try {this.RUI.start();}
            catch (CenterStatusNotMatchingException e) {this.LOGGER.error("Cannot start RUI: " + e, "RUN", this.RUI);}
        }

        if(((String)(this.CONFIGURATION.configurationLine("remote").value())).compareTo("true") == 0){
            try {this.KUI.start();}
            catch (CenterStatusNotMatchingException e) {this.LOGGER.error("Cannot start KUI: " + e, "RUN", this.KUI);}
        }

        if(((String)(this.CONFIGURATION.configurationLine("remote").value())).compareTo("true") == 0){
            try {this.VUI.start();}
            catch (CenterStatusNotMatchingException e) {this.LOGGER.error("Cannot start VUI: " + e, "RUN", this.VUI);}
        }

        if(((String)(this.CONFIGURATION.configurationLine("remote").value())).compareTo("true") == 0){
            try {this.SUO.start();}
            catch (CenterStatusNotMatchingException e) {this.LOGGER.error("Cannot start SUO: " + e, "RUN", this.SUO);}
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
