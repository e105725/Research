package sample.boltzmann_selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class BoltzMannSelection {
	//どの行動を行うかを確率的に選ぶ際に利用する
		private static final Random random = new Random(System.currentTimeMillis());
	
	//Q値のListとTの値を引数として、List内の何番目の行動を行うかを返す。
	public static final int select(List<Double> qValueList, double t) {
		List<Double> probabilityList = calculateProbability(qValueList, t);

		double randomValue = random.nextDouble();
		double probabilitySum = 0.0;
		for (int index = 0; index < probabilityList.size(); index++) {
			double probability = probabilityList.get(index);

			//Infinityが混ざってきたら、q値が一番高い行動を返すようにする
			if (!Double.isFinite(probability)) {
				int hoge = maxQValueIndex(qValueList);
				System.out.println(hoge);
				return hoge;
			}
			probabilitySum += probability;
			if (randomValue < probabilitySum) {
				return index;
			}
		}
		throw new UnsupportedOperationException();
	}
	
	private static final int maxQValueIndex(List<Double> qValueList) {
		double max = -Double.MAX_VALUE;
		int maxIndex = -1;
		for (int index = 0; index < qValueList.size(); index++) {
			double qValue = qValueList.get(index);
			
			if (max < qValue) {
				max = qValue;
				maxIndex = index;
				continue;
			}
			if (max == qValue) {
				//最大q値が等しい場合、どっちかを設定
				max = qValue;
				maxIndex = random.nextInt(2) == 1 ? maxIndex : index;
				continue;
			}
		}
		return maxIndex;
	}
	
	//Q値のListとTの値から、各行動の確率を返す
	private static final List<Double> calculateProbability(List<Double> qValueList, double t) {
		if (qValueList == null || qValueList.isEmpty()) {
			System.err.println(BoltzMannSelection.class);
			System.err.println("qValueのリストが空です。行動選択できません。");
			throw new UnsupportedOperationException();
		}
		List<Double> probabilityList = new ArrayList<>();
		double denominator = calculateDenominator(qValueList, t);
		for (double qValue : qValueList) {
			double numerator = Math.exp(qValue / t);
			double probability = numerator / denominator;
			probabilityList.add(probability);
		}
		return probabilityList;
	}
	
	//ボルツマン選択の分母の計算
	private static final double calculateDenominator(List<Double> qValueList, double t) {
		double denominator = 0;
		for (double qValue : qValueList) {
			double value = Math.exp(qValue / t);
			if (Double.isInfinite(value)) {
				value = Double.MIN_VALUE;
			}
			denominator += value;
		}
		return denominator;
	}
}