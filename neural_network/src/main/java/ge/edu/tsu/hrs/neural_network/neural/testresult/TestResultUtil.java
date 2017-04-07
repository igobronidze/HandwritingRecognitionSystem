package ge.edu.tsu.hrs.neural_network.neural.testresult;

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
        return getIndexOfMax(outputList, -1) == getIndexOfMax(activatedList, -1);
    }

    public static float getDiffBetweenAnsAndBest(List<Float> outputList, List<Float> activatedList) {
        int ansIndex = getIndexOfMax(outputList, -1);
        return activatedList.get(getIndexOfMax(activatedList, ansIndex)) - activatedList.get(ansIndex);
    }

    public static float getNormalizedGeneralError(TestResult testResult) {
        return testResult.getSquaredError() / testResult.getNumberOfData() *
                testResult.getPercentageOfIncorrect() *
                (testResult.getDiffBetweenAnsAndBest() + testResult.getNumberOfData()) / testResult.getNumberOfData();
    }

    private static int getIndexOfMax(List<Float> list, int except) {
        int maxIndex = except == 0 ? 1 : 0;
        for (int i = 0; i < list.size(); i++) {
            if (i == except) {
                continue;
            }
            if (list.get(i) > list.get(maxIndex)) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
