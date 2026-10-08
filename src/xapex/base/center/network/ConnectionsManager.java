package xapex.base.center.network;

import xapex.base.Center;
import xapex.base.SingleInstanceHolder;
import xapex.base.Status;
import xapex.base.data.List;

public class ConnectionsManager extends Center {

    protected static final SingleInstanceHolder<ConnectionsManager> INSTANCE = new SingleInstanceHolder<>();

    public ConnectionsManager() throws SingleInstanceHolder.InstanceAlreadyExistsException {
        ConnectionsManager.INSTANCE.register(this);
    }

    public Status configure() {
        super.configure();



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
