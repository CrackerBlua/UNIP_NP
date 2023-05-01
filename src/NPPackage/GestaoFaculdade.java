package NPPackage;

public class GestaoFaculdade {
	
	public static FileService fileService = FileService.getInstance();

	public static void main(String[] args){
		GestaoFaculdadeBO.loadData();
		FileService.checkExistsFolder();
		CommandUtils.awaitTimeAndRun(() -> { GestaoFaculdadeBO.executeMenu(); }, 5000);
	}
}
