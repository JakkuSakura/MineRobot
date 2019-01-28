package com.jeekrs.MineRobot.script;

import com.jeekrs.MineRobot.script.JythonEngine;

public class JythonEngineTest {

    @org.junit.Test
    public void exec() throws InterruptedException {
        JythonEngine jythonEngine = new JythonEngine();
        jythonEngine.start("test", new String[]{});
        while (jythonEngine.getThread() != null)
            Thread.sleep(10);
    }
}