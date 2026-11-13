package xapex.base.center.interfaces;

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
                //Xapex.EC.LM.logger("Console").log("Testing: " + c);
                if (bracketsOpen) {
                    if (c.equals('"'))
                        bracketsOpen = false;
                } else {
                    if (c.equals('"'))
                        bracketsOpen = true;
                    else if (c.equals(' ')) {
                        TOKENS.add(cmnd.substring(lastWhiteSpace, i).trim());
                        lastWhiteSpace = i;
                    }
                }
                i++;
            }
			TOKENS.add(cmnd.substring(lastWhiteSpace, i).trim());
        }
        return TOKENS;
    }

    public final String ORIGINAL;
    private String TARGET;
		public String target(){return this.TARGET;}
		public Command target(String trgt){this.TARGET = "[" + trgt + "]"; return this;}
    public final List<Command.CommandPair> COMMANDS = new List<>();

    private Command(String cmnd){
        this.ORIGINAL = cmnd;
        final List<String> TOKENS = getTokens(cmnd);
        /*
        for(String s : TOKENS){
            Xapex.EC.LM.logger("Console").log(s == null ? "null" : s);
        }
        */
        {
            if(TOKENS.check(0).indexOf('[') == 0 && TOKENS.check(0).lastIndexOf(']') == TOKENS.check(0).length() - 1)
                this.TARGET = TOKENS.pull();
            else this.TARGET = null;

			TOKENS.add("EOL");

            String COMMAND = null;
            List<String> ARGUMENT_LIST = new List<>();
            boolean bracketsOpen = false;
            boolean firstToken = true;

            for(String s : TOKENS){
				if(s.compareTo(Command.PASS_START) == 0){
					bracketsOpen = true;
				}
				else if(s.compareTo(Command.PASS_END) == 0){
					bracketsOpen = false;
				}
				else{
					if(bracketsOpen){
						ARGUMENT_LIST.add(s);
					}
					else{
						if(!firstToken) this.COMMANDS.add(new CommandPair(COMMAND, ARGUMENT_LIST));
						COMMAND = s;
						ARGUMENT_LIST = new List<>();
						firstToken = false;
					}
				}
				
				
				/*
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
						this.COMMANDS.add(new Command.CommandPair(COMMAND, ARGUMENT_LIST));
                        COMMAND = s;
                    }
                }
                */
                

            }
        }
    }
	
	public String toString(){
		/*
		StringBuilder sb = new StringBuilder(this.TARGET == null ? "" : this.TARGET);
		for(CommandPair cp : this.COMMANDS){
			sb.append(" ").append(cp.COMMAND).append("( ");
			for(String s : cp.ARGUMENTS)
				sb.append(s).append(" ");
			sb.append(")");
		}
		return sb.toString();
		*/
		return this.ORIGINAL;
	}
}
