package xapex.base.center.interfaces.inout.text;

import xapex.Xapex;
import xapex.base.center.InterfacesCenter;
import xapex.base.data.List;

public class Command {

    public record CommandPair(String COMMAND, List<String> ARGUMENTS){}

    public static final String PASS_START = "<";
    public static final String PASS_END = ">";
    public static final char FLAG_INDICATOR = '!';
    public static final char ARGUMENT_TITLED = '=';

    public static Command of(String cmnd){return new Command(cmnd);}
    private static List<String> getTokens(String cmnd) {
        final List<String> TOKENS = new List<>();
        {
            int lastWhiteSpace = 0;
            int i = 0;
            boolean bracketsOpen = false;
            for (Character c : cmnd.toCharArray()) {
                Xapex.EC.LM.logger("Console").log("Testing: " + c);
                if (bracketsOpen) {
                    if (c.equals('"'))
                        bracketsOpen = false;
                } else {
                    if (c.equals('"'))
                        bracketsOpen = true;
                    else if (c.equals(' ')) {
                        TOKENS.add(cmnd.substring(lastWhiteSpace, i));
                        lastWhiteSpace = i;
                    }
                }
                i++;
            }
        }
        return TOKENS;
    }

    public final String ORIGINAL;
    public final String TARGET;
    public final List<Command.CommandPair> COMMANDS = new List<>();

    private Command(String cmnd){
        this.ORIGINAL = cmnd;
        final List<String> TOKENS = getTokens(cmnd);
        for(String s : TOKENS){
            Xapex.EC.LM.logger("Console").log(s == null ? "null" : s);
        }
        {
            if(TOKENS.check(0).indexOf('[') == 0 && TOKENS.check(0).lastIndexOf(']') == 0)
                this.TARGET = TOKENS.pull();
            else this.TARGET = null;

            String COMMAND = null;
            List<String> ARGUMENT_LIST = new List<>();
            boolean bracketsOpen = false;

            for(String s : TOKENS){
                if(bracketsOpen){
                    if(s.compareTo(Command.PASS_END) == 0){
                        bracketsOpen = false;
                        this.COMMANDS.add(new Command.CommandPair(COMMAND, ARGUMENT_LIST));
                        COMMAND = null;
                        ARGUMENT_LIST = new List<>();
                    }
                    else{
                        ARGUMENT_LIST.add(s);
                    }
                }
                else{
                    if(s.compareTo(Command.PASS_START) == 0){
                        bracketsOpen = true;
                    }
                    else{
                        COMMAND = s;
                    }
                }

            }
        }
    }
}