package ge.edu.tsu.hrs.control_panel.server.various_processes;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class DivideCharacters implements VariousProcesses {

    private final String srcPath = "C:\\Users\\gobrona\\Desktop\\vefxistyaosani";

    private final String resultPath = "C:\\Users\\gobrona\\Desktop\\divided\\";

    @Override
    public void process() {
        File srcFile = new File(srcPath);
        for (File file : srcFile.listFiles()) {
            try {
                String fileName = file.getName();
                String fileNameWithoutExtension = fileName.replaceFirst("[.][^.]+$", "");
                String dirName = fileNameWithoutExtension.split("_")[1];
                String dirPath = resultPath + dirName;
                File dir = new File(dirPath);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                FileUtils.copyFile(file, new File(dirPath + "\\" + fileName));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
