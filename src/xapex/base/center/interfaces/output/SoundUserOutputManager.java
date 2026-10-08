package xapex.base.center.interfaces.output;

import xapex.base.Center;
import xapex.base.SingleInstanceHolder;
import xapex.base.Status;

public class SoundUserOutputManager extends Center {

    protected static final SingleInstanceHolder<SoundUserOutputManager> INSTANCE = new SingleInstanceHolder<>();
    public SoundUserOutputManager() throws SingleInstanceHolder.InstanceAlreadyExistsException {
        SoundUserOutputManager.INSTANCE.register(this);
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
