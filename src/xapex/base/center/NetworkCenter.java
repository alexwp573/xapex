package xapex.base.center;

import xapex.Xapex;
import xapex.base.Center;
import xapex.base.SingleInstanceHolder;
import xapex.base.Status;
import xapex.base.center.environment.ConfigurationManager;
import xapex.base.center.environment.configuration.Configuration;
import xapex.base.center.environment.logs.Logger;
import xapex.base.center.network.ConnectionsManager;
import xapex.base.data.List;

import java.net.*;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class NetworkCenter extends Center {

    protected static final SingleInstanceHolder<NetworkCenter> INSTANCE = new SingleInstanceHolder<>();

    public final ConnectionsManager CM = new ConnectionsManager();
        public final ConnectionsManager CONNECTIONS_MANAGER = this.CM;

    private String IP;
    private final String LOOPBACK = "127.0.0.1";
    private final List<Integer> PORTS = new List<>();
    private Logger LOGGER;
    private Configuration CONFIGURATION;

    public NetworkCenter() throws SingleInstanceHolder.InstanceAlreadyExistsException {
        NetworkCenter.INSTANCE.register(this);
    }

    private void detectIPs() throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        /*
        while(interfaces.hasMoreElements()){
            NetworkInterface networkInterface = interfaces.nextElement();
            if(!networkInterface.isLoopback()){
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while(addresses.hasMoreElements()){
                    InetAddress addr = addresses.nextElement();
                    if(addr instanceof Inet4Address){
                        Xapex.EC.LM.logger("Console").log(addr.getHostName() + "\t-\t" + addr.getHostAddress());
                    }
                }
            }
        }
        */
        try {
            this.LOGGER.inform(InetAddress.getLocalHost().getHostName() + " - " + InetAddress.getLocalHost().getHostAddress(), "DEFAULT_IP");
        } catch (UnknownHostException e) {
            this.LOGGER.error(e.toString());
        }
    }

    public Status configure() {
        super.configure();

        this.LOGGER = Xapex.EC.LM.registerFileLogger("NetworkCenter");

        try {this.detectIPs();}
        catch (SocketException e) {throw new RuntimeException(e);}

        try{this.CONFIGURATION = Xapex.EC.CM.newConfigurationFromFile("XapexServer");}
        catch(NoSuchElementException | ConfigurationManager.NameOccupiedException e){
            this.LOGGER.error("Error loading configuration: " + e, "CONFIGURATION", this);}

        this.CM.configure();
        this.CM.personalize();
        try {
            this.CM.start();
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
