package xapex.base.center.interfaces;

/*

	0 - 1				2
	00 - 11				4
	000 - 111			8
	0000 - 1111			16
	00000 - 11111		32
	000000 - 111111		64

*/

public enum Code{
	FALSE(0, 		"FALSE", 		"FL"),
	FINISH(15, 		"FINISH", 		"FN"),
	FORWARD(28, 	"FORWARD", 		"FW"),
	
	TRUE(1, 		"TRUE", 		"TR"),
	TOP(16, 		"TOP", 			"TP"),
	
	OK(2, 			"OK", 			"OK"),
	
	INFO(3, 		"INFO", 		"IF"),
	
	NOTE(4, 		"NOTE", 		"NT"),
	NEXT(27, 		"NEXT", 		"NX"),
	
	WARN(5, 		"WARN", 		"WR"),
	WAIT(41, 		"WAIT", 		"WT"),
	
	ALERT(6, 		"ALERT", 		"AL"),
	ABORT(42, 		"ABORT", 		"AB"),
	
	DANGER(7, 		"DANGER", 		"DN"),
	DOWN(22, 		"DOWN", 		"DW"),
	
	ERROR(8, 		"ERROR", 		"ER"),
	END(14, 		"END", 			"ED"),
	EXIT(43, 		"EXIT", 		"EX"),
	
	STOP(9, 		"STOP", 		"SP"),
	START(11, 		"START", 		"ST"),
	SIDE(23, 		"SIDE", 		"SD"),
	SHIFT(24, 		"SHIFT", 		"SH"),
	SKIP(38, 		"SKIP", 		"SK"),
	
	CRITICAL(10, 	"CRITICAL", 	"CR"),
	CENTER(19, 		"CENTER", 		"CT"),
	CONTINUE(36, 	"CONTINUE", 	"CN"),
	CHECK(40, 		"CHECK", 		"CH"),
	
	BEGGINING(12, 	"BEGGINING", 	"BG"),
	BOTTOM(21, 		"BOTTOM", 		"BT"),
	BACK(31, 		"BACK", 		"BK"),
	
	MIDDLE(13, 		"MIDDLE", 		"MD"),
	MORE(30, 		"MORE", 		"MR"),
	
	UP(17, 			"UP", 			"UP"),
	
	LEFT(18, 		"LEFT", 		"LT"),
	LESS(33, 		"LESS", 		"LS"),
	LOWER(34, 		"LOWER", 		"LR"),
	
	RIGHT(20, 		"RIGHT", 		"RT"),

	PLAY(25, 		"PLAY", 		"PL"),
	PAUSE(26, 		"PAUSE", 		"PA"),
	PREVIOUS(32, 	"PREVIOUS", 	"PR"),
	
	HIGHER(29, 		"HIGHER", 		"HG"),
	HOLD(37, 		"HOLD", 		"HD"),
	HANDSHAKE(39, 	"HANDSHAKE", 	"HS"),
	
	GO(35, 			"GO", 			"GO"),
	
	KILL(44, 		"KILL", 		"KL");
	public final int CODE;
	public final String NAME;
	public final String SHORT_NAME;
	Code(int code, String name, String shortName){
		this.CODE = code;
		this.NAME = name;
		this.SHORT_NAME = shortName;
	}
	public String toString(){
		return new StringBuilder(this.CODE).append("").append(this.SHORT_NAME).toString();
	}
}
