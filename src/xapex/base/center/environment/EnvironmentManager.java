package xapex.base.center.environment;

import xapex.base.Center;
import xapex.base.SingleInstanceHolder;
import xapex.base.Status;

public class EnvironmentManager extends Center {

    protected static final SingleInstanceHolder<EnvironmentManager> INSTANCE = new SingleInstanceHolder<>();

    // constructors

    public EnvironmentManager() throws SingleInstanceHolder.InstanceAlreadyExistsException {
        EnvironmentManager.INSTANCE.register(this);
    }

    // overrides

    public Status configure(){
        super.configure();
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
