package xapex.base.center.interfaces.inout;

import xapex.base.Center;
import xapex.base.SingleInstanceHolder;
import xapex.base.Status;

public class GraphicUserInterfaceManager extends Center {

    protected static final SingleInstanceHolder<GraphicUserInterfaceManager> INSTANCE = new SingleInstanceHolder<>();
    public GraphicUserInterfaceManager() throws SingleInstanceHolder.InstanceAlreadyExistsException {
        GraphicUserInterfaceManager.INSTANCE.register(this);
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
