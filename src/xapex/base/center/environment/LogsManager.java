package xapex.base.center.environment;

import xapex.Xapex;
import xapex.base.Center;
import xapex.base.SingleInstanceHolder;
import xapex.base.Status;
import xapex.base.center.environment.filesystem.File;
import xapex.base.center.environment.logs.Logger;
import xapex.base.center.environment.filesystem.FilesPool;
import xapex.base.data.List;

import java.io.IOException;
import java.util.NoSuchElementException;

public class LogsManager extends Center {

    public enum LogType{
        DEBUG("DEBUG", "DEBG", (byte) 0, 'B', '+'),
        INFORMATION("INFORMATION", "INFO", (byte) 1, 'I', '+'),
        NOTE("NOTE", "NOTE", (byte) 2, 'N', '+'),

        ALERT("ALERT", "ALRT", (byte) 3, 'A', '!'),
        WARNING("WARNING", "WARN", (byte) 4, 'W', '!'),
        DANGER("DANGER", "DNGR", (byte) 5, 'D', '!'),

        STOP("STOP", "STOP", (byte) 6, 'S', 'X'),
        ERROR("ERROR", "ERRR", (byte) 7, 'E', 'X'),
        CRITICAL("CRITICAL", "CRIT", (byte) 8, 'C', 'X');
        public final String NAME;
        public final String SHORT_NAME;
        public final byte LEVEL;
        public final char LETTER;
        public final char CLASS;
        private LogType(String name, String shortName, byte level, char letter, char clas){
            this.NAME = name;
            this.SHORT_NAME = name;
            this.LEVEL = level;
            this.LETTER = letter;
            this.CLASS = clas;
        }
    }
    public enum LogTarget{
        CONSOLE("CONSOLE"),
        FILE("FILE"),
        UI("UI"),
        NETWORK("NETWORK");
        public final String NAME;
        LogTarget(String name){
            this.NAME = name;
        }
        public String toString(){return this.NAME;}
    }
    public static final LogTarget CONSOLE = LogTarget.CONSOLE;
    public static final LogTarget FILE = LogTarget.FILE;
    public static final LogTarget UI = LogTarget.UI;
    public static final LogTarget NETWORK = LogTarget.NETWORK;

    // 0000 - 0 - none
    // 0001 - 1 - type
    // 0010 - 2 - stamp
    // 0100 - 4 - title
    // 1000 - 8 - source
    public static final int NONE = 0b0000;
    public static final int TYPE = 0b0001;
    public static final int STAMP = 0b0010;
    public static final int TITLE = 0b0100;
    public static final int SOURCE = 0b1000;
    public static final int TYPE_AND_STAMP = 0b0011;
    public static final int TYPE_AND_TITLE = 0b0101;
    public static final int TYPE_AND_SOURCE = 0b1001;
    public static final int STAMP_AND_TITLE = 0b0110;
    public static final int STAMP_AND_SOURCE = 0b1010;
    public static final int TITLE_AND_SOURCE = 0b1100;
    public static final int TYPE_STAMP_TITLE = 0b0111;
    public static final int TYPE_STAMP_SOURCE = 0b1101;
    public static final int TYPE_TITLE_SOURCE = 0b1011;
    public static final int STAMP_TITLE_SOURCE = 0b1110;
    public static final int ALL = Integer.MAX_VALUE;

    private FilesPool LOG_FILES;
    private final List<Logger> LOGGERS = new List<>();
        public Logger logger(String name) {
            for(Logger l : this.LOGGERS)
                if(l.name().compareTo(name) == 0)
                    return l;
            return this.logger("Lost");
        }
        public Logger register(Logger logger) throws ConfigurationManager.NameOccupiedException {
            for(Logger l : this.LOGGERS){
                if(l == logger) {
                    return logger;
                }
                if(l.name().compareTo(logger.name()) == 0){
                    throw new ConfigurationManager.NameOccupiedException(logger.name());
                }
            }
            this.LOGGERS.add(logger);
            return logger;
        }
        public Logger registerFileLogger(String name){
            File file = Xapex.EC.LM.LOG_FILES.getOpenAndCreateFile(name, name + ".log", Xapex.EC.FM.path("LOGS"));
            try {
                return this.register(new Logger(name, file));
            } catch (ConfigurationManager.NameOccupiedException e) {
                throw new RuntimeException("HERE | " + e);
            }
        }
        public void deregister(Logger logger){
            this.LOGGERS.get(logger);
        }

    protected static final SingleInstanceHolder<LogsManager> INSTANCE = new SingleInstanceHolder<>();

    // constructors

    public LogsManager() throws SingleInstanceHolder.InstanceAlreadyExistsException {
        LogsManager.INSTANCE.register(this);
    }

    // overrides

    public Status configure(){
        super.configure();
        this.LOG_FILES = Xapex.EC.FM.files_pool("LOGS");
        try {
            this.register(new Logger("Console"));
            this.registerFileLogger("Lost");
        } catch (ConfigurationManager.NameOccupiedException e) {
            throw new RuntimeException(e);
        }
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
