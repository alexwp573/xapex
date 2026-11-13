package xapex.base.center.interfaces;

public abstract class Api{
	
	public static class UnknownRequestException extends Exception{
		private Command COMMAND = null;
		public Command command(){return this.COMMAND;}
		public UnknownRequestException(Command cmnd){
			this.COMMAND = cmnd;
		}
		public String toString(){
			return new StringBuilder("Unknown command found in line: ").append(this.COMMAND.toString()).toString();
		}
	}
	
	public abstract void request(Command cmnd) throws UnknownRequestException;
}
