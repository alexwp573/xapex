package xapex.base.center.environment;

import xapex.Xapex;
import xapex.base.Center;
import xapex.base.SingleInstanceHolder;
import xapex.base.Status;
import xapex.base.center.environment.filesystem.FilesPool;
import xapex.base.center.environment.logs.Debugger;
import xapex.base.data.List;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.NoSuchElementException;

public class FilesystemManager extends Center {

    private static class Pair{
        public final String NAME;
        public final String PATH;
        public Pair(String name, String path){
            this.NAME = name; this.PATH = path;
        }
    }

    private final List<Pair> PATHS = new List<>();
        public void path(String name, String path) throws ConfigurationManager.NameOccupiedException {
            String formatedPath = path.replace("/", this.FILE_SEPARATOR);
            for(Pair p : this.PATHS){
                if(p.NAME.compareTo(name) == 0) throw new ConfigurationManager.NameOccupiedException(name);
            }
            this.PATHS.add(new Pair(name, formatedPath));
        }
        public String path(String name) throws NoSuchElementException {
            for(Pair p : this.PATHS){
                if(p.NAME.compareTo(name) == 0){
                    return p.PATH;
                }
            }
            throw new NoSuchElementException(name);
        }
    private final List<FilesPool> FILES_POOLS = new List<>();
        public FilesPool files_pool(String name) {
            for(FilesPool fp : this.FILES_POOLS){
                if(fp.NAME.compareTo(name) == 0)
                    return fp;
            }
            FilesPool tempNew = new FilesPool(name);
            this.FILES_POOLS.add(tempNew);
            return tempNew;
        }

    protected static final SingleInstanceHolder<FilesystemManager> INSTANCE = new SingleInstanceHolder<>();

    // variables

    private String FILE_SEPARATOR = null; public String fileSeparator(){return this.FILE_SEPARATOR;}
    private String PATH_SEPARATOR = null; public String pathSeparator(){return this.PATH_SEPARATOR;}
    private String LINE_SEPARATOR = null; public String lineSeparator(){return this.LINE_SEPARATOR;}

    // constructors

    public FilesystemManager() throws SingleInstanceHolder.InstanceAlreadyExistsException {
        FilesystemManager.INSTANCE.register(this);
    }

    // black-box

    private void detectFileSeparator(){
        this.FILE_SEPARATOR = FileSystems.getDefault().getSeparator();
    }
    private void detectPathSeparator(){
        this.PATH_SEPARATOR = File.pathSeparator;
    }
    private void detectLineSeparator(){
        this.LINE_SEPARATOR = System.lineSeparator();
    }
    private void detectFileCorePaths(){
        try{this.path("USER_HOME", System.getProperty("user.home"));}
        catch(ConfigurationManager.NameOccupiedException nameOccupiedException){throw new RuntimeException(nameOccupiedException);}
        switch(Xapex.EC.SM.os()){
            case WINDOWS10: case WINDOWS11: case WINDOWS12:
                this.PATHS.add(new Pair("WINDOWS_DRIVE_LETTER", this.path("USER_HOME").substring(0, this.path("USER_HOME").indexOf(":") + 1)));
                try {
                    this.path("XAPEX", this.path("USER_HOME") + "/AppData/Local/xapex");
                } catch (ConfigurationManager.NameOccupiedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    this.path("TEMP_DIR", this.path("WINDOWS_DRIVE_LETTER") + "/Windows/Temp/xapex");
                } catch (ConfigurationManager.NameOccupiedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case LINUX: break;
            case MACOS: break;
            case ANDROID: break;
            case IOS: break;
        }
        try{
            this.path("LOGS", this.path("XAPEX") + "/logs");
            this.path("ASSETS", this.path("XAPEX") + "/assets");
            this.path("CONFIGS", this.path("XAPEX") + "/configs");}
        catch(ConfigurationManager.NameOccupiedException nameOccupiedException){throw new RuntimeException(nameOccupiedException);}
    }

    // overrides

    public Status configure(){
        super.configure();

        this.detectFileSeparator();
        this.detectPathSeparator();
        this.detectLineSeparator();
        this.detectFileCorePaths();

        return super.configure();
    }

    public Status personalize(){
        super.personalize();
        try {
            this.files_pool("LOGS").openFiles(Xapex.EC.FM.path("LOGS"));
            this.files_pool("ASSETS").openFiles(Xapex.EC.FM.path("ASSETS"));
            this.files_pool("CONFIGS").openFiles(Xapex.EC.FM.path("CONFIGS"));

        } catch (ConfigurationManager.NameOccupiedException | IOException e) {
            throw new RuntimeException(e);
        }
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