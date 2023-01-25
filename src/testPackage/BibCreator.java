//Final Project By MoSikLam
package testPackage;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

import basisClass.FileInvalidException;

public class BibCreator {
	
	private static int invalidFilesCounter = 0;
	private static int validFilesCounter = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner readFile = null;
		PrintWriter writeFile = null;
		BufferedReader br = null;
		Scanner scanner = new Scanner(System.in);
		int fileNum = 1;
		final int NUMOFFILES = 10;
		
		boolean isIEEEFileCreated = false;
		boolean isACMFileCreated = false;
		boolean isNJFileCreated = false;
		
		int numOfAttempt = 0;
		String file = "";
		
		System.out.println("Welcome to BibCreator!\n");
		
		// Reading .bib File
		
		try {
			
			for(int i = 1; i <= NUMOFFILES; i++) {
				
				readFile = new Scanner(new FileInputStream("Latex" + i + ".bib"));
				fileNum = i;
				readFile.close();
			}
		} 
		
		catch (FileNotFoundException e)
		{
			System.out.println("Could not open input file Latex" + fileNum + ".bib for reading.\n");
			System.out.println("Please check if file exists! "
					+ "Program will terminate after closing any opened files.");
			System.exit(0);
		}
		
		// Writing ieee, acm and nj file
		
		for(int i = 1; i <= NUMOFFILES; i++) {
		
			try {
				
				//Write IEEE File
				writeFile = new PrintWriter(new FileOutputStream("IEEE" + i + ".json"));
				isIEEEFileCreated = true;
				writeFile.close();
				
				//Write ACM File
				writeFile = new PrintWriter(new FileOutputStream("ACM" + i + ".json"));
				isACMFileCreated = true;
				writeFile.close();
				
				//Write NJ File
				writeFile = new PrintWriter(new FileOutputStream("NJ" + i + ".json"));
				isACMFileCreated = true;
				writeFile.close();
				
				fileNum = i;
				
			}
			catch (FileNotFoundException e) {
				
				System.out.println(e.getMessage() + "\n");
				
				if(isIEEEFileCreated == false) {
					
					System.out.println("Could not open/create IEEE" + fileNum + ".json file.");
					writeFile.close();
				}
				
				if(isACMFileCreated ==false) {
					
					System.out.println("Could not open/create ACM" + fileNum + ".json file.");
					writeFile.close();
				}
				
				if(isNJFileCreated == false) {
					
					System.out.println("Could not open/create NJ" + fileNum + ".json file.");
					writeFile.close();
				}

				System.out.println("Deleting all other created output files. "
						+ "Program will terminate after deleting files.");
				
				for(int j = 1; j <= fileNum; j++) {
					
					deleteFile(j);
				}

				System.exit(0);
			}
			
			// processing the input files and creating the output ones.
			try {
				
				readFile = new Scanner(new FileInputStream("Latex" + i + ".bib"));
					
				try {
						
					processFilesForValidation(readFile, writeFile, i);
				} catch (FileInvalidException e) {
						
					// TODO Auto-generated catch block
					System.out.println(e.getLocalizedMessage());
					deleteFile(i);
					
					writeFile.close();
				}
					
			} catch (FileNotFoundException e) {

				System.out.println("Could not open input file Latex" + i + ".bib for reading.\n\n"
						+ "Please check if file exists! "
						+ "Program will terminate after closing any opened files.");
				readFile.close();
				System.exit(0);
				
			}
		}			
		
		System.out.println("\nA total of " + invalidFilesCounter + " files were invalid, and could not be processed. "
				+ "All other " + validFilesCounter + " \"Valid\" files have been created.\n");
		
		while (numOfAttempt < 2) {
			
			System.out.print("Please enter the name of one of the files that you need to review: ");
			file = scanner.next();
			
			try {
				
				br = new BufferedReader(new FileReader(file));
				System.out.println("Here are the contents of the successfully created Jason File: \n" + file);
				numOfAttempt = 2;
				scanner.close();
			}
			catch (FileNotFoundException e) {
				
				if (numOfAttempt == 1) {
					
					System.out.println("Could not open input file again! Either file does not exist or could not be created.");
					System.out.println("Sorry! I am unable to display your desired files! Program will exit!");
					System.exit(0);
					
				}
				
				System.out.println("Could not open input file. File does not exist; possibly it could not be created!");
				System.out.println("\nHowever, you will be allowed another chance to enter another file name.");
				numOfAttempt++;
			}
		}
		
		try {
			
			displayFileContent(br);
			System.out.println("Goodbye! Hope you have enjoyed creating the needed files using BibCreator.");
		}
		catch(IOException e) {
			
			System.out.println("Error occured while reading the file: " + file);
			System.out.println("Program will terminate");
			System.exit(0);
		}
		
	}
	
	//Delete files created
	private static void deleteFile(int i) {
		// TODO Auto-generated method stub
		
		File fileToDelete = null;
			
		fileToDelete = new File("NJ" + i + ".json");
		fileToDelete.delete();
		
		fileToDelete = new File(("IEEE" + i + ".json"));
		fileToDelete.delete();
		
		fileToDelete = new File("ACM" + i + ".json");
		fileToDelete.delete();
	}
	
	private static void displayFileContent(BufferedReader rf) throws IOException {
		
		String line;
		
		line = rf.readLine();
		while(line!=null) {
			System.out.println(line);
			line = rf.readLine();
		}
		
		rf.close();
	}
	
	//IEEE, ACM, and NJ Format to Write in File
	private static String IEEEToString(String athr, String ttl, String jrnl, String vol, String num, String pg, String m, String yr) {
		
		return athr + ". \"" + ttl + "\" " + jrnl + ", vol. " + vol + ", no. " + num + ", p. " + pg + ", " + m + " " 
				+ yr + ".\n\n";
	}
	
	private static String ACMToString(int artclNum, String athr, String yr, String ttl, String jrnl, String vol, String num, String pg, String DOI) {
		
		return "[" + artclNum + "] " + athr + ". " + yr + ". " + ttl + ". " + jrnl + ". " + vol + ", " + num + " (" + yr + "), " + pg + ". " + DOI + ".\n\n";
	}

	private static String NJToString(String athr, String ttl, String jrnl, String vol, String pg, String yr) {
	
		return athr + ". " + ttl + ". " + jrnl + ". " + vol + ", " + pg + " (" + yr + ").\n\n";
	}
	
public static void processFilesForValidation(Scanner rf, PrintWriter wf, int i) throws FileInvalidException, FileNotFoundException {
		
		String line = "";
		int ACMArticleNum = 0;
		
		StringTokenizer tokenizer = null;
		String[] token;
		
		String IEEE = "";
		String ACM = "";
		String NJ = "";
		
		String author = "";
		String IEEEAuthor = "";
		String ACMAuthor = "";
		String NJAuthors = "";
		String title = "";
		String journal = "";
		String volume ="";
		String number = "";
		String page = "";			
		String month = "";		
		String year = "";	
		String DOI ="";
		
		//substring = returns string with the index
		
		while (rf.hasNextLine()) {
			
			line = rf.nextLine();
			
			if(line.contains("{}") || line.contains("{},}") || line.contains("{ }")) {
				
				invalidFilesCounter++;
				throw new FileInvalidException("Error : Detected Emply Field!\n"
						+  "=============================\n\n" 
						+ "Problem detected with input file : Latex" + i + ".bib\n"
						+ "File Invalid. Field \"" + line.substring(0, line.indexOf("=")) + "\" is Empty. "
						+ "Processing stopped at this point. Other empty fields may be present as well!\n");
			}
			
			else {
				
				if (line.contains("@ARTICLE{")) {
					ACMArticleNum++;
				}
				
				if (line.contains("author={")) {
					
					author = line.substring(line.indexOf("{") + 1, line.indexOf("}"));	
		
					IEEEAuthor = author.replaceAll(" and", ",");
				
					NJAuthors = author.replaceAll("and", "&");
					
					tokenizer = new StringTokenizer(author, " "); //Tokenize the lengthy authors
					token = new String[tokenizer.countTokens()];
					
					for(int j = 0; j < tokenizer.countTokens(); j++){
						
						token[j] = tokenizer.nextToken(); //Assign the separated words into token[]
					}
					
					ACMAuthor = token[0] + " " + token[1] + " et al";		
				}
				
				if (line.contains("title={")) {
					
					title = line.substring(line.indexOf("{") + 1, line.indexOf("}"));	
				}
				
				if (line.contains("journal={")) {
					
					journal = line.substring(line.indexOf("{") + 1, line.indexOf("}")); 
				}
				
				if (line.contains("volume={")){
					
					volume = line.substring(line.indexOf("{") + 1, line.indexOf("}")); 
				}
				
				if (line.contains("number={")) {
					
					number = line.substring(line.indexOf("{") + 1, line.indexOf("}")); 
				}
				
				if (line.contains("pages={")) {
					
					page = line.substring(line.indexOf("{") + 1, line.indexOf("}")); 
				}
				
				if (line.contains("month={")) {
					
					month = line.substring(line.indexOf("{") + 1, line.indexOf("}")); 
				}
				
				if (line.contains("year={")) {
					
					year = line.substring(line.indexOf("{") + 1, line.indexOf("}")); 
					
				}			
				
				if (line.contains("doi={")) {
					
					DOI = "DOI:https://doi.org/" + line.substring(line.indexOf("{") + 1, line.indexOf("}"));
				}
				
				
				if (line.equals("}")) {
					
					wf = new PrintWriter(new FileOutputStream("IEEE" + i + ".json"));
					IEEE += IEEEToString(IEEEAuthor, title, journal, volume, number, page, month, year);
					wf.println(IEEE);
					wf.close();
					
					wf = new PrintWriter(new FileOutputStream("ACM" + i + ".json"));
					ACM += ACMToString(ACMArticleNum, ACMAuthor, year, title, journal, volume, number, page, DOI);
					wf.println(ACM);
					wf.close();
					
					wf = new PrintWriter(new FileOutputStream("NJ" + i + ".json"));
					NJ += NJToString(NJAuthors, title, journal, volume, page, year);
					wf.println(NJ);
					wf.close();
				}
			}
			
			if (rf.hasNextLine() == false) {
				
				validFilesCounter++;
			}
		}
	}

}
