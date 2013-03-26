// @author			Le Hong Trieu
// @date			26 March 2013
// @discussion		Bit's valid value starts from 1. Therefore, the size of bit is the size of the original array + 1.
public class BinaryIndexedTree 
{
	protected int[] fw;
	
	public BinaryIndexedTree (int arrSize)
	{
		fw = new int[arrSize + 1];
	}
	
	public void update(int idx)
    {
        while (idx < fw.length)
        {
            fw[idx] += 1;
            idx = idx + (idx & -idx);
        }
    }
    
    // Returns the cumulative frequency of index idx.
    public int read(int idx)
    {
        int sum = 0;
        while(idx > 0)
        {
            sum += fw[idx];
            idx -= (idx & -idx);
        }
        return sum;
    }
}
