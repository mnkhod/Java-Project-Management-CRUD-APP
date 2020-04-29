import java.util.*;
import java.lang.*;
import java.io.*;


public class Main{
  public static void main(String[] args){

    // Initializing Variables 
    Scanner scan = new Scanner(System.in);
    String filename = "";
    File file;

    // Get File Name and Check If The File Exists
    do{
      System.out.print("Project Mark File Name : ");
      filename = scan.nextLine();
      file = new File(filename);

      // Clear Console
      clearScreen();
    }while(!filename.contains(".csv") || !file.exists());


    log("Now Initializing Manager");
    ProjectManager manage = new ProjectManager(file);



  }



  // Utility Methods
  static void log(String data){
    System.out.println(data);
  }

  static void clearScreen()
  {
      try
      {
	  final String os = System.getProperty("os.name");

	  if (os.contains("Windows"))
	  {
	      Runtime.getRuntime().exec("cls");
	  }
	  else
	  {
	      Runtime.getRuntime().exec("clear");
	  }
      }
      catch (final Exception e)
      {
	  //  Handle any exceptions.
      }
  }



  // Project Classes
  public static class ProjectManager {
    private List<Project> project_items; 

    public ProjectManager(File projectFile){
      project_items = new ArrayList<Project>();

      organizeCSV(projectFile);   
    }

    // Validate CSV File
    public void organizeCSV(File file){
      try{
	log("Now Initializing OrganizeCSV");

	FileInputStream fstream = new FileInputStream(file);
	BufferedReader reader = new BufferedReader(new InputStreamReader(fstream));
	Scanner input = new Scanner(System.in);
	String line;

	while ((line = reader.readLine()) != null ) {
	  log("\nLine : " + line);
	  String[] items = line.split(",");
	  String first_letter = items[0].substring(0,1);

	  // Check if its not first column then continue
	  if(first_letter.equals("P") || first_letter.equals("F") ){

	    String i_id = items[0];
	    String i_name = items[1];
	    String[] i_marks = Arrays.copyOfRange(items,2,items.length);

	    // Check To Make Sure Everything equals to 100
	    int total = 0;
	    boolean isOverHundred = false; 

	    for(int i=0; i < i_marks.length-1; i++){
	      // Checking If There is Empty Grade
	      if(i_marks[i].equals(null) || i_marks[i].equals("")){
		log("\n");
		do{
		  System.out.print("Item is Empty So Please Fill In A Number : ");
		}while(!input.hasNextInt());
		
		i_marks[i] = input.nextLine();
	      }

	      total += Integer.parseInt(i_marks[i]); 

	      if(total > 100){
		isOverHundred = true;
		break;
	      }
	    }

	    // If over 100 then start from the beginning and start changing every grade till total 100 :V
	    if(isOverHundred){
	      int check_total;

	      do{
		log("This is Over 100 cuz main total right now is " + total);
		log("Refactoring Right Now");
		log("\n");
		log("Changing Grade of " + i_name +" which has a total of " + total + " : " 
		    + String.join("," , Arrays.copyOfRange(i_marks,0,i_marks.length-1) ));

		total = 0;
		check_total = 0;

		for(int i=0; i < i_marks.length-1; i++){
		  System.out.print("Grade is " + i_marks[i] + " would u like to change? 'n' for no / number for change : ");
		  String answer = input.nextLine();

		  if(answer.equals("n")){
		    continue;
		  }else{
		    i_marks[i] = answer;
		    check_total += Integer.parseInt(i_marks[i]); 
		    total += Integer.parseInt(i_marks[i]);
		  }

		}

	      }while(check_total != 100);

	    }

	    // After Validating Add To List
	    project_items.add(new Project(i_name,i_id,i_marks)); 
	  }
	}

	log("Validation Was Succesfull :D , Added Everything To Product List");
	fstream.close();

      }catch(Exception ela){

      }

    }

  }

  public static class Project {
    private String _name;
    private String _id;
    private String[] _marks;

    public Project(String name,String id, String[] marks){
      _name = name;
      _id = id;
      _marks = marks;
    } 

  }


  

}
