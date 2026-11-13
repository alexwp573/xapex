package xapex.base.center.module;

import xapex.Xapex;
import xapex.base.Center;
import xapex.base.Thread;
import xapex.base.Status;
import xapex.base.center.interfaces.Command;
import xapex.base.center.interfaces.Api;

public class Module extends Center{
	
	protected static String NAME = "MODULE";
		public String name(){return NAME;}
	protected Api API = new Api(){
		public void request(Command cmnd) throws Api.UnknownRequestException{}
	};
		public Api api(){return this.API;}
	
	public static void main(String[] args) throws Center.CenterStatusNotMatchingException{
		if(!Xapex.isRunning()) Xapex.main(new String[]{});
		Thread.waitFor(() -> Xapex.MC.status() == Status.RUNNING);
		try{Xapex.IC.API.request(Command.of("[X] module < \"" + NAME + "\" >"));}
		catch(Api.UnknownRequestException ure){Xapex.EC.LM.logger(NAME).error(ure.toString());}
	}
	
}
