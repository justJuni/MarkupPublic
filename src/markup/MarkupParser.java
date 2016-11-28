package markup;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.io.BufferedReader;
import schema.saveSQL;

//This java class parses .html files to score aribitrary values to html tags
public class MarkupParser {

	//String array reads in prefix of filenames in keyname_yyyy_mm_dd format
    public static void main(String[] args) throws SQLException {
    	BufferedReader buffR = null;	//Create buffer
    	String currentln;	//Current Line in the buffer
    	String sqlString;	//String created for debugging purposes
    	saveSQL save = new saveSQL();
    	
	    //Try to parse in the file and Catch exceptions
    	try {
			int total = 0;	//Total score
			int min = -1;	//Lowest score
			int max = -1;	//Highest score
			save.myConnection();
			
			//For loops to get each filenames
    		for (int i = 0; i < args.length; i++){
    			args[i] = args[i].toLowerCase();	//Ensure the filenames are lowercase
				//Spliting the filename with delimited underscore characters
				String[] filename = args[i].split("_");
				String keyname = filename[0];
				String year = filename[1];
				String month = filename[2];
				String day = filename[3];
				int score = 0;	//Score to sum up

				buffR = new BufferedReader(new FileReader(args[i]));	//Reading in files

				//Reading each line of the file and add score values
		    	while ((currentln = buffR.readLine()) != null) {
		    		currentln.toLowerCase();	//Ensure all lines are lowercase
	    			if (currentln.contains("<div")){
	    				score += 3;
	    			}
					else if (currentln.contains("<p")){
						score += 1; 
					}
					else if (currentln.contains("<h1")){
						score += 3;
					}
					else if (currentln.contains("<h2")){
						score += 2;
					}
					else if (currentln.contains("<html")){
						score += 5;
					}
					else if (currentln.contains("<body")){
						score += 5;
					}
					else if (currentln.contains("<header")){
						score += 10;
					}
					else if (currentln.contains("<footer")){
						score += 10;
					}
					else if (currentln.contains("<font")){
						score += -1;
					}
					else if (currentln.contains("<center")){
						score += -2;
					}
					else if (currentln.contains("<big")){
						score += -2;
					}
					else if (currentln.contains("<strike")){
						score += -1;
					}
					else if (currentln.contains("<tt")){
						score += -2;
					}
					else if (currentln.contains("<frameset")){
						score += -5;
					}
					else if (currentln.contains("<frame")){
						score += -5;
					}
	        	}

	        	//Sum total score, lowest, and highest values
				total += score;
				min = Math.min(score, min);
				max = Math.max(score, max);

				save.myQuery("INSERT INTO scores (keyname, date, score) VALUES ('" + keyname + "','" + year + "-" + month + "-" + day + "','" + score + "');");
				sqlString = args[i] + " " + score;
        		System.out.println(sqlString);
        	}
    		System.out.println(total + " " + min + " " + max);
    	} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}