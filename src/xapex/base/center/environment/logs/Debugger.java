package xapex.base.center.environment.logs;

import java.util.Scanner;

public class Debugger {

    public static void db(String msg){
        System.out.println("DEBUG\t\t\t|\t" + msg);
    }

    public static void db(String done, String next){
        System.out.println("DEBUG\t\t\t|\t" + done + "\n\tNEXT\t\t|\t" + next);
    }

    public static void bp(){
        System.out.print("\tBREAK-POINT |\tpress enter");
        new Scanner(System.in).nextLine();
    }

    public static void bp(String msg){
        System.out.print("DEBUG\t\t\t|\t" + msg + "\n");
        Debugger.bp();
    }

    public static void bp(String done, String next){
        System.out.print("DEBUG\t\t\t|\t" + done + "\n\tNEXT\t\t|\t" + next + "...\n");
        Debugger.bp();
    }
}

























//>>> [XAPEX.NETWORK_CENTER] connect < ip=127.0.0.1 port=12345 !uns title="test connection" > send < "test" >

/*
>>>
    indicator
[XAPEX.NETWORK_CENTER]
    request target
connect
    command
<
    passing the parameters to the command
ip=127.0.0.1
    argument (default title value)
port=12345
    argument (titled value)
!uns
    argument (flag)
title="test connection"
    argument (titled value that includes whitespaces or equal sign)
>
    passing ending
send
    command
<
    passing the parameters to the command
"test"
    argument (default parameters count and types case)
>
    passing ending
>
    passing ending
*/