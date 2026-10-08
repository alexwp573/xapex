package xapex.base.center.interfaces.input;

import xapex.base.Center;
import xapex.base.SingleInstanceHolder;
import xapex.base.Status;

public class KeyboardUserInputManager extends Center {

    protected static final SingleInstanceHolder<KeyboardUserInputManager> INSTANCE = new SingleInstanceHolder<>();
    public KeyboardUserInputManager() throws SingleInstanceHolder.InstanceAlreadyExistsException {
        KeyboardUserInputManager.INSTANCE.register(this);
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
