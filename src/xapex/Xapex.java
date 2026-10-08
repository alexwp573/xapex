package xapex;

import xapex.base.Center;
import xapex.base.Status;
import xapex.base.Thread;
import xapex.base.center.*;
import xapex.base.center.environment.logs.Logger;

public class Xapex{

    //<3 :)

    public static final DatabaseCenter DC = new DatabaseCenter();
        public static final DatabaseCenter DATABASE_CENTER = Xapex.DC;
    public static final EnvironmentCenter EC = new EnvironmentCenter();
        public static final EnvironmentCenter ENVIRONMENT_CENTER = Xapex.EC;
    public static final InterfacesCenter IC = new InterfacesCenter();
        public static final InterfacesCenter INTERFACES_CENTER = Xapex.IC;
    public static final NetworkCenter NC = new NetworkCenter();
        public static final NetworkCenter NETWORK_CENTER = Xapex.NC;
    public static final SecurityCenter SC = new SecurityCenter();
        public static final SecurityCenter SECURITY_CENTER = Xapex.SC;
    public static final UserCenter UC = new UserCenter();
        public static final UserCenter USER_CENTER = Xapex.UC;

    private static Logger LOGGER;

    static void main(String[] args) throws Center.CenterStatusNotMatchingException {

        /*
        * configuring - checking the system requirements setting base variables in own-environment
        * personalizing - configuration that asks other center for data/task
        * starting - center ready for tasking
        *
        * */

        Xapex.EC.configure();
        Xapex.UC.configure();
        Xapex.IC.configure();
        Xapex.SC.configure(); Xapex.SC.configure();
        Xapex.NC.configure();
        //Xapex.DC.configure(); Xapex.DC.configure();

        Xapex.LOGGER = Xapex.EC.LM.registerFileLogger("Xapex");
        Xapex.LOGGER.inform("All centres configured", "CONFIGURATION", new Xapex());

        Xapex.EC.personalize();
        Thread.waitFor(() -> Xapex.EC.status() == Status.PERSONALIZED);
        Xapex.EC.start();
        Xapex.LOGGER.inform("EC started", "RUN", Xapex.EC);

        Xapex.UC.personalize();
        Thread.waitFor(() -> Xapex.UC.status() == Status.PERSONALIZED);
        Xapex.UC.start();
        Xapex.LOGGER.inform("UC started", "RUN", Xapex.UC);

        Xapex.SC.personalize(); Xapex.SC.personalize();
        Thread.waitFor(() -> Xapex.SC.status() == Status.PERSONALIZED);
        Xapex.SC.start();
        Xapex.LOGGER.inform("SC started", "RUN", Xapex.SC);

        Xapex.NC.personalize(); Xapex.NC.personalize();
        Thread.waitFor(() -> Xapex.NC.status() == Status.PERSONALIZED);
        Xapex.NC.start();
        Xapex.LOGGER.inform("NC started", "RUN", Xapex.NC);

        Xapex.IC.personalize();
        Thread.waitFor(() -> Xapex.IC.status() == Status.PERSONALIZED);
        Xapex.IC.start();
        Xapex.LOGGER.inform("IC started", "RUN", Xapex.IC);

        //Xapex.DC.personalize(); Xapex.DC.personalize();
        //Thread.waitFor(() -> Xapex.DC.status() == Status.PERSONALIZED);
        //Xapex.DC.start();
        //Xapex.LOGGER.inform("DC started", "RUN", Xapex.DC);

    }

}
