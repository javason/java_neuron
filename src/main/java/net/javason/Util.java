package net.javason;

import java.util.List;

public class Util {

    /**
     * 값을 -1 ~ 1 범위로 압축하는 시그모이드(Sigmoid) 활성화 함수
     */
    public static double sigmoid(double in) {
        return 1 / (1 + Math.exp(-in));
    }

    /**
     * 신경망에서 결과 집합을 테스트하기 위한 일반적인 방법인 평균 제곱 오차(MSE) 공식을 사용해서 결과를 확인할 수 있다.
     * @param correctAnswers
     * @param predictedAnswers
     * @return
     */
    public static Double meanSquareLoss(List<Double> correctAnswers, List<Double> predictedAnswers) {
        double sumSquare = 0;
        for( int i = 0; i< correctAnswers.size(); i++) {
            double error = correctAnswers.get(i) - predictedAnswers.get(i);
            sumSquare += (error * error);
        }
        return sumSquare / (correctAnswers.size());
    }
}
