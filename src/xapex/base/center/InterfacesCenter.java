package xapex.base.center;

import xapex.Xapex;
import xapex.base.Center;
import xapex.base.SingleInstanceHolder;
import xapex.base.Status;
import xapex.base.center.environment.ConfigurationManager;
import xapex.base.center.environment.configuration.Configuration;
import xapex.base.center.environment.logs.Logger;
import xapex.base.center.interfaces.inout.*;
import xapex.base.center.interfaces.input.*;
import xapex.base.center.interfaces.output.*;

public class InterfacesCenter extends Center {

    private Logger LOGGER;
    private Configuration CONFIGURATION;

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

    public Status configure() {
        super.configure();

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

        if(((String)(this.CONFIGURATION.configurationLine("remote").value())).compareTo("true") == 0){
            this.KUI.personalize();
            xapex.base.Thread.waitFor(() -> this.KUI.status() == Status.PERSONALIZED);
            try {this.KUI.start();}
            catch (CenterStatusNotMatchingException e) {this.LOGGER.error("Cannot start KUI: " + e, "RUN", this.KUI);}
        }

        if(((String)(this.CONFIGURATION.configurationLine("remote").value())).compareTo("true") == 0){
            this.VUI.personalize();
            xapex.base.Thread.waitFor(() -> this.VUI.status() == Status.PERSONALIZED);
            try {this.VUI.start();}
            catch (CenterStatusNotMatchingException e) {this.LOGGER.error("Cannot start VUI: " + e, "RUN", this.VUI);}
        }

        if(((String)(this.CONFIGURATION.configurationLine("remote").value())).compareTo("true") == 0){
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
