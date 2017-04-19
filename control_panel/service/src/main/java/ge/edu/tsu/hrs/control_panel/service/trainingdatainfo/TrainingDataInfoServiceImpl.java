package ge.edu.tsu.hrs.control_panel.service.trainingdatainfo;

import ge.edu.tsu.hrs.control_panel.server.dao.trainingdatainfo.TrainingDataInfoDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.trainingdatainfo.TrainingDataInfoDAOImpl;

public class TrainingDataInfoServiceImpl implements TrainingDataInfoService {

	private final TrainingDataInfoDAO trainingDataInfoDAO = new TrainingDataInfoDAOImpl();

	public void deleteTrainingDataInfo(int id) {
		trainingDataInfoDAO.deleteTrainingDataInfo(id);
	}
}
