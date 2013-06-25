/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nodomain.volkerk.LoggingLib;

import java.util.*;

/**
 *
 * @author volker
 */
public class LoggingClass {
    protected static final String LOG_FILL = " ......... ";
    protected static final String LOG_OK = "SUCCESS";
    protected static final String LOG_FAIL = "FAILED";
    protected static final String LOG_UNKNOWN = "UNKNOWN";
    protected static final String LOG_ERR = "ERROR  : ";
    protected static final String LOG_WARN = "WARNING: ";
    protected static final String LOG_INFO = "INFO   : ";
    protected static final String LOG_DEBUG = "DEBUG  : ";
    protected static final String LOG_FAILPREFIX = "FAIL   : ";
    protected static final int LVL_DEBUG = 0;
    protected static final int LVL_INFO = 1;
    protected static final int LVL_WARN = 2;
    protected static final int LVL_FAIL = 3;
    protected static final int LVL_ERR = 4;
    protected static String[] lvlMsg = new String[]{LOG_DEBUG, LOG_INFO, LOG_WARN, LOG_FAILPREFIX, LOG_ERR};
    
    protected static int logLvl = LVL_WARN;
    private static Deque<String> logStack = new ArrayDeque<>();
    
    /**
     * Converts any number of arguments to strings and concatenates the strings; used for logging purposes.
     * 
     * @param objs the objects to convert and concatenate
     * @return the string containing the concatenated object-strings
     */
    protected static String strCat(Object ... objs)
    {
        String msg = "";
        for (Object o : objs)
        {
            msg += o.toString();
        }

        return msg;
    }
    
    /**
     * Logs any data to stderr. Used as central hook for later extensions, e. g. callbacks for a GUI
     * 
     * @param objs the data to be logged
     */
    protected static void log(boolean appendNewline, int lvl, Object ... objs)
    {
        
        if ((lvl >= 0) && (lvl < logLvl)) return;
        
        String msg = strCat(objs);
        
        if (lvl >= 0)
        {
            for (int i=0; i<logStack.size(); i++) msg = "  " + msg;
            msg = lvlMsg[lvl] + " " + msg;
        }
        
        if (appendNewline) System.err.println(msg);
        else System.err.print(msg);
    }
    
    /**
     * Prints the first part of a log message, without final newline
     * 
     * @param objs objects to compose the log message of
     */
    protected static void preLog(int lvl, Object ... objs)
    {
        // prepare the new message
        log(false, lvl, strCat(objs) + LOG_FILL);
    }
    
    /**
     * Completes a log line along with a result
     * 
     * @param result the result to append to the previously printed first part of the line
     */
    protected static void resultLog(String result)
    {
        // if there's no pre-logged message, we throw an exception
        log(true, -1, result);
    }
    
    protected static void info(Object ... args)
    {
        log(true, LVL_INFO, args);
    }
    
    
    protected static void dbg(Object ... args)
    {
        log(true, LVL_DEBUG, args);
    }
    
    protected static void failed(Object ... args)
    {
        log(true, LVL_FAIL, args);
    }
    
    protected static void logPush(Object ... args)
    {
        log(true, LVL_DEBUG, args);
        logStack.push(strCat(args));
    }
        
    protected static void logPop(String result)
    {
        String msg = result + " " + logStack.pop();
        log(true, LVL_DEBUG, msg);
    }
    
}
