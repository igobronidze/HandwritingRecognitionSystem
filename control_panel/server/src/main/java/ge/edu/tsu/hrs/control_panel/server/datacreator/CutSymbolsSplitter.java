package ge.edu.tsu.hrs.control_panel.server.datacreator;

import ge.edu.tsu.hrs.control_panel.server.util.CharacterUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CutSymbolsSplitter {

    private static final String srcPath = "D:\\hrs\\HRSImageData\\cut_symbols\\handwritten\\vefxistyaosani_test";

    private static final String trainPath = srcPath + "/train";

    private static final String testPath = srcPath + "/test";

    public static void main(String[] args) {
        Map<Character, List<File>> charToFilesMap = new HashMap<>();
        File srcFile = new File(srcPath);
        File trainFile = new File(trainPath);
        trainFile.mkdir();
        File testFile = new File(testPath);
        testFile.mkdir();
        for (File file : srcFile.listFiles()) {
            try {
                String fileName = file.getName();
                String fileNameWithoutExtension = fileName.replaceFirst("[.][^.]+$", "");
                String text = fileNameWithoutExtension.split("_")[1];
                char symbol = CharacterUtil.getCharFromFileName(text);
                charToFilesMap.putIfAbsent(symbol, new ArrayList<>());
                charToFilesMap.get(symbol).add(file);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        for (List<File> files : charToFilesMap.values()) {
            Collections.shuffle(files);
        }
        for (List<File> files : charToFilesMap.values()) {
            int k = files.size() / 6;
            for (int i = 0; i < k; i++) {
                try {
                    Files.copy(files.get(i).toPath(), new File(testFile.getPath() + "/" + files.get(i).getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            for (int i = k; i < files.size(); i++) {
                try {
                    Files.copy(files.get(i).toPath(), new File(trainFile.getPath() + "/" + files.get(i).getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}
