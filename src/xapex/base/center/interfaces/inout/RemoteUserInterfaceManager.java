package xapex.base.center.interfaces.inout;

import xapex.base.Center;
import xapex.base.SingleInstanceHolder;
import xapex.base.Status;

public class RemoteUserInterfaceManager extends Center {

    protected static final SingleInstanceHolder<RemoteUserInterfaceManager> INSTANCE = new SingleInstanceHolder<>();
    public RemoteUserInterfaceManager() throws SingleInstanceHolder.InstanceAlreadyExistsException {
        RemoteUserInterfaceManager.INSTANCE.register(this);
    }

    public Status configure() {
        super.configure();



        return super.configure();
    }

    public Status personalize() {
        super.personalize();
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
