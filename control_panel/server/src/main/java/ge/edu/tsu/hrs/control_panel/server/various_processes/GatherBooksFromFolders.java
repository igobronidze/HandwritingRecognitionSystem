package ge.edu.tsu.hrs.control_panel.server.various_processes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class GatherBooksFromFolders implements VariousProcesses {

	private final String srcPath = "C:\\Users\\ioseb.gobronidze\\Desktop\\ქართული პოეზია";

	private final String resultPath = "C:\\Users\\ioseb.gobronidze\\Desktop\\ქარ. პოე.";

	@Override
	public void process() {
		File srcFile = new File(srcPath);
		File resultFile = new File(resultPath);
		resultFile.mkdir();
		for (File file : srcFile.listFiles()) {
			if (file.isDirectory()) {
				for (File f : file.listFiles()) {
					if (f.isFile()) {
						try {
							Files.copy(f.toPath(), new File(resultPath + "\\" + f.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
						} catch (IOException ex) {
							System.out.println("Can't copy file[" + file.getName() + "]");
						}
					}
				}
			}
		}
	}
}
