package partitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

/*
 * Outline:
 * 
 * 
 */
public class Partitions {
	
	// stored partition data structure
	private ArrayList<ArrayList<Partition>> partitions;
	
	// the simplest partitioning
	private static final Integer[] BASIS = {1};
	
	
	public Partitions() {
		partitions = new ArrayList<ArrayList<Partition>>();
		
		// add the Zero Partition
		ArrayList<Partition> zeroRow = new ArrayList<Partition>();
		zeroRow.add(new Partition(new int[0]));
		partitions.add(zeroRow);
	}
	
	
	/* Adding rows to the data structure */
	public void addRow() {
		addRow(len());
	}
	
	private void addRow(int n) {
		ArrayList<Partition> row = new ArrayList<Partition>();
		
		// add the single Partition
		Integer[] single = new Integer[1];
		single[0] = n;
		row.add(new Partition(single, false, false));
		
		// loop through last half of partitions
		for(int i = (n+1)/2; i < n; i++) {
			ArrayList<Partition> current = partitions.get(i);
			int diff = n - i;
			
			// loop through Partitions in this row
			Iterator<Partition> it = current.iterator();
			Partition p;
			while(it.hasNext() && (p = it.next()).min() >= diff) {
				// add new if the partition has min >= diff
				row.add(p.append(diff));
			}
		}
		partitions.add(n,row);
	}
	
	public void addUpTo(int n) {
		int next;
		while((next = len()) <= n) {
			addRow(next);
		}
	}
	
	
	/* Trimming */
	public void trimRow(int n, int minVal) {
		if(n < 1) {return;}
		ArrayList<Partition> row = partitions.get(n);
		
		// iterate over the row until min gets too high
		int i = row.size()-1;
		while(i >= 0 && row.get(i).min() < minVal) {
			row.remove(i--);
		}
	}
	
	public void trimAllRows(int minVal) {
		for(int n = 1; n < len(); n++) {
			trimRow(n, minVal);
		}
	}
	
	public void trimForNextRow() {
		int last = len()-1;
		for(int n = 1; n < last; n++) {
			trimRow(n, last-n+1);
		}
	}
	
	
	/* Information about the Partition list */
	public long size() {
		long result = 0;
		for(ArrayList<Partition> ap: partitions) {
			for(Partition p: ap) {
				result += p.count();
			}
		}
		return result;
	}
	
	public long count() {
		long result = 0;
		for(ArrayList<Partition> ap: partitions) {
			result += ap.size();
		}
		return result;
	}
	
	public int len() {
		return partitions.size();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(ArrayList<Partition> ap: partitions) {
			sb.append("[ ");
			for(Partition p: ap) {
				sb.append(p.toString() + " ");
			}
			sb.append("]\n");
		}
		return sb.toString();
	}
	
	
	/* static methods to compute Partitions */
	public static Partition[] partitionsOf(int n) {
		if(n < 1) {
			return null;
		}
		
		Partitions parts = new Partitions();
		while(parts.len() <= n) {
			parts.addRow();
			parts.trimForNextRow();
		}
		ArrayList<Partition> ap = parts.partitions.get(n);
		return ap.toArray(new Partition[ap.size()]);
	}
	// in practice, this method can go up to 73 before java runs out of heap space
	
	public static long numPartitionsOf(int n) {
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
	// goes up to 405 before long overflows
	
	
	/* Partition from frequency */
	// create a Partition using the frequency of an array of chars
	// runs in O(n^2) time, where n is length of arr
	public static Partition getPartitionOf(char[] arr) {
		if(arr == null) {
			return null;
		}
		
		if(arr.length == 0) {
			// zero partition
			return new Partitions().new Partition(new int[0]);
		}
		
		int[] values = new int[arr.length];
		ArrayList<Integer> refs = new ArrayList<Integer>(arr.length);
		
		for(int i = 0; i < arr.length; i++) {
			// try to find matches already recorded
			int j = 0;
			while(j < refs.size() && arr[i] != arr[refs.get(j)])
				j++;
			
			if(j == refs.size()) // none found
				refs.add(i);
			values[j]++;
		}
		
		// copy trimmed values
		int[] result = new int[refs.size()];
		for(int i = 0; i < result.length; i++) {
			result[i] = values[i];
		}
		
		Partition p = new Partitions().new Partition(result);
		return p;
	}
	
	// create a Partition using the frequency of an array of ints
	// runs in O(n^2) time, where n is length of arr
	public static Partition getPartitionOf(int[] arr) {
		if(arr == null) {
			return null;
		}

		if(arr.length == 0) {
			// zero partition
			return new Partitions().new Partition(new int[0]);
		}
		
		int[] values = new int[arr.length];
		ArrayList<Integer> refs = new ArrayList<Integer>(arr.length);
		
		for(int i = 0; i < arr.length; i++) {
			// try to find matches already recorded
			int j = 0;
			while(j < refs.size() && arr[i] != arr[refs.get(j)])
				j++;
			
			if(j == refs.size()) // none found
				refs.add(i);
			values[j]++;
		}
		
		// copy trimmed values
		int[] result = new int[refs.size()];
		for(int i = 0; i < result.length; i++) {
			result[i] = values[i];
		}
		
		Partition p = new Partitions().new Partition(result);
		return p;
	}
	
	// create a Partition using the frequency of an array of doubles
	// runs in O(n^2) time, where n is length of arr
	public static Partition getPartitionOf(double[] arr) {
		if(arr == null) {
			return null;
		}
		
		if(arr.length == 0) {
			// zero partition
			return new Partitions().new Partition(new int[0]);
		}
		
		int[] values = new int[arr.length];
		ArrayList<Integer> refs = new ArrayList<Integer>(arr.length);
		
		for(int i = 0; i < arr.length; i++) {
			// try to find matches already recorded
			int j = 0;
			while(j < refs.size() && arr[i] != arr[refs.get(j)])
				j++;
			
			if(j == refs.size()) // none found
				refs.add(i);
			values[j]++;
		}
		
		// copy trimmed values
		int[] result = new int[refs.size()];
		for(int i = 0; i < result.length; i++) {
			result[i] = values[i];
		}
		
		Partition p = new Partitions().new Partition(result);
		return p;
	}
	
	// create a Partition using the frequency of an array of objects
	// runs in O(n^2) time, where n is length of arr
	// uses behavior of .equals() method of T
	public static <T> Partition getPartitionOf(T[] arr) {
		if(arr == null) {
			return null;
		}

		if(arr.length == 0) {
			// zero partition
			return new Partitions().new Partition(new int[0]);
		}
		
		int[] values = new int[arr.length];
		ArrayList<Integer> refs = new ArrayList<Integer>(arr.length);
		
		for(int i = 0; i < arr.length; i++) {
			// try to find matches already recorded
			int j = 0;
			while(j < refs.size() && !arr[i].equals(arr[refs.get(j)]))
				j++;
			
			if(j == refs.size()) // none found
				refs.add(i);
			values[j]++;
		}
		
		// copy trimmed values
		int[] result = new int[refs.size()];
		for(int i = 0; i < result.length; i++) {
			result[i] = values[i];
		}
		
		Partition p = new Partitions().new Partition(result);
		return p;
	}
	
	
	// allows access to private constructor of Partition without risk
	// of returning an invalid Partition
	public static Partition getPartition(int[] arr) {
		if(arr != null) {
			Partition p = new Partitions().new Partition(arr);
			if(p.isValid()) {
				return p;
			}
		}
		return null;
	}
	
	public class Partition implements Iterable<Integer> {
		private Integer[] arr;
		
		/* construction and validity check */
		private Partition(int[] arr) {
			// copy values
			this.arr = new Integer[arr.length];
			for(int i = 0; i < arr.length; i++) {
				this.arr[i] = arr[i];
			}
			
			// sort in descending order
			Arrays.sort(this.arr, Collections.reverseOrder());
		}
		
		private Partition(Integer[] arr, boolean copy, boolean sort) {
			if(copy) {
				// copy values
				this.arr = new Integer[arr.length];
				for(int i = 0; i < arr.length; i++) {
					this.arr[i] = arr[i];
				}
			} else {
				this.arr = arr;
			}
			
			if(sort) {
				// sort in descending order
				Arrays.sort(this.arr, Collections.reverseOrder());
			}
		}
		
		private boolean isValid() {
			// invalid if non-positive values
			for(int i: arr) {
				if(i <= 0) {
					return false;
				}
			}
			
			return true;
		}
		
		private boolean isZero() {
			return (count() == 0);
		}
		
		
		/* array attributes */
		public int sum() {
			int sum = 0;
			for(int i : arr) {
				sum += i;
			}
			return sum;
		}
		
		public int prod() throws ZeroPartitionException {
			if(isZero()) {
				throw new ZeroPartitionException();
			}
			
			int prod = 1;
			for(int i: arr) {
				prod *= i;
			}
			return prod;
		}

		public int count() {
			return arr.length;
		}
		
		public int max() throws ZeroPartitionException {
			if(isZero()) {
				throw new ZeroPartitionException();
			}
			
			return arr[0];
		}
		
		public int min() throws ZeroPartitionException {
			if(isZero()) {
				throw new ZeroPartitionException();
			}
			
			return arr[arr.length-1];
		}
		
		
		/* check for equality */
		@Override
		public boolean equals(Object obj) {
			// check type
			if(!(obj instanceof Partition))
				return false;
			Partition other = (Partition) obj;
			
			// check for same length
			if(this.count() != other.count())
				return false;
			
			// check for the same values
			for(int i = 0; i < this.count(); i++) {
				if(this.arr[i] != other.arr[i])
					return false;
			}
			
			return true;
		}
		
		
		/* member access and iteration */
		public int intAt(int index) throws ArrayIndexOutOfBoundsException {
			return arr[index];
		}
		
		@Override
		public Iterator<Integer> iterator() {
			return new PartitionIterator();
		}
		
		private class PartitionIterator implements Iterator<Integer> {
			int index = 0;
			
			@Override
			public boolean hasNext() {return (index < arr.length);}

			@Override
			public Integer next() {return arr[index++];}
		}
		
		
		/* printing */
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("{ ");
			for(int i: arr) {
				sb.append(i + " ");
			}
			sb.append("}");
			
			return sb.toString();
		}
		
		
		/* operations that return a new Partition */
		public Partition copy() { // makes a deep copy of the Partition
			return new Partition(this.arr, true, false);
		}
		
		public Partition append(int value) {
			Integer[] result = new Integer[arr.length+1];
			
			int i = 0;
			while(i < arr.length && arr[i] > value) { // copy up to value
				result[i] = arr[i];
				i++;
			}
			
			result[i++] = value; // insert value
			
			while(i < result.length) { // copy rest
				result[i] = arr[i-1];
				i++;
			}
			
			return new Partition(result, false, false);
		}
		
		public Partition fact() {
			Integer[] result = new Integer[arr.length];
			
			for(int i = 0; i < arr.length; i++) {
				result[i] = (int) Combinatorics.fact(arr[i]);
			}
			
			return new Partition(result, false, false);
		}
		
		public Partition multiplyPositive(int factor) {
			if(factor < 1) {
				return null;
			}
			
			Integer[] result = new Integer[arr.length];
			
			for(int i = 0; i < arr.length; i++) {
				result[i] = factor*arr[i];
			}
			
			return new Partition(result, false, false);
		}
		
		public Partition addPositive(int addend) {
			if(addend < 0) {
				return null;
			}
			
			Integer[] result = new Integer[arr.length];
			
			for(int i = 0; i < arr.length; i++) {
				result[i] = addend + arr[i];
			}
			
			return new Partition(result, false, false);
		}
		
		public <F extends ValueWiseOp> Partition forEach(F f) {
			Integer[] result = new Integer[arr.length];
			
			for(int i = 0; i < arr.length; i++) {
				result[i] = f.operation(arr[i]);
			}
			
			Partition p = new Partition(result, false, true);
			return p.isValid() ? p : null;
		}
		
		
		// gives the frequency of integers appearing in the Partition
		// in the form of another Partition
		public Partition freq() {
			if(isZero()) {
				// frequency of zero partition is itself
				return new Partition(new int[0]);
			}
			
			int[] counts = new int[arr.length];
			
			int cur = 0;
			int last = arr[0];
			for(int i: arr) {
				if(i != last)
					cur++;
				counts[cur]++;
				last = i;
			}
			
			int[] values = new int[++cur];
			for(int i = 0; i < values.length; i++) {
				values[i] = counts[i]; 
			}
			
			return new Partition(values);
		}
		
		// the order of a partition is the number of times the freq
		// can be taken before reaching the BASIS array
		public int order() throws ZeroPartitionException {
			if(isZero()) {
				throw new ZeroPartitionException();
			}
			
			int order = 0;
			Partition basis = new Partition(BASIS, false, false);
			Partition freq = this;
			
			while(!freq.equals(basis)) {
				freq = freq.freq();
				order++;
			}
			
			return order;
		}
	}
	
	
	public static <F extends PartitionCount> long countAll(Partition[] arr, F f) {
		long result = 0;
		for(Partition p: arr) {
			result += f.count(p);
		}
		return result;
	}

}
