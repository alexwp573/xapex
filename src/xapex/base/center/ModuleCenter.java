package xapex.base.center;

import java.io.IOException;
import java.util.NoSuchElementException;

import xapex.Xapex;
import xapex.base.data.List;
import xapex.base.Center;
import xapex.base.SingleInstanceHolder;
import xapex.base.Status;
import xapex.base.center.module.Module;
import xapex.base.center.environment.ConfigurationManager;
import xapex.base.center.environment.configuration.Configuration;
import xapex.base.center.environment.logs.Logger;
import xapex.base.center.environment.filesystem.FilesPool;
import xapex.base.center.environment.filesystem.File;

public class ModuleCenter extends Center{
	
	private List<String> MODULES_NAMES = new List<>();
		public String[] modules_names(){
			return (String[])(this.MODULES_NAMES.getData());
		}
		public String listAllModules(){
			StringBuilder sb = new StringBuilder("MODULES:\n");
			for(String s : this.MODULES_NAMES)
				sb.append(s).append("\n");
			return sb.toString();
		}
	private FilesPool MODULES_JARS;
    private Logger LOGGER;
    private Configuration CONFIGURATION;
    protected static final SingleInstanceHolder<ModuleCenter> INSTANCE = new SingleInstanceHolder<>();
    public ModuleCenter() throws SingleInstanceHolder.InstanceAlreadyExistsException {
        ModuleCenter.INSTANCE.register(this);
    }

	public Module getModule(String moduleName) throws NoSuchElementException{
		String className = null;
		for(int i = 0; i < this.MODULES_NAMES.size(); i++)
			if(this.MODULES_NAMES.check(i).compareTo(moduleName) == 0)
				className = moduleName.toLowerCase() + "." + moduleName;
		if(className == null)
			throw new NoSuchElementException("NULL");
		else{try{
				Class<?> clazz = Class.forName(className);
				return (Module)(clazz.getDeclaredConstructor().newInstance());}
			catch(Exception e){
				this.LOGGER.error(e.toString());
				throw new NoSuchElementException(className);
			}
		}
	}

    public Status configure() {
        super.configure();
        
        this.THREAD.name("Module Center");
		
        this.LOGGER = Xapex.EC.LM.registerFileLogger("ModuleCenter");

		this.MODULES_JARS = Xapex.EC.FM.files_pool("ModulesJars");
		try{this.MODULES_JARS.openFiles(Xapex.EC.FM.path("LIBS") + "/modules");}
		catch(java.util.NoSuchElementException | ConfigurationManager.NameOccupiedException | IOException e){
			this.LOGGER.error(e.toString());}
        
        //try {this.CONFIGURATION = Xapex.EC.CM.newConfigurationFromFile("ModuleCenter");}
        //catch (ConfigurationManager.NameOccupiedException e) {this.LOGGER.error("Couldn't load configuration: " + e, "CONFIGURATION", this);}

        return super.configure();
    }

    public Status personalize() {
        super.personalize();

		this.MODULES_NAMES.erase();
		for(String s : this.MODULES_JARS.files()) this.MODULES_NAMES.add(s);

        return super.personalize();
    }

    public void run() {
		
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
