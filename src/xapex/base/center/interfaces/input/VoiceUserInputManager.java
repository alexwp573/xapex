package xapex.base.center.interfaces.input;

import xapex.base.Center;
import xapex.base.SingleInstanceHolder;
import xapex.base.Status;

public class VoiceUserInputManager extends Center {

    protected static final SingleInstanceHolder<VoiceUserInputManager> INSTANCE = new SingleInstanceHolder<>();
    public VoiceUserInputManager() throws SingleInstanceHolder.InstanceAlreadyExistsException {
        VoiceUserInputManager.INSTANCE.register(this);
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
