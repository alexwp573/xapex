package xapex.base.center.environment;

import xapex.Xapex;
import xapex.base.Center;
import xapex.base.SingleInstanceHolder;
import xapex.base.Status;
import xapex.base.center.EnvironmentCenter;
import xapex.base.center.environment.configuration.Configuration;
import xapex.base.center.environment.configuration.ConfigurationListener;
import xapex.base.center.environment.filesystem.File;
import xapex.base.center.environment.filesystem.FilesPool;
import xapex.base.data.List;

import java.util.NoSuchElementException;

public class ConfigurationManager extends Center {

    protected static final SingleInstanceHolder<ConfigurationManager> INSTANCE = new SingleInstanceHolder<>();

    // declarations

    public static class NameOccupiedException extends Exception{
        public final String NAME;
        public NameOccupiedException(String name){this.NAME = name;}
        public String toString(){return "Name <" + this.NAME + "> already occupied";}
    }

    // variables

    private FilesPool CONFIG_FILES;
    private final List<Configuration> CONFIGURATIONS = new List<>();
        public Configuration configuration(String name) throws NoSuchElementException {
            for(Configuration c : this.CONFIGURATIONS){
                if(c.name().compareTo(name) == 0) return c;
            }
            throw new NoSuchElementException("Configuration <" + name + "> does not exist");
        }
        public Configuration newConfiguration(Configuration config) throws NameOccupiedException {
            try{this.configuration(config.name());
            throw new NameOccupiedException(config.name());}
            catch(NoSuchElementException noSuchElementException){this.CONFIGURATIONS.add(config); return config;}
        }
        public Configuration newConfigurationFromFile(String fileName) throws NameOccupiedException {
            File temp = this.CONFIG_FILES.getOpenAndCreateFile(fileName, fileName + ".config", Xapex.EC.FM.path("CONFIGS"));
            return this.newConfiguration(new Configuration(fileName, temp));
        }

    // constructors

    public ConfigurationManager() throws SingleInstanceHolder.InstanceAlreadyExistsException {
        ConfigurationManager.INSTANCE.register(this);
    }

    // overrides

    public Status configure(){
        super.configure();
        this.CONFIG_FILES = Xapex.EC.FM.files_pool("CONFIGS");
        return super.configure();
    }

    public Status personalize(){
        super.personalize();
        return super.personalize();
    }

    public void run() {

    }

    public Status save(){
        super.save();

        

        return super.save();
    }

    public Status close(){
        super.close();
        return super.close();
    }
}
