package com.jeekrs.MineRobot.script;
import com.jeekrs.MineRobot.script.JythonEngine;

public class JythonEngineTest {

    @org.junit.Test
    public void exec() {
        JythonEngine jythonEngine = new JythonEngine();
        jythonEngine.start("hello", new String[0]);
    }
}