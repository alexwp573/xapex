package xapex.base.center;

import xapex.base.Center;
import xapex.base.SingleInstanceHolder;
import xapex.base.Status;
import xapex.base.center.environment.*;

public class EnvironmentCenter extends Center {

    protected static final SingleInstanceHolder<EnvironmentCenter> INSTANCE = new SingleInstanceHolder<>();

    public final SystemManager SM = new SystemManager();
        public final SystemManager SYSTEM_MANAGER = this.SM;
    public final FilesystemManager FM = new FilesystemManager();
        public final FilesystemManager FILESYSTEM_MANAGER = this.FM;
    public final EnvironmentManager EM = new EnvironmentManager();
        public final EnvironmentManager ENVIRONMENT_MANAGER = this.EM;
    public final ConfigurationManager CM = new ConfigurationManager();
        public final ConfigurationManager CONFIGURATION_MANAGER = this.CM;
    public final LogsManager LM = new LogsManager();
        public final LogsManager LOGS_MANAGER = this.LM;

    public EnvironmentCenter() throws SingleInstanceHolder.InstanceAlreadyExistsException {
        EnvironmentCenter.INSTANCE.register(this);
    }

    public Status configure() {
        super.configure();

        this.SM.configure();
        this.SM.personalize();
        try {
            this.SM.start();
        } catch (CenterStatusNotMatchingException e) {
            throw new RuntimeException(e);
        }

        this.FM.configure();
        this.FM.personalize();
        try {
            this.FM.start();
        } catch (CenterStatusNotMatchingException e) {
            throw new RuntimeException(e);
        }

        this.EM.configure();
        this.EM.personalize();
        try {
            this.EM.start();
        } catch (CenterStatusNotMatchingException e) {
            throw new RuntimeException(e);
        }

        this.CM.configure();
        this.CM.personalize();
        try {
            this.CM.start();
        } catch (CenterStatusNotMatchingException e) {
            throw new RuntimeException(e);
        }

        this.LM.configure();
        this.LM.personalize();
        try {
            this.LM.start();
        } catch (CenterStatusNotMatchingException e) {
            throw new RuntimeException(e);
        }

        return super.configure();
    }

    public Status personalize(){
        super.personalize();

        return super.personalize();
    }

    public void run(){

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
