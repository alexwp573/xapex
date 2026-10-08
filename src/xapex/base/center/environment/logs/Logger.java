package xapex.base.center.environment.logs;

import xapex.base.Id;
import xapex.base.center.environment.LogsManager;
import xapex.base.center.environment.filesystem.File;
import xapex.base.data.List;

public class Logger {

    public final String NAME;
        public String name() {return this.NAME;}

    private int STYLE = Integer.MAX_VALUE;
        public int style(){return this.STYLE;}
        public void style(int style){this.STYLE = style;}

    private final List<LogsManager.LogTarget> TARGETS = new List<>();
        public void addTarget(LogsManager.LogTarget target){
            for(LogsManager.LogTarget t : this.TARGETS)
                if(t == target) return;
            this.TARGETS.add(target);}
        public void removeTarget(LogsManager.LogTarget target){this.TARGETS.get(target);}

    private File FILE;
        public File file(){return this.FILE;}
        public void file(File file){this.FILE = file;}
    // SOCKET FIELD
    /*
    private Socket SOCKET;
        public Socket socket(){return this.SOCKET;}
        public void socket(Socket socket){this.SOCKET = socket;}
    */
    // UI FIELD
    /*
    private UiLogger UI;
        public UiLogger ui(){return this.UI;}
        public void ui(UiLogger ui){this.UI = ui;}
    */

    public Logger(String name){
        this.addTarget(LogsManager.LogTarget.CONSOLE);
        this.NAME = name;
    }
    public Logger(String name, File file){
        this.addTarget(LogsManager.LogTarget.FILE);
        this.NAME = name;
        this.file(file);
    }
    // SOCKET CONSTRUCTOR
    /*
    public Logger(String name, Socket socket){
        this.TARGET = LogsManager.LogTarget.NETWORK;
        this.NAME = name;
        this.socket(socket);
    }
    */
    // UI CONSTRUCTOR
    /*
    public Logger(String name, UiLogger uiLog){
        this.TARGET = LogsManager.LogTarget.UI;
        this.NAME = name;
        this.ui(uiLog);
    }
    */

    /*
    +INFO @2026/09/17|08:22:30|123:456:789 $SOURCE #TITLE >>>msg
    !WARN @2026/09/17|08:22:30|123:456:789 $SOURCE #TITLE >>>msg
    XERRR @2026/09/17|08:22:30|123:456:789 $SOURCE #TITLE >>>msg
    */

    public void log(String msg){
        this.log(msg, null, null);
    }
    public void log(String msg, Object source){
        this.log(msg, null, source);
    }
    public void log(String msg, String title){
        this.log(msg, title, null);
    }
    public void log(String msg, String title, Object source){
        this.targetLog(this.buildLog(null, new Id(), source, title, msg));
    }

    public void debug(String msg){
        this.debug(msg, null, null);
    }
    public void debug(String msg, Object source){
        this.debug(msg, null, source);
    }
    public void debug(String msg, String title){
        this.debug(msg, title, null);
    }
    public void debug(String msg, String title, Object source){
        this.targetLog(this.buildLog(LogsManager.LogType.DEBUG, new Id(), source, title, msg));
    }

    public void inform(String msg){
        this.inform(msg, null, null);
    }
    public void inform(String msg, Object source){
        this.inform(msg, null, source);
    }
    public void inform(String msg, String title){
        this.inform(msg, title, null);
    }
    public void inform(String msg, String title, Object source){
        this.targetLog(this.buildLog(LogsManager.LogType.INFORMATION, new Id(), source, title, msg));
    }

    public void note(String msg){
        this.note(msg, null, null);
    }
    public void note(String msg, Object source){
        this.note(msg, null, source);
    }
    public void note(String msg, String title){
        this.note(msg, title, null);
    }
    public void note(String msg, String title, Object source){
        this.targetLog(this.buildLog(LogsManager.LogType.NOTE, new Id(), source, title, msg));
    }

    public void warn(String msg){
        this.warn(msg, null, null);
    }
    public void warn(String msg, Object source){
        this.warn(msg, null, source);
    }
    public void warn(String msg, String title){
        this.warn(msg, title, null);
    }
    public void warn(String msg, String title, Object source){
        this.targetLog(this.buildLog(LogsManager.LogType.WARNING, new Id(), source, title, msg));
    }

    public void alert(String msg){
        this.alert(msg, null, null);
    }
    public void alert(String msg, Object source){
        this.alert(msg, null, source);
    }
    public void alert(String msg, String title){
        this.alert(msg, title, null);
    }
    public void alert(String msg, String title, Object source){
        this.targetLog(this.buildLog(LogsManager.LogType.ALERT, new Id(), source, title, msg));
    }

    public void danger(String msg){
        this.danger(msg, null, null);
    }
    public void danger(String msg, Object source){
        this.danger(msg, null, source);
    }
    public void danger(String msg, String title){
        this.danger(msg, title, null);
    }
    public void danger(String msg, String title, Object source){
        this.targetLog(this.buildLog(LogsManager.LogType.DANGER, new Id(), source, title, msg));
    }

    public void error(String msg){
        this.error(msg, null, null);
    }
    public void error(String msg, Object source){
        this.error(msg, null, source);
    }
    public void error(String msg, String title){
        this.error(msg, title, null);
    }
    public void error(String msg, String title, Object source){
        this.targetLog(this.buildLog(LogsManager.LogType.ERROR, new Id(), source, title, msg));
    }

    public void stop(String msg){
        this.stop(msg, null, null);
    }
    public void stop(String msg, Object source){
        this.stop(msg, null, source);
    }
    public void stop(String msg, String title){
        this.stop(msg, title, null);
    }
    public void stop(String msg, String title, Object source){
        this.targetLog(this.buildLog(LogsManager.LogType.STOP, new Id(), source, title, msg));
    }

    public void critical(String msg){
        this.critical(msg, null, null);
    }
    public void critical(String msg, Object source){
        this.critical(msg, null, source);
    }
    public void critical(String msg, String title){
        this.critical(msg, title, null);
    }
    public void critical(String msg, String title, Object source){
        this.targetLog(this.buildLog(LogsManager.LogType.CRITICAL, new Id(), source, title, msg));
    }

    private String buildLog(LogsManager.LogType type, Id id, Object source, String title, String msg){
        StringBuilder stringBuilder = new StringBuilder();
        if(type != null)
            stringBuilder.append(type.CLASS).append(type).append("\t\t");
        if(id != null)
            stringBuilder.append("@").append(id).append("\t\t");
        if(title != null)
            stringBuilder.append("#").append(title).append("\t\t");
        if(source != null)
            stringBuilder.append("$").append(source).append("\t\t");
        return stringBuilder.append(">>>").append(msg).toString();
    }
    private void targetLog(String msg){
        for(LogsManager.LogTarget lt : this.TARGETS){
            switch(lt){
                case CONSOLE:
                    this.logToConsole(msg);
                    break;
                case FILE:
                    this.logToFile(msg);
                    break;
                case UI:
                    this.logToUi(msg);
                    break;
                case NETWORK:
                    this.logToSocket(msg);
                    break;
            }
        }
    }

    private void logToConsole(String msg){
        System.out.println(msg);
    }
    private void logToFile(String msg){
        this.FILE.appendLine(msg);
    }
    private void logToUi(String msg){
        //this.UI.addLine(msg);
    }
    private void logToSocket(String msg){
        //this.SOCKET.send(msg);
    }
}
