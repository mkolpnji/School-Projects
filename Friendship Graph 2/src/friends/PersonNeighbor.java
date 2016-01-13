package friends;

public class PersonNeighbor {
	
	int vertexNum;
	PersonNeighbor next;
	
	PersonNeighbor(int vn, PersonNeighbor n){
		vertexNum = vn;
		next = n;
	}
}
