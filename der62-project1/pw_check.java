
import java.util.*;
import java.io.*;

public class pw_check {
	public static Trie list;

	public static void main(String [] args){
		list = new Trie();
		//read in dictionary and make it a dlb
		try{
			Scanner in = new Scanner(new File("dictionary.txt"));
			while(in.hasNext()){
				list.add(in.nextLine().toLowerCase());
			}
			in.close();
		}
		catch(FileNotFoundException e)
		{
			System.err.print("File not found");
		}
		//if run with find it generates all passwords and writes them to a new file
		if(args[0].equals("-find"))
		{
			char[] posChars ="bcdefghjklmnopqrstuvwxyz02356789!@$^_*".toCharArray();
			int len = 5;
			try{
				FileWriter temp = new FileWriter("all_passwords.txt");
				long startTime = System.currentTimeMillis();
				findPasswords(posChars, len, new char[len],  0,0,0,0,temp, startTime);
				temp.close();
			}
			catch(IOException e)
			{}

		}
		//if run with search it reads in the file to a dlb before asking you to enter a password.
		//if found it prints password and time, if not it says not found
		else if(args[0].equals("-check")){
			Trie passwords = new Trie();
			Scanner reader = new Scanner(System.in);
			boolean yes;
			try{
				Scanner in = new Scanner(new File("all_passwords.txt"));
				while(in.hasNext()){
					String temp = in.nextLine();
					String [] tempy = temp.split(",");
					tempy[0] = tempy[0].toLowerCase();
					long data = Long.parseLong(tempy[1]);
					passwords.add(tempy[0], data);
				}
			
				yes = true;
			}
			catch(FileNotFoundException e)
			{
				System.out.println("File Not found");
				yes = false;
			}
			while(yes){
				System.out.println("Enter a password: ");
				String password = reader.next();
				password = password.toLowerCase();
				StringBuilder temp = new StringBuilder(password);
				int bleh = passwords.search(temp);
				while(true){
				System.out.println("Would you like to enter another password(yes/no)?: ");
				String go = reader.next();
				
					if(go.equals("yes")){
						yes = true;
						break;
					}
					else if(go.equals("no")){
						yes = false;
						break;
					}
					
				}
			}
		}
		

	}
	//method to generate all passwords and their times with pruning during generation
	private static void findPasswords(char [] posChars, int len, char [] passWord, int pos, int numLetters, int numNumbers, int numSymbols,FileWriter temp, long startTime){
		if(pos == len){
			if(numLetters<1 || numNumbers <1 || numSymbols<1)
				return;
			else{
				String word = new String(passWord);
				try{
					long endTime = System.currentTimeMillis();
					long timeToFind = endTime-startTime;
					temp.write(word+',' + timeToFind + '\n');
					return;
				}
				catch(IOException e)
				{}
			}
		}
		for(int i = 0; i<posChars.length; i++){
			boolean isNumber = Character.isDigit(posChars[i]);
			boolean isLetter = Character.isLetter(posChars[i]);
			if( isNumber && numNumbers > 2){
				return;
			}
			else if(isLetter && numLetters > 3){
				return;
			}
			else if(isNumber==false && isLetter==false && numSymbols > 2){
				return;
			}
			else if(list.search(passWord, true) != 0)
			{
				return;
			}
			else
			{
				passWord[pos] = posChars[i];
				if(isLetter)
					findPasswords(posChars, len, passWord, pos+1, numLetters+1, numNumbers, numSymbols, temp, startTime);
				else if(isNumber)
					findPasswords(posChars, len, passWord, pos+1, numLetters, numNumbers+1, numSymbols,temp, startTime);
				else
					findPasswords(posChars, len, passWord, pos+1, numLetters, numNumbers, numSymbols+1,temp, startTime);
			}
		}

	}

}