package learn_pinch_2d;


public class Test {
	public static void main(String[] args) {
		int[][][][] a = new int[10][10][10][10];
		int index = 0;
		int state = 9999;
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				for (int z = 0; z < 10; z++) {
					for (int n = 0; n < 10; n++) {
						a[x][y][z][n] = index;
						if (index == state) {
							System.out.println(x);
							System.out.println(y);
							System.out.println(z);
							System.out.println(n);
						}
						index++;	
					}
				}
			}
		}

		System.out.println();
		int x = state / (1000);
		int y = (state - (1000 * x)) / 100;
		int z = (state - (1000 * x) - (100 * y)) / 10;
		int n = (state - (1000 * x) - (100 * y) - (10 * z));
		System.out.println(x);
		System.out.println(y);
		System.out.println(z);
		System.out.println(n);
		System.out.println(a[x][y][z][n]);
	}
}
