package com.jeekrs.MineRobot.script;

import com.jeekrs.MineRobot.MineRobot;
import com.jeekrs.MineRobot.util.LogUtil;
import org.python.core.*;
import org.python.util.PythonInterpreter;

import java.io.File;
import java.util.Properties;


public class JythonEngine implements ScriptEngine {
    private PythonInterpreter interp;
    private String rootPath = System.getProperty("user.dir") + File.separator + "scripts";
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

        PySystemState sys = Py.getSystemState();
        sys.path.append(new PyString(rootPath));
        interp = new PythonInterpreter(null, sys);


        MineRobot.LOGGER.info("Script engine is ready.");
        MineRobot.LOGGER.info("Root path is {}", rootPath);
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
                // todo
            } catch (Exception all) {
                LogUtil.showMessage("method " + method + " error: " + all.toString());
                all.printStackTrace();
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

    public void reload() {
        if (interp != null) {
//            interp.exec("import sys\n" +
//                    "sys.modules.clear()");
            interp.exec("import sys\n" +
                    "if globals().has_key('init_modules'):\n" +
                    "\tfor m in [x for x in sys.modules.keys() if x not in init_modules]:\n" +
                    "\t\tdel(sys.modules[m]) \n" +
                    "else:\n" +
                    "\tinit_modules = sys.modules.keys()");
        }
    }

}
