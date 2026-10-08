package xapex.base.center.environment.configuration;

import xapex.base.center.environment.ConfigurationManager;
import xapex.base.center.environment.filesystem.File;
import xapex.base.data.List;

import java.io.ObjectInputFilter;
import java.util.NoSuchElementException;

public class Configuration {

    public record ConfigurationLine<T>(String title, T value) {
        public Class<?> valueClass(){return value.getClass();}
    }

    private String NAME = null;
        public String name(){return this.NAME;}

    public final List<ConfigurationListener> LISTENERS = new List<>();
        public void sendUpdate() {
            for (ConfigurationListener cl : this.LISTENERS) {
                cl.getUpdate(this);
            }
        }

    private final List<ConfigurationLine<?>> CONFIGURATION_LINES = new List<>();
        public ConfigurationLine<?> configurationLine(String name) throws NoSuchElementException{
            for(ConfigurationLine<?> cl : this.CONFIGURATION_LINES){
                if(cl.title().compareTo(name) == 0) return cl;
            }
            throw new NoSuchElementException(name);
        }
        public Class<?> classOf(String label) throws NoSuchElementException{
            return configurationLine(label).valueClass();
        }
        public void addConfigurationLine(ConfigurationLine<?> configurationLine) throws ConfigurationManager.NameOccupiedException {
            try{configurationLine(configurationLine.title());
                throw new ConfigurationManager.NameOccupiedException(configurationLine.title);}
            catch(NoSuchElementException nsee){this.CONFIGURATION_LINES.add(configurationLine); this.sendUpdate();}
        }
        public void updateConfigurationLine(ConfigurationLine<?> configurationLine){
            for(ConfigurationLine<?> cl : this.CONFIGURATION_LINES)
                if (cl.title.compareTo(configurationLine.title) == 0){
                    this.CONFIGURATION_LINES.get(cl);
                    try{this.addConfigurationLine(configurationLine); return;}
                    catch(ConfigurationManager.NameOccupiedException nameOccupiedException){}
                }
        }

    public Configuration(String name){this.NAME = name;}
    public Configuration(String name, File file){
        this(name);
        String[] temp = file.readLines();
        for(String s : temp){
            String[] temp2 = s.split(" = ");
            String title = temp2[0];
            String value = temp2[1];
            try {
                this.addConfigurationLine(new ConfigurationLine<>(title, value));
            } catch (ConfigurationManager.NameOccupiedException e) {
                this.CONFIGURATION_LINES.get(this.configurationLine(title));
                try {
                    this.addConfigurationLine(new ConfigurationLine<>(title, value));
                } catch (ConfigurationManager.NameOccupiedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder(this.NAME + "\n");
        for(ConfigurationLine<?> cl : this.CONFIGURATION_LINES){
            sb.append(cl.title).append(" = ").append(cl.value.toString()).append("\n");
        }
        return sb.toString();
    }
}
