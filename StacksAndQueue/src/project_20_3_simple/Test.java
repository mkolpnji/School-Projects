package project_20_3_simple;

public class Test
{
    public final String input;
    public final Double result;
    
    public Test(String input, Double result)
    {
        this.input = input;
        this.result = result;
    }
    
    public String toString()
    {
        return input + " = " + result;
    }
}