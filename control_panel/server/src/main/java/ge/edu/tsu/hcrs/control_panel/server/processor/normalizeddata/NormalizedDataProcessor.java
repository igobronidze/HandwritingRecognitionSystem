package ge.edu.tsu.hcrs.control_panel.server.processor.normalizeddata;

import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.GroupedNormalizedData;
import ge.edu.tsu.hcrs.control_panel.model.network.normalizeddata.NormalizedData;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.GroupedNormalizedDataDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.GroupedNormalizedDataDAOImpl;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.NormalizedDataDAO;
import ge.edu.tsu.hcrs.control_panel.server.dao.normalizeddata.NormalizedDataDAOImpl;
import ge.edu.tsu.hcrs.control_panel.server.processor.normalizeddata.normalizationmethod.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NormalizedDataProcessor {

    private NormalizedDataDAO normalizedDataDAO = new NormalizedDataDAOImpl();

    private GroupedNormalizedDataDAO groupedNormalizedDataDAO = new GroupedNormalizedDataDAOImpl();

    public void addNormalizedDatum(GroupedNormalizedData groupedNormalizedData, List<String> directories) {
        List<NormalizedData> normalizedDatum = new ArrayList<>();
        for (String directory : directories) {
            File file = new File(directory);
            addNormalizedData(groupedNormalizedData, file, normalizedDatum);
        }
        int groupedNormalizedDataId = groupedNormalizedDataDAO.addOrGetGroupedNormalizedDataId(groupedNormalizedData);
        normalizedDataDAO.addNormalizedDatum(normalizedDatum, groupedNormalizedDataId);
    }

    private void addNormalizedData(GroupedNormalizedData groupedNormalizedData, File file, List<NormalizedData> normalizedDatum) {
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                addNormalizedData(groupedNormalizedData, f, normalizedDatum);
            } else {
                try {
                    BufferedImage image = ImageIO.read(f);
                    NormalizationMethod normalizationMethod = null;
                    switch (groupedNormalizedData.getNormalizationType()) {
                        case DISCRETE_BY_AREA:
                            normalizationMethod = new DiscreteByAreaNormalization();
                            break;
                        case DISCRETE_RESIZE:
                            normalizationMethod = new DiscreteResizeNormalization();
                            break;
                        case LINEAR_BY_AREA:
                            normalizationMethod = new LinearByAreaNormalization();
                            break;
                        case LINEAR_RESIZE:
                            normalizationMethod = new LinearResizeNormalization();
                            break;
                    }
                    NormalizedData normalizedData = normalizationMethod.getNormalizedDataFromImage(image, groupedNormalizedData, getLetterFromFile(f));
                    normalizedDatum.add(normalizedData);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    private Character getLetterFromFile(File file) {
        String fileName = file.getName();
        String fileNameWithoutExtension = fileName.replaceFirst("[.][^.]+$", "");
        return fileNameWithoutExtension.split("_")[1].charAt(0);
    }
}