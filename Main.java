import java.util.*;
import java.lang.*;
import java.io.*;


public class Main{
  public static void main(String[] args){

    // Initializing Variables 
    String filename = "";
    File file;

    // Get File Name and Check If The File Exists
    do{
      System.out.print("Project Mark File Name : ");
      Scanner check = new Scanner(System.in);
      filename = check.nextLine();
      file = new File(filename);
    }while(!filename.contains(".csv") || !file.exists());


    log("Now Initializing Manager");
    ProjectManager manage = new ProjectManager(file,filename);

    while(true){
      manage.showAndGetMenuOption();
    }

  }



  // Utility Methods
  static void log(String data){
    System.out.println(data);
  }


  // Project Classes
  public static class ProjectManager {
    public List<Project> project_items; 
    public String[] columns;
    public String filename;
    public int mark_size;

    public ProjectManager(File projectFile,String f_name){
      project_items = new ArrayList<Project>();
      filename = f_name;

      organizeCSV(projectFile);   
    }

    // Validate CSV File
    public void organizeCSV(File file){
      try{
	log("Now Initializing OrganizeCSV");

	FileInputStream fstream = new FileInputStream(file);
	BufferedReader reader = new BufferedReader(new InputStreamReader(fstream));
	String line;

	while ((line = reader.readLine()) != null ) {
	  log("\nLine : " + line);
	  String[] items = line.split(",");

	  String first_letter = items[0].substring(0,1);
	  Scanner input = new Scanner(System.in);

	  int count = items.length-2;

	  String[] i_marks = new String[count];
	  String[] i_mark_withNoTotal = new String[count-1];

	  int p = 0;
	  for(int j = 2; j < (items.length-1); j++){
	    i_marks[p] = items[j]; 
	    p++;
	  }

	  p = 0;
	  for(int o = 0; o < (i_marks.length-1); o++){
	    i_mark_withNoTotal[p] = i_marks[o]; 
	    p++;
	  }


	  // Check if its not first column then continue
	  if(first_letter.equals("P") || first_letter.equals("F") ){

	    String i_id = items[0];
	    String i_name = items[1];

	    // Check To Make Sure Everything equals to 100
	    int total = 0;
	    boolean isOverHundred = false; 

	    for(int i=0; i < i_marks.length-1; i++){
	      // Checking If There is Empty Grade
	      if(i_marks[i].equals(null) || i_marks[i].equals("")){
		log("\n");


		while(true){
		  try{
		    Scanner check = new Scanner(System.in);
		    System.out.print("Item is Empty So Please Fill In A Number : ");
		    i_marks[i] = Integer.toString(check.nextInt());
		    break;
		  }catch(Exception e){
		    log("It has to be a number!");
		  }
		}

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
		input = new Scanner(System.in);
		log("This is Over 100 cuz main total right now is " + total);
		log("Refactoring Right Now");
		log("\n");
		log("Changing Grade of " + i_name +" which has a total of " + total + " : " 
		    + String.join("," , i_mark_withNoTotal ));

		total = 0;
		check_total = 0;

		for(int i=0; i < i_marks.length-1; i++){
		  String answer = null;

		  while(true){
		    try{
		      System.out.print("Grade is " + i_marks[i] + " would u like to change? 'n' for no / number for change : ");
		      Scanner check = new Scanner(System.in);
		      answer = check.nextLine();
		      break;
		    }catch(Exception e){

		    }
		  }


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


	    // Finished This Current Project Validation Hence Now Total is 100
	    i_marks[i_marks.length-1] = "100";

	    // After Validating Add To List
	    project_items.add(new Project(i_name,i_id,i_marks)); 
	  }else{
	    // Getting The First Column Headers
	    columns = items;
	    mark_size = i_marks.length; 
	    log("Got The Column Headers");
	  }

	}

	log("Validation Was Succesfull :D , Added Everything To Product List");
	fstream.close();

      }catch(Exception ela){

      }

    }

    // Show Data Table
    public void printToScreen(){
      String[] dump_arr;

      log("\n");
      
      for(int i = 0; i < columns.length; i++){
	System.out.print("| " + columns[i] + " |\t");
      }
      log("\n");

      for(int x=0; x< project_items.size(); x++){
	Project p = project_items.get(x);

	System.out.print("| " + p.id + " |\t");
	System.out.print("| " + p.name + " |\t");

	dump_arr = p.marks;

	for(int i = 0; i < dump_arr.length; i++){
	  System.out.print("| " + dump_arr[i] + " |\t");
	}
	log("\n");
      }
    }

    // Show Data Menu
    public void printDataMenu(){
      log("\n");
      log("1. Enter Project Marks");
      log("2. Add New Project");
      log("3. Delete Project");
      log("4. Save and Exit");
      log("5. Exit without Saving");
      log("\n");
    }

    public void showAndGetMenuOption(){
      Scanner input = new Scanner(System.in);
      int answer = 0;

      printToScreen();
      printDataMenu();
      log("\n");
      
      while(answer <= 0 || answer >= 6){

	  while(true){
	    try{
	      Scanner check = new Scanner(System.in);
	      System.out.print("Please Enter Menu Number : ");
	      answer = check.nextInt();
	      break;
	    }catch(Exception e){
	      log("It has to be a number!");
	    }
	  }

      }

      switch(answer){
	case 1:
	  getProjectOption();
	  break;
	case 2:
	  addProjectOption();
	  break;
	case 3:
	  deleteProjectOption();
	  break;
	case 4:
	  saveAndExitOption();
	  break;
	case 5:
	  exitOption();
	  break;
	default:
	  log("We Dont Have That Option");
      }

    }

    // MENU OPTIONS

    public void getProjectOption(){
      Scanner input = new Scanner(System.in);
      String answer;
      boolean found = false;

      while(true){
	try{
	  Scanner check = new Scanner(System.in);
	  System.out.print("\nPlease Enter Student Id : ");
	  answer = check.nextLine();
	  break;
	}catch(Exception e){
	}
      }

      for(int x=0; x< project_items.size(); x++){
	Project p = project_items.get(x);

	if(answer.equals(p.id)){
	  found = true;

	  String[] marks = p.marks;
	  int total = 0;
	  boolean isHundred = true;
	  

	  String[] i_mark_withNoTotal = new String[marks.length-1];

	  int r = 0;
	  for(int o = 0; o < (marks.length-1); o++){
	    i_mark_withNoTotal[r] = marks[o]; 
	    r++;
	  }
	  // Found now editing the marks

	  log("Refactoring Right Now");

	  do{
	    log("\n");

	    if(isHundred == false){
	      log("Total Mark not 100! , right now the total is " + total);
	    }

	    log("Changing Grade of " + p.name + " : " + String.join("," , i_mark_withNoTotal ));

	    total = 0;

	    for(int i=0; i < marks.length-1; i++){

	      while(true){
		try{
		  Scanner check = new Scanner(System.in);
		  System.out.print("Grade is " + marks[i] + " would u like to change? 'n' for no / number for change : ");
		  answer = check.nextLine();
		  break;
		}catch(Exception e){

		}
	      }


	      if(answer.equals("n")){
		continue;
	      }else{
		marks[i] = answer;
		total += Integer.parseInt(marks[i]); 
	      }

	    }

	    if(total != 100){
	      isHundred = false;
	    }

	  }while(total != 100);

	  // Finished This Current Project Validation Hence Now Total is 100
	  marks[marks.length-1] = "100";
	  log("Finished Refactoring Project Marks");

	  break;
	}
      }

      if(!found){
	log("User Was Not Found!");
      }

    }

    public void saveAndExitOption(){
      String[] dump_arr;

      try {
	FileWriter writer = new FileWriter(filename);

	for(int i = 0; i < columns.length; i++){
	  if(i == columns.length - 1){
	    writer.write(columns[i] + "\n");
	  }else{
	    writer.write(columns[i] + ",");
	  }
	}

	for(int x=0; x< project_items.size(); x++){
	  Project p = project_items.get(x);

	  writer.write(p.id + ",");
	  writer.write(p.name + ",");

	  dump_arr = p.marks;

	  for(int i = 0; i < dump_arr.length; i++){

	    if(i == dump_arr.length - 1){
	      writer.write(dump_arr[i] + "\n");
	    }else{
	      writer.write(dump_arr[i] + ",");
	    }

	  }
	}

	writer.close();

	log("Successfully saved project!");
	log("\nExiting Right Now!");
	System.exit(0);

      } catch (IOException e) {
	System.out.println("An error occurred.");
      }

    }

    public void deleteProjectOption(){
      Scanner input = new Scanner(System.in);
      String answer;
      boolean found = false;
      int counter = 0;

      while(true){

	try{
	  System.out.print("\nPlease Enter Student Id : ");
	  Scanner check = new Scanner(System.in);
	  answer = check.nextLine();
	  break;
	}catch(Exception e){

	}
      }
      
      for(int x=0; x< project_items.size(); x++){
	Project p = project_items.get(x);

	if(answer.equals(p.id)){
	  found = true;

	  project_items.remove(counter);
	  break;
	}

	counter++;
      }

      if(!found){
	log("User Was Not Found!");
      }else{
	log("Successfully Removed Project Task!");
      }

    }

    public void addProjectOption(){
      Scanner input = new Scanner(System.in);
      String answer;
      String new_name;
      String new_id;
      String[] new_marks = new String[mark_size];
      int total = 0;

      int counter = 0;

      do{
	log("ID MUST START WITH 'F' OR 'P' AND FOLLOWED BY 6 OR 7 DIGITS");

	while(true){
	  try{
	    System.out.print("\nPlease Enter New Student Id : ");
	    Scanner check = new Scanner(System.in);
	    new_id = check.nextLine();
	    break;
	  }catch(Exception e){

	  }
	}

	if(new_id.substring(0,1).equals("P") || new_id.substring(0,1).equals("F")){
	  if(new_id.length() == 8 || new_id.length() == 7 ){
	    break;
	  }
	}

      }while(true);


	while(true){
	  try{
	    System.out.print("\nPlease Enter New Student Name : ");
	    Scanner check = new Scanner(System.in);
	    new_name = check.nextLine();
	    break;
	  }catch(Exception e){

	  }
	}

      

      
      for(int i = 0; i < mark_size-1; i++){
	while(true){
	  try{
	    System.out.print("\nPlease Enter New Mark " + (i+1) +" of "+(mark_size-1)+" : ");
	    Scanner check = new Scanner(System.in);
	    new_marks[i] = Integer.toString(check.nextInt());
	    break;
	  }catch(Exception e){
	    log("It Has To Be A Number!");
	  }
	}

	total += Integer.parseInt(new_marks[i]);
      }

      new_marks[mark_size-1] = Integer.toString(total);

      project_items.add(new Project(new_name,new_id,new_marks)); 

      log("Sucessfully Added A New Project Task");
    }

    public void exitOption(){
      log("\nExiting Right Now!");
      System.exit(0);
    }

  }





  // Template Class For Every Project So We can Access and Group Data
  public static class Project {
    public String name;
    public String id;
    public String[] marks;

    public Project(String n,String i, String[] m){
      name = n;
      id = i;
      marks = m;
    } 
  }


  

}
