package partitions;

import partitions.Partitions.Partition;

public class Tester {
	
	public static void main(String[] args) {
		int n = 9;
		int r = 6;
		System.out.println(n + "! = " + Combinatorics.fact(n));
		System.out.println(r + "! = " + Combinatorics.fact(r));
		System.out.println(n + "P" + r + " = " + Combinatorics.perm(n,r));
		System.out.println(n + "C" + r + " = " + Combinatorics.comb(n,r));
		
		int[] arr = {6,4,5,3,7,3,2,2};
		Partition p1 = Partitions.getPartition(arr);
		
		System.out.println(p1);
		System.out.println("Sum = " + p1.sum());
		System.out.println("Count = " + p1.count());
		System.out.println("Max = " + p1.max());
		System.out.println("Min = " + p1.min());
		System.out.println("Order = " + p1.order());
		
		Partition p2 = p1.freq();
		
		System.out.println(p2);
		System.out.println("Sum = " + p2.sum());
		System.out.println("Count = " + p2.count());
		System.out.println("Max = " + p2.max());
		System.out.println("Min = " + p2.min());
		System.out.println("Order = " + p2.order());
		
		String[] strs = {"abc", "123", "def", "xyz", "abc", "xyz"};
		System.out.println(Partitions.getPartitionOf(strs));
		
		System.out.println(p2.append(1));
		
		Partition p3 = p1.forEach(value -> 1<<value);// 2^value
		System.out.println(p3);
		
		System.out.println();
		
		Partitions parts = new Partitions();
		System.out.println("Size = " + parts.size());
		parts.addUpTo(5);
		System.out.println("Size = " + parts.size());
		
		int n1, n2;
		n1 = 3;
		n2 = 10;
		System.out.println("Partitions of " + n1 + " = " + Partitions.numPartitionsOf(n1));
		System.out.println("Size = " + parts.size());
		System.out.println("Partitions of " + n2 + " = " + Partitions.numPartitionsOf(n2));
		System.out.println("Size = " + parts.size());
		
		parts.trimForNextRow();
		System.out.println("Size = " + parts.size());
		System.out.println("Partitions = " + parts.count());
		System.out.println("Partitions of " + (n2+1) + " = " + Partitions.numPartitionsOf(n2+1));
		
		Partition[] pp = Partitions.partitionsOf(n2+2);
		for(Partition p: pp) {
			System.out.print(p);
		}
		System.out.println("\nPartitions of " + (n2+2) + " = " + pp.length);
		
		int n3 = 300;
		System.out.println("Partitions of " + n3 + " = " + Partitions.numPartitionsOf(n3));
		
		System.out.println((n2+2) + "! = " + Partitions.countAll(pp, p -> 
			Combinatorics.fact(p.sum())/p.prod()/p.freq().fact().prod()
		));
		
		System.out.println("5^" + (n2+2) + " = " + Partitions.countAll(pp, p ->
			(Combinatorics.fact(p.sum())/p.fact().prod())
			*(Combinatorics.fact(p.freq().sum())/p.freq().fact().prod())
			*Combinatorics.comb(5, p.count())
		));
		
		//System.out.println(parts)
	}
}
