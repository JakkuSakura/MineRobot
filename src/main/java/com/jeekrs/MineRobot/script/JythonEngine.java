package com.jeekrs.MineRobot.script;

import com.jeekrs.MineRobot.MineRobot;
import com.jeekrs.MineRobot.util.Utils;
import org.python.core.*;
import org.python.util.PythonInterpreter;

import javax.rmi.CORBA.Util;
import java.io.File;
import java.util.Properties;


public class JythonEngine implements ScriptEngine {
    private PythonInterpreter interp;
    private String rootPath = System.getProperty("user.dir") + "/scripts/";
    private Thread thread;

    public Thread getThread() {
        return thread;
    }

    public JythonEngine() {
        MineRobot.LOGGER.info("Script engine (Jython 2.70) is preparing.");
        Properties props = new Properties();
        props.put("python.home", rootPath);
        props.put("python.console.encoding", "UTF-8");
        props.put("python.security.respectJavaAccessibility", "false");
        props.put("python.import.site", "false");

        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, new String[0]);
        interp = new PythonInterpreter();
        MineRobot.LOGGER.info("Script engine is ready.");
        MineRobot.LOGGER.info("Root path is {}", rootPath);
    }

    @Override
    public void start(String method, String[] args) {
        stop();

        MineRobot.LOGGER.info("Run method {}", method);
        String path = rootPath + method + ".py";
        if (!new File(path).exists()) {
            Utils.showMessage("File not fount: " + path);
            return;
        }
        try {
            interp.execfile(path);
        } catch (Exception e) {
            Utils.showMessage(e.toString());
            return;
        }

        PyFunction func = interp.get(method, PyFunction.class);
        PyObject[] argList = new PyObject[args.length];
        for (int i = 0; i < args.length; ++i)
            argList[i] = PyString.fromInterned(args[i]);

        thread = new Thread(() -> {
            try {
                func.__call__(argList);
                MineRobot.LOGGER.info("method {} finished", method);
            } catch (Exception all) {
                Utils.showMessage("method " + method + " error: " + all.toString());
            } finally {
                thread = null;
            }
        });
        thread.start();
    }

    @Override
    public void stop() {
        if (thread != null)
            thread.stop();
        thread = null;
        // i don't know how to do
    }

}
