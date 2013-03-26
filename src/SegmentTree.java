// @author			Le Hong Trieu
// @date			26 March 2013

public class SegmentTree
{
	private class Node
	{
		public int value;
		public int add;
		public int b;
		public int e;
		public String toString()
		{
			return "(Value: " + value + ", add: " + add + " b: " + b + " e: " + e + ")";
		}
	}	
	
	protected Node[] M;
	protected int[] A;
	// @discussion		By default, the range which the segment tree covers is [1, sizeA].		
	public SegmentTree(int[] arr)
	{
		A = arr;
		int sizeM = (int)(2 * Math.pow(2, Math.ceil(Math.log(A.length) / Math.log(2))));
		M = new Node[sizeM];
		initialize(0, 1, A.length);
	}
		
	// Time complexity O(M.length)
	public void initialize(int nodeId, int b, int e)
	{
		M[nodeId] = new Node();
		M[nodeId].b = b;
		M[nodeId].e = e;
		if(b == e)
		{
			// We minus 1 from b before using it to index into A because the covered range is [1, A.length]. 
			M[nodeId].value = A[b - 1];
		}
		else
		{
		    initialize(leftChild(nodeId), b, (b + e) / 2);
		    initialize(rightChild(nodeId), (b + e) / 2 + 1, e);
		  
		    if(M[leftChild(nodeId)].value <= M[rightChild(nodeId)].value)
		    	M[nodeId].value = M[rightChild(nodeId)].value;
		    else
		        M[nodeId].value = M[leftChild(nodeId)].value; 
		}
	}
		
	public void updateRange(int u, int v, int m)
	{
		try 
		{
			updateRange(0, u, v, m);
		} catch (Exception e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
		
	protected void updateRange(int nodeId, int u, int v, int m) throws Exception
	{
		Node node = M[nodeId];
		// The updating range covers the node's interval.
		if(node.b >= u && node.e <= v)
		{
			updateWithLazyPropagation(nodeId, m);
		}
		// The updating range disjoins from the node's interval. 
		else if(u > node.e || v < node.b)
		{}
		else
		{
			split(nodeId);
			updateRange(leftChild(nodeId), u, v, m);
			updateRange(rightChild(nodeId), u, v, m);
			merge(nodeId);
		}
	}
		
	public int queryRange(int u, int v)
	{
		try {
			return queryRange(0, u, v);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Integer.MIN_VALUE;
		}
	}
		
	protected int queryRange(int nodeId, int u, int v) throws Exception
	{
		Node node = M[nodeId];
		// The updating range covers the node's interval.
		if(node.b >= u && node.e <= v)
		{
			return node.value;
		}
		// The updating range disjoins from the node's interval. 
		else if(u > node.e || v < node.b)
		{
			return Integer.MIN_VALUE;
		}
		else
		{
			split(nodeId);
			int l = leftChild(nodeId);
			int r = rightChild(nodeId);
			int leftValue = queryRange(l, u, v);
			int rightValue = queryRange(r, u, v);
			return Math.max(leftValue, rightValue);
		}
	}
		
	protected void updateWithLazyPropagation(int nodeId, int m)
	{
		M[nodeId].add += m;
		M[nodeId].value += m;
	}
		
	protected void split(int nodeId) throws Exception
	{
		int l = leftChild(nodeId);
		int r = rightChild(nodeId);
		if(l >= M.length || r >= M.length)
			throw new Exception("Leaf node cannot be splitted!");
					
		updateWithLazyPropagation(l, M[nodeId].add);
		updateWithLazyPropagation(r, M[nodeId].add);
		M[nodeId].add = 0;
	}
		
	protected void merge(int nodeId) throws Exception
	{
		if(M[nodeId].add != 0) throw new Exception("Merge when the node's 'add' field is not zero!");
		M[nodeId].value = Math.max(M[leftChild(nodeId)].value, M[rightChild(nodeId)].value);
	}
		
	protected int leftChild(int nodeId)
	{
		return nodeId * 2 + 1;
	}
		
	protected int rightChild(int nodeId)
	{
		return (nodeId + 1) * 2;
	}
		
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		int cnt = 0;
		int e = 1;
		for(int i = 0; i < M.length; i++)
		{
			str.append(M[i] + " ");
			cnt++;
			if(cnt == e)
			{
				str.append("\n");
				cnt = 0;
				e *= 2;
			}
		}
		return str.toString();
	}
}
