package ge.edu.tsu.hcrs.neural_network.neural.testresult;

import java.util.List;

public class TestResultUtil {

    public static float getSquaredError(List<Float> outputList, List<Float> activatedList) {
        float squaredError = 0;
        for (int i = 0; i < outputList.size(); i++) {
            squaredError += Math.pow(outputList.get(i) - activatedList.get(i), 2) / 2.0;
        }
        return squaredError;
    }

    public static boolean isCorrect(List<Float> outputList, List<Float> activatedList) {
        return getIndexOfMax(outputList) == getIndexOfMax(activatedList);
    }

    public static float   getDiffBetweenAnsAndBest(List<Float> outputList, List<Float> activatedList) {
        return outputList.get(getIndexOfMax(outputList)) - outputList.get(getIndexOfMax(activatedList));
    }

    // TODO[sg] დასამუშავებელია ფორმულა, რომელიც დათვლის საერთო შეცდომას
    public static float getNormalizedGeneralError(TestResult testResult) {
        return testResult.getSquaredError() * testResult.getPercentageOfCorrects() * testResult.getDiffBetweenAnsAndBest() / testResult.getNumberOfData();
    }

    private static int getIndexOfMax(List<Float> list) {
        int maxIndex = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > list.get(maxIndex)) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
