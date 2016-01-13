package huffmanEncoding;

public class FrequencyTreeNode implements Comparable<FrequencyTreeNode>
{
    public final char letter;
    public final int frequency;
    
    public FrequencyTreeNode left, right;

    public FrequencyTreeNode(char letter, int frequency)
    {
        this.letter = letter;
        this.frequency = frequency;
    }
    
    public int compareTo(FrequencyTreeNode other)
    {
        return frequency - other.frequency;
    }
}
