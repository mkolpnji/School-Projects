package myTreeMap;

import java.util.*;

public class MyTreeMapTester
{
    public static void main(String[] args)
    {
        MyTreeMap<Integer,Integer> theMap = new MyTreeMap<Integer,Integer>();
        Random rand = new Random();
        
        System.out.println("Added to map:");
        for(int i = 0; i <= 25; i++)
        {
            int key = rand.nextInt(100);
            int value = rand.nextInt(100);
            
            System.out.println(key + " " + value);
            theMap.put(key,value);
        }
        
        System.out.println();
        System.out.println("Keys in preorder:");
        System.out.println(theMap.keysPreorder());

        System.out.println();
        System.out.println("Keys in inorder:");
        System.out.println(theMap.keysInorder());
        
        System.out.println();
        System.out.println("Removed from map:");
        
        Set<Integer> theKeys = theMap.keySet();
        
        Iterator<Integer> theKeysItr = theKeys.iterator();
        while(theKeysItr.hasNext())
        {
            int key = theKeysItr.next();
            int value = theMap.remove(key);
            System.out.println(key + " " + value);
        }
        
        System.out.println();
        System.out.println("Map size: " + theMap.size());
        
        System.out.println();
        System.out.println("Retreived from map:");
        
        theKeys = theMap.keySet();
        System.out.println(theKeys);
        theKeysItr = theKeys.iterator();
        while(theKeysItr.hasNext())
        {
            int key = theKeysItr.next();
            int value = theMap.get(key);
            System.out.println(key + " " + value);
        }
        
        System.out.println("End");
    }
}

