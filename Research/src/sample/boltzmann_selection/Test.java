package sample.boltzmann_selection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
	public static void main(String[] args) {
		List<Double> qValueList = new ArrayList<>();
		qValueList.add(1/3.0);
		qValueList.add(1/2.0);
		qValueList.add(1/6.0);

		//		qValueList.add(0.1);
		//		qValueList.add(0.0999999999999);
		//		qValueList.add(0.0000000000001);
		//		qValueList.add(0.2);
		//		qValueList.add(0.3);
		//		qValueList.add(0.05);
		//		qValueList.add(0.05);
		//		qValueList.add(0.02);
		//		qValueList.add(0.08);
		//		qValueList.add(0.1);

		Map<Integer, Integer> map = new HashMap<>();
		double subtractValue = 1 /  1000000.0;
		for (int index = 0; index < 1000000; index++) {
			//List<Double> hoge = calculateProbability(qValueList, 1 - subtractValue * index);
			//System.out.println(hoge);
			if (index % 100000 == 0) {
				System.out.println("hoge = " + index / 100000);
			}
			int hoge = BoltzMannSelection.select(qValueList, 1.00001 - subtractValue * index);
			map.put(hoge, map.get(hoge) == null ? 1 : map.get(hoge) + 1);
		}
		System.out.println(map.entrySet());
	}
}