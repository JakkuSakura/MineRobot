package com.jeekrs.MineRobot.script;

import java.io.File;
import java.util.List;

public class FileFinder {
    public static List<File> getAllFiles(List<File> files, String path, String tofind) {
        File fileT = new File(path);
        if (fileT.exists()) {
            if (fileT.getName().equals(tofind))
                files.add(fileT);
            if (fileT.isDirectory()) {
                File[] l = fileT.listFiles();
                if (l != null)
                    for (File f : l) {
                        files = getAllFiles(files, f.getAbsolutePath(), tofind);
                    }
            }
        }
        return files;
    }
}
