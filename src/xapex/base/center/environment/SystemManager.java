package xapex.base.center.environment;

import xapex.base.Center;
import xapex.base.SingleInstanceHolder;
import xapex.base.Status;

public class SystemManager extends Center {

    protected static final SingleInstanceHolder<SystemManager> INSTANCE = new SingleInstanceHolder<>();

    // declarations

    public static enum Os{
        LINUX("Linux"),
        WINDOWS10("Windows 10"),
        WINDOWS11("Windows 11"),
        WINDOWS12("Windows 12"),
        ANDROID("Android"),
        MACOS("MacOS"),
        IOS("iOS");
        public final String NAME;
        private Os(String name){this.NAME = name;}
    }

    // variables

    private Os OS = null; public Os os(){return this.OS;}
    private String OSV = null; public String version(){return this.OSV;}
    private String ARCH = null; public String arch(){return this.ARCH;}

    // constructors

    public SystemManager() throws SingleInstanceHolder.InstanceAlreadyExistsException {
        SystemManager.INSTANCE.register(this);
    }

    // black-box

    private void detectOS(){
        switch(System.getProperty("os.name")){
            case "Windows 11": this.OS = Os.WINDOWS11; break;
            case "Linux": this.OS = Os.LINUX; break;
        }
    }
    private void detectOSV(){
        this.OSV = System.getProperty("os.version");
    }
    private void detectArch(){
        this.ARCH = System.getProperty("os.arch");
    }

    // overrides

    public Status configure(){
        super.configure();

        this.detectOS();
        this.detectOSV();
        this.detectArch();

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
