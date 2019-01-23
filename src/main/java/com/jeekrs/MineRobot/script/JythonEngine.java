package com.jeekrs.MineRobot.script;

import com.jeekrs.MineRobot.MineRobot;
import org.python.core.*;
import org.python.util.PythonInterpreter;

import java.util.Properties;


public class JythonEngine implements ScriptEngine {
    private PythonInterpreter interp;
    private String rootPath = System.getProperty("user.dir") + "/scripts/";
    private Thread thread;
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
        interp.execfile(path);
        PyFunction func = interp.get(method, PyFunction.class);
        PyObject py_pro = MineRobot.INSTANCE != null ? PyJavaType.wrapJavaObject(MineRobot.INSTANCE) : Py.None;
        PyArray pyArray = new PyArray(PyString.class, args.length);
        for (String e : args)
            pyArray.append(PyString.fromInterned(e));
        thread = new Thread(() -> func.__call__(py_pro, pyArray));
        thread.start();
    }

    @Override
    public void stop() {
        if(thread != null)
            thread.stop();
        thread = null;
        // i don't know how to do
    }

}
