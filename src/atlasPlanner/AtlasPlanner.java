package atlasPlanner;

import xapex.Xapex;
import xaepex.base.*;
import xaepex.base.data.*;
import xapex.base.center.module.Module;

public class AtlasPlanner extends Module{
	
	protected static String NAME = "AtlasPlanner";
	protected Api API = new Api(){
		public void request(Command cmnd) throws Api.UnknownRequestException{
			
		}
	};
	
	public Day OPERATIONAL_DAY = null;
	
	public class Day{
		
	}
	
}
