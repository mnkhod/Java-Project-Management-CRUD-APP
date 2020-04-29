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


    // Initialize the Class! The Class will Validate The Data Inside The Fcken CSV
    log("Now Initializing Manager");
    ProjectManager manage = new ProjectManager(file,filename);

    // After Validation im just making a loop dat runs forever and shows the damn menu :V
    while(true){

      // this bitch shows the data table
      // shows the damn option
      // waits for the option input
      // then depending on the input runs functions :D
      // then loops again :V
      manage.showAndGetMenuOption();
    }

  }



  // Utility Methods
  // I fcken hate writing System.out.println so writing log is faster
  static void log(String data){
    System.out.println(data);
  }


  // Project Classes
  public static class ProjectManager {

    // Having a list of the product with name , array of marks and id is much easier to deal with 
    public List<Project> project_items; 

    // Main Headers so we can print it out easier
    public String[] columns;

    //if u dont understand this pls kill urself :D
    public String filename;

    // Size of Array of Marks
    public int mark_size;


    // CONSTRUCTOR then validator function to ya know validate the fcken data
    public ProjectManager(File projectFile,String f_name){
      project_items = new ArrayList<Project>();
      filename = f_name;

      organizeCSV(projectFile);   
    }

    // Validate CSV File
    public void organizeCSV(File file){
      try{
	log("Now Initializing OrganizeCSV");

	// OK THIS WHOLE THING READS DATA 1 CHAR BY 1 AND ADDS TO LINE ARRAY LIST HENCE
	// WE HAVE ARRAY OF LINES 
	//
	InputStream stream = new FileInputStream(file); 
	String line;
	int data; 
	String l = ""; 
	ArrayList<String> line_list = new ArrayList<String>();

	do {
	   data = stream.read(); 

	   if ((char)data == '\n'){
	      line_list.add(l);
	      l = "";
	      continue;
	   }

	   l += Character.toString((char)data);

	} while (data != -1);

	stream.close();



	// here is a loop of line array so we can deal with line by line and extract data
	for(int x=0; x< line_list.size(); x++){
	  line = line_list.get(x);
	  log("\nLine : " + line);
	  String[] items = line.split(",");

	  String first_letter = items[0].substring(0,1);
	  Scanner input = new Scanner(System.in);

	  int count = items.length-2;


	  // here we make project mark array
	  // first one is marks WITH TOTAL
	  // second is marks WITHOUT TOTAL
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
	  // if its not first column then we extract the data and add it to our PROJECT ARRAY LIST
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


		// if there is an empty mark grade then we fcken huchindeh dat bitch and add an data
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

	      // having a variable where we can check if it exceeded 100 or naah
	      total += Integer.parseInt(i_marks[i]); 

	      if(total > 100){
		isOverHundred = true;
		break;
	      }
	    }

	    // If over 100 then start from the beginning and start changing every grade till total 100 :V
	    if(isOverHundred){
	      int check_total;

	      // LOOP MAKES USER INPUT FROM SCRATCH SINCE THE GRADE IS EXCEEDING 100
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

	// the data is fcken validated now
	log("Validation Was Succesfull :D , Added Everything To Product List");

      }catch(Exception ela){

      }

    }

    //
    //
    // THIS IS JUST METHODS FAM
    
    // Show GRADE DATA TABLE ON SCREEEEN
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

    // Show OPTIONS ON THE DAMN SCREEEN
    public void printDataMenu(){
      log("\n");
      log("1. Enter Project Marks");
      log("2. Add New Project");
      log("3. Delete Project");
      log("4. Save and Exit");
      log("5. Exit without Saving");
      log("\n");
    }



    // OK THIS THE MAIN FUNCTION DAT SHOWS BOTH TABLE AND THE DAMN OPTION MENU
    // THEN ASKS FOR INPUT 

    public void showAndGetMenuOption(){
      Scanner input = new Scanner(System.in);
      int answer = 0;

      printToScreen();
      printDataMenu();
      log("\n");
      
      // validating data since we only want the user to enter 1-6
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

      // DEPENDING ON THE ANSWER WE HAVE FUNCTIONS
      // and since its in a while true forever loop
      // when the function ends
      // the function gets called again
      // showing data table then options to the list <3
      // kids stuff
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

    //
    //
    //
    // MENU OPTIONS

    public void getProjectOption(){
      Scanner input = new Scanner(System.in);
      String answer;
      boolean found = false;


      // just getting data from user
      while(true){
	try{
	  Scanner check = new Scanner(System.in);
	  System.out.print("\nPlease Enter Student Id : ");
	  answer = check.nextLine();
	  break;
	}catch(Exception e){
	}
      }

      // searching for the id from our project list
      for(int x=0; x< project_items.size(); x++){
	Project p = project_items.get(x);


	// here we found our certain project so we can just
	// let the user change everything in this project's grades xD
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

	  // then we break from this loop since we did all what we wanted
	  break;
	}
      }

      // if not found then obvs show user was not found :V
      if(!found){
	log("User Was Not Found!");
      }

    }



    // write all our data from columns since its the header part
    // then from our project list we get our data and write dat bish to a file
    // this is why we needed columns and filename
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

    // using ArrayList remove func we remove it :D from our Project List
    // ask for id then loop through project list till we find it
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

    // Now adding is a bitch
    public void addProjectOption(){
      Scanner input = new Scanner(System.in);
      String answer;
      String new_name;
      String new_id;
      String[] new_marks = new String[mark_size];
      int total = 0;

      int counter = 0;

      // we gotta validated the damn id :V
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


      // now we getting name 
	while(true){
	  try{
	    System.out.print("\nPlease Enter New Student Name : ");
	    Scanner check = new Scanner(System.in);
	    new_name = check.nextLine();
	    break;
	  }catch(Exception e){

	  }
	}

      

     // this is why i needed mark_size
     // cuz i needed to get the length of the damn array so i can use it here
     // for loop and ask and add it to a array :V
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

      // dont forget to add total part too
      new_marks[mark_size-1] = Integer.toString(total);


      // then we add it to our project list ez ez ez
      project_items.add(new Project(new_name,new_id,new_marks)); 

      log("Sucessfully Added A New Project Task");
    }

    
    // oh boi its 5 fcken am manlai :D
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
