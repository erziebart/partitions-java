package partitions;

public class Combinatorics {
	public static long fact(int n) {
		if(n < 0) {
			// undefined/error status
			return -1;
		} else {
			int result = 1;
			while(n > 0) {
				result *= n--;
			}
			return result;
		}
	}
	
	public static long perm(int n, int r) {
		if(n < 0) {
			// undefined/error status
			return -1;
		} else if(r < 0 || r > n) {
			return 0;
		} else {
			int result = 1;
			int end = n-r;
			while(n > end) {
				result *= n--;
			}
			return result;
		}
	}
	
	public static long comb(int n, int r) {
		if(n < 0) {
			// undefined/error status
			return -1; 
		} else {
			return perm(n,r)/fact(r);
		}
	}
}
