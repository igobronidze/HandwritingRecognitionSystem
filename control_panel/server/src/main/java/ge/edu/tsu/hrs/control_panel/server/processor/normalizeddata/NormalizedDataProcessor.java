package ge.edu.tsu.hrs.control_panel.server.processor.normalizeddata;

import ge.edu.tsu.hrs.control_panel.model.network.TrainingDataInfo;
import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hrs.control_panel.model.network.normalizeddata.NormalizedData;
import ge.edu.tsu.hrs.control_panel.server.dao.normalizeddata.GroupedNormalizedDataDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.normalizeddata.GroupedNormalizedDataDAOImpl;
import ge.edu.tsu.hrs.control_panel.server.dao.normalizeddata.NormalizedDataDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.normalizeddata.NormalizedDataDAOImpl;
import ge.edu.tsu.hrs.control_panel.server.processor.normalizeddata.normalizationmethod.DiscreteByAreaNormalization;
import ge.edu.tsu.hrs.control_panel.server.processor.normalizeddata.normalizationmethod.DiscreteResizeNormalization;
import ge.edu.tsu.hrs.control_panel.server.processor.normalizeddata.normalizationmethod.LinearByAreaNormalization;
import ge.edu.tsu.hrs.control_panel.server.processor.normalizeddata.normalizationmethod.LinearResizeNormalization;
import ge.edu.tsu.hrs.control_panel.server.processor.normalizeddata.normalizationmethod.Normalization;
import ge.edu.tsu.hrs.control_panel.server.util.CharUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NormalizedDataProcessor {

    private final NormalizedDataDAO normalizedDataDAO = new NormalizedDataDAOImpl();

    private final GroupedNormalizedDataDAO groupedNormalizedDataDAO = new GroupedNormalizedDataDAOImpl();

    public void addNormalizedDatum(GroupedNormalizedData groupedNormalizedData, List<String> files) {
        TrainingDataInfo trainingDataInfo = new TrainingDataInfo();
        trainingDataInfo.setWidth(groupedNormalizedData.getWidth());
        trainingDataInfo.setHeight(groupedNormalizedData.getHeight());
        trainingDataInfo.setMinValue(groupedNormalizedData.getMinValue());
        trainingDataInfo.setMaxValue(groupedNormalizedData.getMaxValue());
        trainingDataInfo.setNormalizationType(groupedNormalizedData.getNormalizationType());
        List<NormalizedData> normalizedDatum = new ArrayList<>();
        Date date = new Date();
        for (String directory : files) {
            File file = new File(directory);
            addNormalizedData(trainingDataInfo, file, normalizedDatum);
        }
        groupedNormalizedData.setDuration((new Date().getTime() - date.getTime()));
        int groupedNormalizedDataId = groupedNormalizedDataDAO.addOrGetGroupedNormalizedDataId(groupedNormalizedData);
        groupedNormalizedData.setId(groupedNormalizedDataId);
        normalizedDataDAO.addNormalizedDatum(normalizedDatum, groupedNormalizedData);
    }

    private void addNormalizedData(TrainingDataInfo trainingDataInfo, File file, List<NormalizedData> normalizedDatum) {
        if (!file.isDirectory()) {
            try {
                BufferedImage image = ImageIO.read(file);
                Normalization normalization = null;
                switch (trainingDataInfo.getNormalizationType()) {
                    case DISCRETE_BY_AREA:
                        normalization = new DiscreteByAreaNormalization();
                        break;
                    case DISCRETE_RESIZE:
                        normalization = new DiscreteResizeNormalization();
                        break;
                    case LINEAR_BY_AREA:
                        normalization = new LinearByAreaNormalization();
                        break;
                    case LINEAR_RESIZE:
                        normalization = new LinearResizeNormalization();
                        break;
                }
                NormalizedData normalizedData = normalization.getNormalizedDataFromImage(image, trainingDataInfo, getLetterFromFile(file));
                normalizedDatum.add(normalizedData);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            if (file.listFiles() != null) {
                for (File f : file.listFiles()) {
                    addNormalizedData(trainingDataInfo, f, normalizedDatum);
                }
            }
        }
    }

    private Character getLetterFromFile(File file) {
        String fileName = file.getName();
        String fileNameWithoutExtension = fileName.replaceFirst("[.][^.]+$", "");
        String text = fileNameWithoutExtension.split("_")[1];
        return CharUtil.getCharFromFileName(text);
    }
}
