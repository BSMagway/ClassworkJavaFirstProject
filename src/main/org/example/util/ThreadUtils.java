package org.example.util;

import java.util.Date;

public class ThreadUtils {

    public static String buildName(Thread th){
        return th.getId() + " " + th.getName() + " [" + th.getState() + "]";
    }
    public static void startLog(Thread th){
        System.out.println(buildName(th) + " is STARTED " + new Date());
    }

    public static void endLog(Thread th){
       System.out.println(buildName(th) + " is FINISHED " + new Date());
    }

}
