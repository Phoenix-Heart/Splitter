import java.awt.Panel;
import java.io.*;
import java.util.Scanner;

import javax.swing.JFileChooser;


public class Run {


	public static void main(String[] args) {
		convert(selector());
	}
	static String selector() {
		String filename;
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(new Panel()); //Where frame is the parent component

		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    file = fc.getSelectedFile();
		    //Now you have your file to do whatever you want to do
		    filename = file.getAbsolutePath();
		} else {
			System.out.println("File selector failed.");
			System.out.println("Enter filename");
			Scanner in = new Scanner(System.in);
			filename = in.next();
		}
		return filename;
		
	}
	static void convert(String filename) {

			  
			  String returnValue = "";
			  FileReader infile = null;
			  FileWriter outfile = null;
			  
			  try {
				  String outfilename = filename.replaceFirst(".txt", "_clean.txt");
			      outfile = new FileWriter(outfilename);
			      BufferedWriter writer = new BufferedWriter(outfile);
			      //writer.write(s);
			      File file = new File(outfilename);
			      File parent = file.getParentFile();

			      if(!parent.exists() && !parent.mkdirs()){									// !parent.mkdirs() -> if it fails to create the directory
			          throw new IllegalStateException("Couldn't create dir: " + parent);
			      } 
				  
			    infile = new FileReader(filename);
			    BufferedReader reader = new BufferedReader(infile);
			    String line = "";
			    while ((line = reader.readLine()) != null) {
			    	if(!line.isEmpty()) {
			    		
				    	while(line.charAt(0)==' ') {		// strip out leading space
				    		line = line.substring(1);
				    	}
				    	if(line.charAt(0)=='#') {
				    		System.out.print("writing..");
				    		writer.write(line.substring(1));
				    		writer.newLine();
				    	}// remove hash
				    	if(line.startsWith("Quiz")) {
				    		System.out.print("writing..");
				    			writer.write(line);
				    			writer.newLine();
				    	}
			    	}
			    }
			    writer.close();
			  } catch (Exception e) {
			      throw new RuntimeException(e);
			  } finally {
			    if (infile != null) {
			      try {
			        infile.close();
			      } catch (IOException e) {
			        System.out.println("Error: did not close file properly.");
			      }
			    }
			    if (outfile != null) {
		        try {
		            outfile.close();
		          } catch (IOException e) {
		            // Ignore issues during closing
		          }
			  }
			  }
	}

}