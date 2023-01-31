package net.javason;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Network {
    List<Neuron> neurons = Arrays.asList(
            new Neuron(), new Neuron(), new Neuron(),   //input nodes (0,1,2)
            new Neuron(), new Neuron(),                 //hidden nodes (3,4)
            new Neuron()                                //output node (5)
    );
    
    public Double predict(Integer input1, Integer input2) {
        return neurons.get(5).compute(
                neurons.get(4).compute(
                        neurons.get(2).compute(input1, input2),
                        neurons.get(1).compute(input1, input2)
                ),
                neurons.get(3).compute(
                        neurons.get(1).compute(input1, input2),
                        neurons.get(0).compute(input1, input2)
                )
        );
    }

    /**
     * train() 메서드는 인수의 data 및 answers List에 대해 1,000번 반복된다.
     * 같은 크기의 학습 집합이며 data에는 입력 값이, answers에는 알려진 정답이 저장된다.
     * 메서드는 이 학습 집합에 대해 반복 실행되면서 알려진 올바른 대답과 비교해 네트워크가 얼마나 잘 추정했는지에 대한 값을 구한다.
     * 그런 다음 임의의 뉴런을 변이시키고, 새 테스트가 더 나은 예측이었음이 확인되면 변경을 유지한다.
     *
     * 각 학습 라운드(에포크(epoch)라고 함)
     * @param data
     * @param answers
     */
    public void train(List<List<Integer>> data, List<Double> answers, int epochCount) {
        Double bestEpochLoss = null;
        for (int epoch = 0; epoch < epochCount; epoch++) {
            //adapt neuron
            Neuron epochNeuron = neurons.get(epoch % 6);
            epochNeuron.mutate(); // this.learnFactor ????

            List<Double> predictions = new ArrayList<>();
            for (int i = 0; i< data.size(); i++) {
                predictions.add(i, this.predict(
                                            data.get(i).get(0),
                                            data.get(i).get(1)
                    )
                );
            }

            Double thisEpochLoss = Util.meanSquareLoss(answers, predictions);

            if(bestEpochLoss == null) {
                bestEpochLoss = thisEpochLoss;
                epochNeuron.remember();
            } else {
                if(thisEpochLoss < bestEpochLoss) {
                    bestEpochLoss = thisEpochLoss;
                    epochNeuron.remember();
                } else {
                    epochNeuron.forget();
                }
            }

            if( epoch % 10 == 0) {
                //반복될수록 서서히 하강하는 손실(우측으로부터의 오차 괴리)을 보여준다. 즉, 정확한 예측에 가까워지고 있다
                System.out.println(String.format("Epoch: %s | bestEpochLoss: %.15f | thisEpochLoss: %.15f", epoch, bestEpochLoss, thisEpochLoss));
            }

        }
    }

    public static void main(String[] args) {
        //TODO : 정답셋
//        학습 데이터는 2차원 정수 집합 목록(체중과 신장이라고 생각하면 됨)과 대답 목록(1.0이 여성, 0.0이 남성)이다.
        List<List<Integer>> data = new ArrayList<>();
        data.add(Arrays.asList(115,  66));
        data.add(Arrays.asList(175,  78));
        data.add(Arrays.asList(205,  72));
        data.add(Arrays.asList(120,  67));
        List<Double> answers = Arrays.asList(1.0, 0.0, 0.0, 1.0);

        Network network = new Network();

        //TODO : 학습 (train 의 epoch 를 1000에서 10000 으로 올리면 정확도가 엄청 올라간다)
        network.train(data, answers, 1000);

        //TODO : 예측
        /*
        대부분의 값 쌍(벡터)에서 네트워크가 꽤 좋은 결과를 도출했음을 알 수 있다.
        여성 데이터 집합에 대해 1에 상당히 근접한 약 .907의 추정치를 제공한다.
        두 남성 수치는 .027과 .030으로 0에 가깝다.
        이상치인 남성 데이터 집합(130, 67)은 여성으로 볼 수 있지만, 확신도는 상대적으로 낮은 .900이다.
         */
        System.out.println("");
        System.out.println(">>>> 0.0이 남성, 1.0이 여성  <<<<<");
        System.out.println(String.format("  male, 167, 73: %.10f", network.predict(167, 73)));
        System.out.println(String.format("  male, 143, 67: %.10f", network.predict(143, 67)));
        System.out.println(String.format("  male, 130, 66: %.10f", network.predict(130, 66)));
        System.out.println(String.format("female, 105, 67: %.10f", network.predict(105, 67)));
        System.out.println(String.format("female, 120, 72: %.10f", network.predict(120, 72)));
    }
    
}
