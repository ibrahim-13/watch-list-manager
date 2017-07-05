//    Copyright (C) 2017 MD. Ibrahim Khan
//
//    Project Name: 
//    Author: MD. Ibrahim Khan
//    Author's Email: ib.arshad777@gmail.com
//
//    Redistribution and use in source and binary forms, with or without modification,
//    are permitted provided that the following conditions are met:
//
//    1. Redistributions of source code must retain the above copyright notice, this
//       list of conditions and the following disclaimer.
//
//    2. Redistributions in binary form must reproduce the above copyright notice, this
//       list of conditions and the following disclaimer in the documentation and/or
//       other materials provided with the distribution.
//
//    3. Neither the name of the copyright holder nor the names of the contributors may
//       be used to endorse or promote products derived from this software without
//       specific prior written permission.
//
//    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
//    ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
//    WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
//    IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
//    INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING
//    BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
//    DATA, OR PROFITS; OR BUSINESS INTERRUPTIONS) HOWEVER CAUSED AND ON ANY THEORY OF
//    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
//    OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
//    OF THE POSSIBILITY OF SUCH DAMAGE.

package watchlistmanager.model.logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arshad
 */
public class AppLogger {
    public static String LOG_FILE_NAME = "stack_trace.log";
    public static String DATA_DIR = "./appData";
    
    private File logFile;
    private Thread t;
    private final Object threadControl = new Object();
    private ArrayList<Exception> errorList;
    
    public AppLogger() {
        File DataDir = new File(DATA_DIR);
        if(!DataDir.exists()) {
            DataDir.mkdirs();
        }
        logFile = new File(DATA_DIR + "/" + LOG_FILE_NAME);
        if(!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(AppLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        if(!logFile.exists()) {
//            logFile.mkdirs();
//        }
        errorList = new ArrayList<>();
        t = new Thread(getThreadRunnable(), "Logger Thread");
        t.setDaemon(true);
        t.start();
    }
    
    public void log(Exception e) {
        synchronized(errorList) {
            errorList.add(e);
        }
        synchronized(threadControl) {
            threadControl.notifyAll();
        }
    }
    
    private Runnable getThreadRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                while(true) {
                    synchronized(threadControl) {
                        try {
                            threadControl.wait();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(AppLogger.class.getName()).log(Level.SEVERE, null, ex);
                            continue;
                        }
                    }
                    ArrayList<Exception> tmpList;
                    synchronized(errorList) {
                        tmpList = new ArrayList<>(errorList);
                    }
                    
                    PrintWriter pr = null;
                    try {
                        pr = new PrintWriter(logFile);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(AppLogger.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    if(pr != null) {
                        for(Exception e : tmpList) {
                            pr.println("=========================================================");
                            pr.println(e.getMessage());
                            for(StackTraceElement st : e.getStackTrace()) {
                                pr.println(st.toString());
                            }
                            pr.println("=========================================================");
                        }
                    }
                    if(pr != null) {
                        pr.close();
                    }
                }
            }
        };
    }
}
