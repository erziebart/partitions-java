package partitions;

// calculates number of ways to decompose integers
// subject to constraints on group size and numbers of a certain group size
// more general notion of partition count
public class Decomps {
	
	// same as p(n) - "partitions of n"
	public static long count(int n) {
		// O(n^2) algorithm derived from pentagonal number theorem
		long[] coeffs = new long[n+1];
		coeffs[0] = 1;
		
		for(int k = 1; k <= n; k++) {
			for(int i = 0; i < coeffs.length-k; i++) {
				coeffs[i+k] += coeffs[i];
			}
		}
		
		return coeffs[n];
	}
	
	public static long countMinGroupSize(int n, int minGroupSize) {
		if(minGroupSize < 1) {
			return count(n);
		}
		
		long[] coeffs = new long[n+1];
		coeffs[0] = 1;
		
		for(int k = minGroupSize; k <= n; k++) {
			for(int i = 0; i < coeffs.length-k; i++) {
				coeffs[i+k] += coeffs[i];
			}
		}
		
		return coeffs[n];
	}
	
	public static long countMaxGroupSize(int n, int maxGroupSize) {
		if(maxGroupSize > n) {
			return count(n);
		}
		
		long[] coeffs = new long[n+1];
		coeffs[0] = 1;
		
		for(int k = 1; k <= maxGroupSize; k++) {
			for(int i = 0; i < coeffs.length-k; i++) {
				coeffs[i+k] += coeffs[i];
			}
		}
		
		return coeffs[n];
	}
	
	public static long countMinAndMaxGroupSize(int n, int minGroupSize, int maxGroupSize) {
		if(minGroupSize < 1) {
			return countMaxGroupSize(n, maxGroupSize);
		}
		
		if(maxGroupSize > n) {
			return countMinGroupSize(n, minGroupSize);
		}
		
		long[] coeffs = new long[n+1];
		coeffs[0] = 1;
		
		for(int k = minGroupSize; k <= maxGroupSize; k++) {
			for(int i = 0; i < coeffs.length-k; i++) {
				coeffs[i+k] += coeffs[i];
			}
		}
		
		return coeffs[n];
	}
	
	public static long countSelectK(int n, int[] ks) {
		long[] coeffs = new long[n+1];
		coeffs[0] = 1;
		
		for(int k: ks) {
			if(k > 0) {
				for(int i = 0; i < coeffs.length-k; i++) {
					coeffs[i+k] += coeffs[i];
				}
			}
		}
		
		return coeffs[n];
	}
	
	public static long countEvenGroups(int n) {
		long[] coeffs = new long[n+1];
		coeffs[0] = 1;
		
		for(int k = 2; k <= n; k+=2) {
			for(int i = 0; i < coeffs.length-k; i++) {
				coeffs[i+k] += coeffs[i];
			}
		}
		
		return coeffs[n];
	}
	
	public static long countOddGroups(int n) {
		long[] coeffs = new long[n+1];
		coeffs[0] = 1;
		
		for(int k = 1; k <= n; k+=2) {
			for(int i = 0; i < coeffs.length-k; i++) {
				coeffs[i+k] += coeffs[i];
			}
		}
		
		return coeffs[n];
	}
	
	public static long countUnequalGroups(int n) {
		long[] coeffs = new long[n+1];
		coeffs[0] = 1;
		
		int start = 0;
		for(int k = 1; k <= n; k++) {
			start += k;
			for(int i = Math.min(start, n)-k; i >= 0; i--) {
				coeffs[i+k] += coeffs[i];
			}
		}
		
		return coeffs[n];
	}
	
	public static long countMEachSize(int n, int m) {
		long[] coeffs = new long[n+1];
		coeffs[0] = 1;
		
		if(n%m != 0) {
			// if n is not divisible by m, the answer is 0
			return 0;
		} else {
			return countUnequalGroups(n/m);
		}
	}
	
	public static long countUnequalOddGroups(int n) {
		// also the number of partitions of n which are self-conjugates
		long[] coeffs = new long[n+1];
		coeffs[0] = 1;
		
		int start = 0;
		for(int k = 1; k <= n; k+=2) {
			start += k;
			for(int i = Math.min(start, n)-k; i >= 0; i--) {
				coeffs[i+k] += coeffs[i];
			}
		}
		
		return coeffs[n];
	}
	
	public static long countMultiplesOf(int n, int factor) {
		long[] coeffs = new long[n+1];
		coeffs[0] = 1;
		
		for(int k = factor; k <= n; k+=factor) {
			for(int i = 0; i < coeffs.length-k; i++) {
				coeffs[i+k] += coeffs[i];
			}
		}
		
		return coeffs[n];
	}
	
	public static long countNotMultiplesOf(int n, int factor) {
		long[] coeffs = new long[n+1];
		coeffs[0] = 1;
		
		for(int k = 1; k <= n; k++) {
			if(k%factor != 0) {
				for(int i = 0; i < coeffs.length-k; i++) {
					coeffs[i+k] += coeffs[i];
				}
			}
		}
		
		return coeffs[n];
	}
}
