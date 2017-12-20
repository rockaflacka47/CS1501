
import java.util.*;
public class Trie {

	private Node rootNode;
	private char voidChar = '.';
	public Trie(){
		rootNode = new Node();
	}
	//adds new word to dlb, if added sets result to true to prevent repeat characters
	public boolean add(String toAdd){
		toAdd = toAdd+ voidChar;
		Node currNode = rootNode;
		boolean added = false;
		for(int i =0; i<toAdd.length(); i++){
			char temp = toAdd.charAt(i);
			wasAdded result = setChildNode(currNode, temp);
			currNode = result.thisNode;
			added = result.added;
		}
		return added;


	}
	//to add the passwords as well as times, incorporates the extra element data on the final character of a word
	public boolean add(String toAdd,long data){
		toAdd = toAdd+ voidChar;
		Node currNode = rootNode;
		boolean added = false;
		for(int i =0; i<toAdd.length(); i++){
			char temp = toAdd.charAt(i);
			wasAdded result = setChildNode(currNode, temp);
			currNode = result.thisNode;
			currNode.data = data;
			added = result.added;
		}
		return added;


	}
	//search for a given word, returns an int based on if its found as a  prefix, word, both, or not found. 
	//only used when searching for a password so prints time if found or not found
	public int search(StringBuilder key)
	{
		Node currNode = rootNode;
		for (int i = 0; i < key.length(); i++)
		{
			char c = key.charAt(i);
			//checks to see if exists
			currNode = getChildNode(currNode, c);
			if (currNode == null)
			{
				//not there
				System.out.println("Password: " + key +" not found.");
				return 0;
			}
		}


		Node terminatorNode = getChildNode(currNode, voidChar);
		if (terminatorNode == null)
		{
			//prefix if no null character
			System.out.println("Found password " + key + " in "+currNode.data+"ms");
			return 1;
		}
		else if (terminatorNode.siblingNode == null)
		{
			//word if null character
			System.out.println("Found password " + key + " in "+currNode.data+"ms");
			return 2;
		}
		else
		{
			//word and prefix cause null character and siblings
			System.out.println("Found password " + key + " in "+currNode.data+"ms");
			return 3;
		}
	}
	//method to prevent numbers/symbols as letters in common words
	public char [] replace(char [] before){
		String s = before.toString();
		s.replace('7',  't');
		s.replace('0', 'o');
		s.replace('3', 'e');
		s.replace('$', 's');
		return s.toCharArray();
	}
	//same as above search funtion but with character array
	public int search(char [] key, boolean switchup)
	{
		Node currNode = rootNode;
		if(switchup = true)
			key = replace(key);
		for (int i = 0; i < key.length; i++)
		{
			char c = key[i];
			currNode = getChildNode(currNode, c);

			if (currNode == null)
			{
				//not there
				return 0;
			}
		}
		Node terminatorNode = getChildNode(currNode, voidChar);
		if (terminatorNode == null)
		{
			return 1;
		}
		else if (terminatorNode.siblingNode == null)
		{
			return 2;
		}
		else
		{
			return 3;
		}
	}
	//sets sibling at first spot it should and returns true if it was added or if it exists doesnt add and returns false
	public wasAdded setSiblingNode(Node currNode, char toAdd){
		if(currNode == null)
		{
			currNode = new Node(toAdd);
			return new wasAdded(currNode, true);
		}
		else{
			Node nextSibling = currNode;
			while(nextSibling.siblingNode !=null)
			{
				if(nextSibling.value == toAdd)
					break;
				nextSibling = nextSibling.siblingNode;
			}

			if(nextSibling.value==toAdd)
				return new wasAdded(nextSibling, false);
			else
			{
				nextSibling.siblingNode = new Node(toAdd);
				return new wasAdded(nextSibling.siblingNode, true);
			}
		}
	}
	//set child node if null otherwise pass it to sibling node
	private wasAdded setChildNode(Node currNode, char toAdd){
		if(currNode.childNode==null)
		{
			currNode.childNode = new Node(toAdd);
			return new wasAdded(currNode.childNode, true);
		}
		else{
			return setSiblingNode(currNode.childNode, toAdd);
		}
	}
	//returns sibling node
	public Node getSiblingNode(Node currNode, char c){
		Node nextSib = currNode;
		while(nextSib != null)
		{
			if(nextSib.value == c)
				break;
			nextSib=nextSib.siblingNode;
		}
		return nextSib;
	}
	//gets childnode if not null by passing the child to get sibling
	public Node getChildNode(Node currNode, char c){
		return getSiblingNode(currNode.childNode, c);
	}



	class Node {
		Node siblingNode;
		Node childNode;
		char value;
		long data;
		public Node(){
			value = ' ';
			siblingNode = null;
			childNode = null;
			data = 0;
		}

		public Node(char c){
			this(c, null, null, 0);
		}

		public Node(char c, Node sibling, Node child, long d){
			value = c;
			siblingNode = sibling;
			childNode = child;
			data = d;
		}
	}
	//a class to say if a letter was added to prevent dups
	class wasAdded
	{
		Node thisNode;
		boolean added;
		public wasAdded(Node temp, boolean wasAdded)
		{
			thisNode = temp;
			added = wasAdded;
		}
	}

}