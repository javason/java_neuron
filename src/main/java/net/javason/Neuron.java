package net.javason;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Neuron {

    Random random = new Random();
    private Double bias = (random.nextDouble() * 2) -1; //between -1 and 1
    private Double weight1 = (random.nextDouble() * 2) -1; //between -1 and 1
    private Double weight2 = (random.nextDouble() * 2) -1; //between -1 and 1

    private Double oldBias = (random.nextDouble() * 2) -1; //between -1 and 1
    private Double oldWeight1 = (random.nextDouble() * 2) -1; //between -1 and 1
    private Double oldWeight2 = (random.nextDouble() * 2) -1; //between -1 and 1

    public double compute(double input1, double input2) {
        double preActivation = (this.weight1 * input1) + (this.weight2 * input2) + this.bias;
//        System.out.println("preActivation = " + preActivation);
        double output = Util.sigmoid(preActivation);
        return output;
    }

    /**
     * 무작위로 속성 하나와 -1에서 1 사이의 값을 선택한 다음 속성을 변경
     */
    public void mutate() {
        int propertyToChange = random.nextInt(4); //between 0 and 3
        Double changeFactor = (random.nextDouble() * 2) -1; //between -1 and 1

        if(propertyToChange == 0) {
            this.bias += changeFactor;
        } else if (propertyToChange == 1) {
            this.weight1 += changeFactor;
        } else {
            this.weight2 += changeFactor;
        }
    }

    /**
     * 변경을 이전 값으로 되돌리고
     */
    public void forget() {
        bias = oldBias;
        weight1 = oldWeight1;
        weight2 = oldWeight2;
    }

    /**
     * 새 값을 버퍼로 복사
     */
    public void remember() {
        oldBias = bias;
        oldWeight1 = weight1;
        oldWeight2 = weight2;
    }

    public static void main(String[] args) {
        Neuron neuron = new Neuron();
        double compute = neuron.compute(186, 95);
        System.out.println("compute = " + compute);
    }
    
}

