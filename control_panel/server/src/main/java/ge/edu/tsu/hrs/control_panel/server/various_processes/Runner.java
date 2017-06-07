package ge.edu.tsu.hrs.control_panel.server.various_processes;

public class Runner {

	private static final VariousProcessesType TYPE = VariousProcessesType.DIVIDE_CHARACTERS;

	public static void main(String[] args) {
		VariousProcesses variousProcesses = null;
		switch (TYPE) {
			case CUT_SYMBOLS_SPLITTER:
				variousProcesses = new CutSymbolsSplitter();
				break;
			case GATHER_BOOKS_FROM_FOLDERS:
				variousProcesses = new GatherBooksFromFolders();
				break;
			case MNIST_DATA_CREATOR:
				variousProcesses = new MNISTDataCreator();
				break;
			case DIVIDE_CHARACTERS:
				variousProcesses = new DivideCharacters();
				break;
		}
		variousProcesses.process();
	}
}
