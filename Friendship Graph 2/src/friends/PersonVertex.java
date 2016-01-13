package friends;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


public class PersonVertex {

	String name;
	String school;
	boolean isStudent;
	ArrayList<PersonVertex> friends = new ArrayList<PersonVertex>();
	int connections;
	PersonNeighbor adjNeighbor;
	
	
	// this is for students
	public PersonVertex (String n, String s, boolean isStudent, int connections){
		
		this.name = n;
		this.school = s;
		this.isStudent = isStudent;
		this.connections = connections;
		
	}
	
	// this is for non-students
	public PersonVertex (String n){
		this.name = n;
		this.school=null;
		this.isStudent = false;
		this.connections =0;
		
	}
	
	// this function is to add a friend
	public void addFriend (PersonVertex f){
		
		if (f==this){
			return;
		}
		if (friends.contains(f)){
			return;
		}
		else {
			friends.add(f);
		}
		
	}
	
	
}
