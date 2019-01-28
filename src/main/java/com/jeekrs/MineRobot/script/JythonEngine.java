package com.jeekrs.MineRobot.script;

import com.jeekrs.MineRobot.MineRobot;
import com.jeekrs.MineRobot.util.LogUtil;
import org.jline.utils.Log;
import org.python.core.*;
import org.python.util.PythonInterpreter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class JythonEngine implements ScriptEngine {
    private PythonInterpreter interp;
    private String rootPath;
    private Thread thread;

    public Thread getThread() {
        return thread;
    }

    public JythonEngine() {
        LogUtil.log("Script engine (Jython 2.70) is preparing.");

        rootPath = System.getProperty("user.dir") + File.separator + "scripts";

        if (!new File(rootPath).exists()) {
            rootPath = new File(System.getProperty("user.dir")).getParent() + File.separator + "scripts";
            if (!new File(rootPath).exists())
                throw new RuntimeException("Cannot locate scripts path");
        }
        LogUtil.log("Root path is " + rootPath);

        Properties props = new Properties();
        props.put("python.home", rootPath);
        props.put("python.console.encoding", "UTF-8");
        props.put("python.security.respectJavaAccessibility", "true");
        props.put("python.import.site", "false");
        PySystemState.initialize(null, props, new String[0]);


        PySystemState sys = Py.getSystemState();
        sys.path.append(new PyString(rootPath));

        interp = new PythonInterpreter(null, sys);
        LogUtil.log("Script engine is ready.");
    }

    @Override
    public void start(String method, String[] args) {
        stop();

        thread = new Thread(() -> {
            try {
                LogUtil.showMessage("Run method " + method);
                String path = rootPath + File.separator + method + ".py";
                if (!new File(path).exists()) {
                    LogUtil.showMessage("File not fount: " + path);
                    return;
                }

                interp.execfile(path);

                PyFunction func = interp.get(method, PyFunction.class);
                PyObject[] argList = new PyObject[args.length];
                for (int i = 0; i < args.length; ++i)
                    argList[i] = PyString.fromInterned(args[i]);

                func.__call__(argList);
                LogUtil.showMessage("method " + method + " finished");
            } catch (ThreadDeath e) {
                LogUtil.showMessage("method " + method + " stopped");
            } catch (Exception all) {
                if (all.getCause() instanceof ThreadDeath)
                    LogUtil.showMessage("method " + method + " stopped");
                else {
                    LogUtil.showMessage("method " + method + " error: " + all.toString());
                    all.printStackTrace();
                }
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

    // about reload:
    // please use reload(module)

}
