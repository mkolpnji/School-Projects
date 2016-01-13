package friends;

/**
 * Jay Hung
 * Sava Radovic
 * CS 112
 */

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Friends {

	public static ArrayList<PersonVertex> people = new ArrayList<PersonVertex>();
	public static HashMap<String, PersonVertex> peopleLookup = new HashMap<String, PersonVertex>();
	public static HashMap<String, Integer> vertexLookup = new HashMap<String, Integer>();

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		System.out.println("Give the input graph file.");

		String input = sc.nextLine();

		buildGraph(input);

		// printPeopleList();

		String options = "";

		do {

			System.out.println("What do you want to do?");
			System.out.println("(A). Shortest Intro Chain");
			System.out.println("(B). Cliques at School");
			System.out.println("(C). Connectors");
			System.out.println("(D). Quit");

			options = sc.nextLine().toUpperCase();

			System.out.println("Your choice is : " + options);
			System.out.println();

			if (options.equals("A")) { // Shortest Intro Chain

				System.out.println("Name of the person 1: ");

				String sp1 = sc.nextLine();

				System.out.println("Name of the person 2: ");

				String sp2 = sc.nextLine();

				PersonVertex p1 = peopleLookup.get(sp1.toLowerCase());
				PersonVertex p2 = peopleLookup.get(sp2.toLowerCase());

				if (p1 == null || p2 == null) {

					System.out.println("One of these 2 people is not valid.");

				} else {

					System.out.println("Looking for shortest path from " + p1.name + " to " + p2.name + ":");

					// ArrayList<Person> path = p1.findShortestPathTo(p2);
					String path = findShortestPathTo(p1, p2);
					System.out.println(path);

					if (path.equals("")) {
						System.out.println("There is no path between these people.");
					}

				}

				// shortestIntroChain(graph);

			}

			else if (options.equals("B")) { // Cliques at School
				System.out.println("Enter school name: ");
				String schoolName = sc.nextLine();
				cliquesAtSchool(schoolName);
			}

			else if (options.equals("C")) { // Connectors
				connectorsResult();
			} else if (options.equals("D")) {
			} else {
				System.out.println("That option is not valid");

			}

		} while (!options.equals("D"));
	}

	// this function will accept the file name and build a graph out of it
	// quick way to do vertex number -> person name is
	// people.get(vertexNum).name

	public static void buildGraph(String input) {

		File f = new File(input);

		try {

			Scanner sc = new Scanner(f);

			String sCount = sc.nextLine();

			int count = new Integer(sCount).intValue();

			// System.out.println("Count of people is : " + count);

			// First read in all the people information

			for (int i = 0; i < count; i++) {

				if (sc.hasNextLine()) {

					String line = sc.nextLine();

					// System.out.println("Read from file line " + line);

					String tokens[] = line.split("\\|");

					if (tokens.length == 3) {

						PersonVertex p = new PersonVertex(tokens[0], tokens[2], true, 0);

						people.add(p);
						vertexLookup.put(p.name.toLowerCase(), people.size() - 1);
						peopleLookup.put(tokens[0].toLowerCase(), p);

					}

					if (tokens.length == 2) {

						PersonVertex p = new PersonVertex(tokens[0]);

						people.add(p);
						vertexLookup.put(p.name.toLowerCase(), people.size() - 1);
						peopleLookup.put(tokens[0].toLowerCase(), p);

					}

				}

			}

			// Then read in all the relationships

			while (sc.hasNextLine()) {

				String ln = sc.nextLine();

				// System.out.println ("Line is " + ln);

				String twoFriends[] = ln.split("\\|");

				if (twoFriends.length >= 2) {

					PersonVertex p1 = peopleLookup.get(twoFriends[0].toLowerCase());
					PersonVertex p2 = peopleLookup.get(twoFriends[1].toLowerCase());
					int v1 = vertexLookup.get(twoFriends[0].toLowerCase());
					int v2 = vertexLookup.get(twoFriends[1].toLowerCase());

					if (p1 != null && p2 != null) {

						p1.adjNeighbor = new PersonNeighbor(v2, p1.adjNeighbor);
						p2.adjNeighbor = new PersonNeighbor(v1, p2.adjNeighbor);
						p1.addFriend(p2);
						p2.addFriend(p1);

					}

				}

			}
			System.out.println(people.size());

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	// Function to find shortest path to other person

	// This is classic BFS algorithm - first time we find a node

	// it is the shortest path
	public static String findShortestPathTo(PersonVertex p1, PersonVertex p2) {

		// Each person has a string to keep track of the path how we got there

		HashMap<PersonVertex, String> pathLookup = new HashMap<PersonVertex, String>();

		// this is like a queue to keep track of what nodes need to be visited

		Queue<PersonVertex> toVisit = new LinkedList<PersonVertex>();

		int threshold = people.size() + 1;
		int count = 0;

		// Starting from myself as a Person

		toVisit.offer(p1);

		// System.out.println (toVisit.poll().name);
		// System.exit(0);

		pathLookup.put(p1, p1.name); // path to myself from myself is my
										// name

		// System.out.println(pathLookup.get(p1));
		// System.exit(0);

		// For each node that we need to visit. we take all the friends and put
		// them

		// on the queue. Then we remove ourself from the queue

		// ... also each node contains the path to how we got there, path is
		// constructed

		// from the initial friends path and add "-" and the name

		while (!toVisit.isEmpty()) {

			PersonVertex prs = toVisit.poll();
			// System.out.println(prs.name);

			count++;

			for (PersonVertex friend : prs.friends) {

				toVisit.offer(friend);

				pathLookup.put(friend, pathLookup.get(prs) + "-" + friend.name);

				// if the friend is the one that we are looking for in this
				// function

				// return the path and get out

				if (p2 == friend) {

					return pathLookup.get(p2);
				}

				if (count == threshold) {
					return "";
				}

			}

			// we did the visit, added all the friends, now we take the node off
			// the list

			// so it does not get into infinite loop

			toVisit.remove(prs);

		}

		// we visited all the nodes, but we did not find the friend, so return
		// ""

		return "";

	}

	public static void connectorsResult() {

		String result = "";

		ArrayList<PersonVertex> connectors = new ArrayList<PersonVertex>();

		for (PersonVertex p : people) {

			ArrayList<PersonVertex> copy = new ArrayList<PersonVertex>();

			// creating copy of people array and setting number of connections
			for (PersonVertex p2 : people) {
				p2.connections = checkConnections(p2);
				PersonVertex pCopy = new PersonVertex(p2.name, p2.school, p2.isStudent, 0);
				copy.add(pCopy);
			}

			for (PersonVertex p6 : copy) {
				PersonVertex original = peopleLookup.get(p6.name);
				for (PersonVertex friend : original.friends) {
					PersonVertex friendCopy = null;
					for (PersonVertex search : copy) {
						if (search.name.equals(friend.name)) {
							friendCopy = search;
							break;
						}
					}
					p6.addFriend(friendCopy);
				}
			}

			// find the person you need to remove in the copy
			PersonVertex toRemove = null;
			for (PersonVertex p3 : copy)
				if (p3.name.equals(p.name))
					toRemove = p3;

			// remove the person you need to remove and all the connections
			copy.remove(toRemove);
			for (PersonVertex removeFriends : toRemove.friends) {
				int i = 0;
				for (i = 0; i < removeFriends.friends.size(); i++)
					if (removeFriends.friends.get(i).name.equals(toRemove.name))
						break;
				removeFriends.friends.remove(i);

			}

			// updates # of connections for each person in the copy
			for (PersonVertex p4 : copy) {
				p4.connections = checkConnections(p4);

				if (p4.connections + 1 < peopleLookup.get(p4.name).connections) {
					if (!connectors.contains(p))
						connectors.add(p);
					break;
				}
			}
		}

		for (PersonVertex connector : connectors)
			if (connectors.get(0) == connector)
				System.out.print("Connectors: " + connector.name);
			else
				System.out.print(", " + connector.name);

		if (connectors.isEmpty())
			System.out.println("No connectors found.");
		else
			System.out.println();
		System.out.println();
	}

	private static int checkConnections(PersonVertex p) {

		// first find out how many connections are true without
		// removing anyone, and then compare to the number of
		// connections are true with removing someone
		return checkConnections(p, new ArrayList<PersonVertex>(), 0).size() - 1;
	}

	private static ArrayList<PersonVertex> checkConnections(PersonVertex source, ArrayList<PersonVertex> visited,
			int numConn) {
		if (visited.contains(source))
			return visited;
		visited.add(source);

		for (PersonVertex friend : source.friends)
			checkConnections(friend, visited, numConn);

		return visited;
	}

	public static void cliquesAtSchool(String schoolName) {
		ArrayList<ArrayList<PersonVertex>> cliques = new ArrayList<ArrayList<PersonVertex>>();
		for (PersonVertex p : people)
			if (!containsPerson(cliques, p) && p.school != null)
				if (p.school.equalsIgnoreCase(schoolName))
					cliques.add(dfsCliques(p, new ArrayList<PersonVertex>(), schoolName));

		int cliqueNum = 0;
		for (ArrayList<PersonVertex> clique : cliques) {
			cliqueNum++;
			System.out.println();
			System.out.println("Clique " + cliqueNum + ":");
			System.out.println(clique.size());
			for (PersonVertex p : clique) {
				System.out.print(p.name + "|");
				if (p.isStudent)
					System.out.println("y|" + p.school);
				else
					System.out.println("n");
			}
			ArrayList<PersonVertex> peopleVisited = new ArrayList<PersonVertex>();
			for (PersonVertex p : clique) {
				for (PersonVertex friend : p.friends) {
					if (friend.isStudent && friend.school.equalsIgnoreCase(schoolName)
							&& !peopleVisited.contains(friend))
						System.out.println(p.name + "|" + friend.name);
				}
				peopleVisited.add(p);
			}

		}

		if (cliques.isEmpty())
			System.out.println("No cliques found.");
		System.out.println();
	}

	private static boolean containsPerson(ArrayList<ArrayList<PersonVertex>> cliques, PersonVertex p) {
		for (ArrayList<PersonVertex> clique : cliques)
			if (clique.contains(p))
				return true;
		return false;
	}

	private static ArrayList<PersonVertex> dfsCliques(PersonVertex source, ArrayList<PersonVertex> visited,
			String schoolName) {
		if (visited.contains(source))
			return visited;
		if (source.isStudent && source.school.equalsIgnoreCase(schoolName))
			visited.add(source);
		else
			return visited;

		for (PersonVertex friend : source.friends)
			dfsCliques(friend, visited, schoolName);
		return visited;
	}

	// this will print the list of people and all their frieds

	public static void printPeopleList() {

		System.out.println("List of all people:");
		System.out.println("-------------------");

		for (PersonVertex p : people) {

			System.out.print(p.name + "  " + p.isStudent + "  " + p.school + "   friends are: ");

			for (int i = 0; i < p.friends.size(); i++) {

				System.out.print(p.friends.get(i).name + " ");

			}

			System.out.println();

		}

		System.out.println("-------------------");
	}

}
