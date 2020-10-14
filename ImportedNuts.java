/*Name: Mahika Vajpeyi and Saumyaa Mehra
 *File: INuts
 *Description: Presents information on import of nuts into the United States during the period 1999-2014 
 */
import acm.program.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import acm.graphics.*;
import acm.util.*;


public class ImportedNuts extends GraphicsProgram
{
	// FIELDS FOR INTRODUCTION STATE
	// images of different types of nuts
	private GImage Cashews;
	private GImage Peanuts;
	private GImage TreeNuts;
	// booleans to check if a particular nut is chosen
	private boolean cashewsChosen;
	private boolean peanutsChosen;
	private boolean treeNutsChosen;
	//Label that is used to welcome the user
	private GLabel Hello;
	
	// FIELDS FOR MENU STATE
	//the rectangles that give the user different options(importers of the nut selected)
	// to choose from array lists that store boxes displaying names of importers for each type of nut
	private GRect box;
	private ArrayList<GRect> peanutBoxes = new ArrayList<GRect>();
	private ArrayList<GRect> cashewBoxes = new ArrayList<GRect>();
	private ArrayList<GRect> treeNutBoxes = new ArrayList<GRect>();
	//Y co-ordinate of the upper left corner of the first box for each nut
	private int firstYCoordinate; 
	//variable to keep track of the line at which the program starts storing names of importers for each nut
	private int j;
	//y co-ordinate of the lower left corner of the label that displays names of importers
	private int labelYCoordinate;
	//array list to store names of importers
	private ArrayList<String> countryNames = new ArrayList<String>();
	
	// FIELDS FOR DISPLAY STATE
	// array lists that store value of imports of all nuts by all importers for
	// the year indicated in the names of respective list
	private ArrayList<Double> values1999 = new ArrayList<Double>();
	private ArrayList<Double> values2000 = new ArrayList<Double>();
	private ArrayList<Double> values2001 = new ArrayList<Double>();
	private ArrayList<Double> values2002 = new ArrayList<Double>();
	private ArrayList<Double> values2003 = new ArrayList<Double>();
	private ArrayList<Double> values2004 = new ArrayList<Double>();
	private ArrayList<Double> values2005 = new ArrayList<Double>();
	private ArrayList<Double> values2006 = new ArrayList<Double>();
	private ArrayList<Double> values2007 = new ArrayList<Double>();
	private ArrayList<Double> values2008 = new ArrayList<Double>();
	private ArrayList<Double> values2009 = new ArrayList<Double>();
	private ArrayList<Double> values2010 = new ArrayList<Double>();
	private ArrayList<Double> values2011 = new ArrayList<Double>();
	private ArrayList<Double> values2012 = new ArrayList<Double>();
	private ArrayList<Double> values2013 = new ArrayList<Double>();
	private ArrayList<Double> values2014 = new ArrayList<Double>();
	
	// array list of array lists that stores value of imports of all nuts by all
	// importers for all years
	private ArrayList<ArrayList> masterList = new ArrayList<ArrayList>();
	// array list to store years
	private ArrayList<Integer> yearsList = new ArrayList<Integer>();
	// array list to store bars displayed in bar graphs
	private ArrayList<GRect> Rects = new ArrayList<GRect>();
	//stores all the usernames of different user accounts
	private ArrayList<String> userUN = new ArrayList<String>(); 
	//stores all the passwords of different user accounts
	private ArrayList<String> userPW = new ArrayList<String>();
	
	//keeps track of index within smaller array lists that store values for a single year
	private int importValuesIndex;	
	//y co-ordinate of the upper left corner of the first bar in each bar graph
	private int yStart;						
	private int masterListIndex;	// keeps track of index within the masterList
	private GLine arrowBody; 			// arrow of the label (says "IMPORTS IN MILLION
	// DOLLARS") below the x-axis
	private GLine upperArrow;			// upper arrow of the arrowBody
	private GLine lowerArrow;			// lower arrow of the arrowBody
	private GLabel label;					// label for the arrow (says "IMPORTS IN MILLION DOLLARS")
	
	private GLine xAxis;
	private GLabel xAxisLabel;		// label that says 'X' for the x-axis
	private GLine upperXArrow;		// upper arrow of the x-axis
	private GLine lowerXArrow;		// lower arrow of the y-axis
	private GLine yAxis;
	private GLabel yAxisLabel;		// label that says 'Y' for the y-axis
	private GLine leftYArrow;			// left arrow of the y-axis
	private GLine rightYArrow;		//right arrow of the y-axis
	private int initialGreen = 204;// initial value of green in the color of bars
	private int initialBlue = 204;// initial value of blue in the color of bars
	private int markX;						//the X co-ordinate of markings(vertical lines) along the X axis
	
	private int firstMark;				//the marks(numbers) for markings along the X axis
	
	
	// label that displays value of imports when the user hovers the cursor over a bar	
	private GLabel valueOfImports = new GLabel("", 100, 530);
	// the label that asks the user to move the cursor over one of the bars
	private GLabel instructions = new GLabel("Move the cursor over one of the bars.", 5, 600);
	//keeps track of the index of the previous bar chosen when the user chooses a new bar
	private int previousIndex;
	
	//FIELDS FOR STATE 4 AND 5
	private GLabel usernameLbl;			//Label that displays text "Username" for login system
	private GLabel passwordLbl;			//Label that displays text "Password" for login system
	private GRect signIn; 					//box for sign in button
	private GLabel signInLabel; 		//text for Sign in button 
	private TextField username; 		//textfield that takes in username entered by user
	private TextField password;			//textfield that takes in password entered by user
	private String UNstring, PWstring; //strings that stores the username and password after reading from file account.txt
	private GRect createAccount; 		//box for create account button
	private GLabel createAccountlbl;//label for create account button 
	private GLabel incorrect; 			//text displayed when user enters incorrect username and password
	
	//Fields required for more than one state
	private GLabel countries;				// label that displays names of countries
	private GRect back; 						// the back button
	private GLabel backLabel;				// label displayed inside the back button
	private int state=4; 						// integer to denote the state the user is in
	
	//arraylist used to store the index of the country whose graph the user last viewed
	//private ArrayList<Double> userGraphInfo = new ArrayList<Double>(); 
	
	
	@Override
	public void run()
	{
		setSize(1050, 900);						// resets applet size
		
		goToState(4);
		
		try (Scanner file = new Scanner(new FileReader("nuts2_1_.csv")))
		{
			int line = 1;							// counts the number of lines
			
			// omits the first four lines of the text file
			while (line < 5)
			{
				file.nextLine();
				line++;									// increments the number of lines
			}
			
			// stores values of imports
			while (line < 47)
			{
				// stores each line of the file in a variable called imports splits up a line if the third 
				// character is an upper case letter and omits it otherise (because all lines 
				//  needed for the program use only uppercase characters) 
				String imports = file.nextLine();
				
				if (imports.substring(2, 3).matches("[A-Z]"))
				{
					// creates an array of strings formed by splitting each line
					//of the file at commas
					String[] pieces = imports.split(",");
					
					// adds the first element of each line to the array list of names of countries
					// stores values in respective lists
					countryNames.add(pieces[0]);
					values1999.add(Double.parseDouble(pieces[1]));
					values2000.add(Double.parseDouble(pieces[2]));
					values2001.add(Double.parseDouble(pieces[3]));
					values2002.add(Double.parseDouble(pieces[4]));
					values2003.add(Double.parseDouble(pieces[5]));
					values2004.add(Double.parseDouble(pieces[6]));
					values2005.add(Double.parseDouble(pieces[7]));
					values2006.add(Double.parseDouble(pieces[8]));
					values2007.add(Double.parseDouble(pieces[9]));
					values2008.add(Double.parseDouble(pieces[10]));
					values2009.add(Double.parseDouble(pieces[11]));
					values2010.add(Double.parseDouble(pieces[12]));
					values2011.add(Double.parseDouble(pieces[13]));
					values2012.add(Double.parseDouble(pieces[14]));
					values2013.add(Double.parseDouble(pieces[15]));
					values2014.add(Double.parseDouble(pieces[16]));
					// adds all lists to the masterList
					masterList.add(values1999);
					masterList.add(values2000);
					masterList.add(values2001);
					masterList.add(values2002);
					masterList.add(values2003);
					masterList.add(values2004);
					masterList.add(values2005);
					masterList.add(values2006);
					masterList.add(values2007);
					masterList.add(values2008);
					masterList.add(values2009);
					masterList.add(values2010);
					masterList.add(values2011);
					masterList.add(values2012);
					masterList.add(values2013);
					masterList.add(values2014);
				}
				line = line + 1;							// increments the number of lines by one
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.print("File not found: " + e.getMessage());
			return;												// aborts the program if the required file is not found
		}
		
		try (Scanner file2 = new Scanner(new FileReader("account.txt")))
		{
			int linef2=1;
			
			while(linef2<=8) 							//omits first 8 lines of text  
			{ 
				file2.nextLine();
				linef2++;
			}
			
			while (file2.hasNextLine())
			{
				String lineString = file2.nextLine();	//stores each line in lineString
				String[] piecesf2 = lineString.split(","); //contents of lineString are split on the basis of a comma and each piece is stored in array piecesf2
				
				userUN.add(piecesf2[0]);
				userPW.add(piecesf2[1]);
				
				//if (piecesf2[2]!=null)
				//{
				//userGraphInfo.add(Double.parseDouble(piecesf2[2]));
				//}
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.print("File not found: " + e.getMessage());
			return;
		}
		
		// adds images and resizes them
		Cashews = new GImage(MediaTools.loadImage("Cashews.jpg"), 440, 420);
		Peanuts = new GImage(MediaTools.loadImage("Peanuts.jpg"), 250, 250);
		TreeNuts = new GImage(MediaTools.loadImage("TreeNuts.jpg"), 650, 250);
		Cashews.scale(0.3);
		Peanuts.scale(0.6, 0.6);
		TreeNuts.scale(.5, 0.735);
		// creates the back button (and the label)
		back = new GRect(800, 600, 50, 30);
		backLabel = new GLabel("BACK", 810, 620);
		// creates the label below the x-axis
		arrowBody = new GLine(80, 430, 520, 430);
		upperArrow = new GLine(520, 430, 515, 425);
		lowerArrow = new GLine(520, 430, 515, 435);
		label = new GLabel("IMPORTS (IN MILLION DOLLARS)", 150, 420);
		// creates the axes and arrows and labels for the axes
		xAxis = new GLine(60, 380, 740, 380);
		upperXArrow = new GLine(740, 380, 735, 375);
		lowerXArrow = new GLine(740, 380, 735, 385);
		xAxisLabel = new GLabel("X", 745, 390);
		yAxis = new GLine(60, 40, 60, 380);
		leftYArrow = new GLine(60, 40, 55, 45);
		rightYArrow = new GLine(60, 40, 65, 45);
		yAxisLabel = new GLabel("Y", 50, 45);
		
		// adds years from 1999 to 2014 to the array list of years
		// variable to keep track of index in the yearsList
		
		int j = 1999;
		
		while (j < 2015)
		{
			yearsList.add(j);
			j = j + 1;
		}
		
		addMouseListeners();
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		// if the sign in button is clicked compiler extracts corresponding values of usernames and passwords
		//and stores them in UNstring and PWstring
		if ((signIn.contains(e.getX(), e.getY()))) 
		{
			//Then, it compares these two strings to the strings entered in the text field, to check 
			//if the user has entered a valid username and password
			for (int i = 0; i < userUN.size(); i++)	
			{																			
				UNstring = userUN.get(i);
				PWstring = userPW.get(i);
				if ((username.getText().equals(UNstring)&& (password.getText().equals(PWstring))))		
				{
					//if user has entered valid username and p/w, user can now retrieve information and go to state 1
					goToState(1);									
					//double countryIndex = userGraphInfo.get(i);
					return;
				}
				
				//displayed when username & password entered is not stored anywhere in file account.txt
				incorrect = new GLabel("Username or Password entered is incorrect. Sign in again!", 100, 80); 
				incorrect.setFont(new Font("Serif", Font.BOLD, 20));
				add(incorrect);																																		
				
			}			
			return; //to avoid implementation of other conditions in mousePressed
		}
		
		if (createAccount.contains(e.getX(), e.getY()))
		{
			if (state == 4)
			{
				goToState(5);
				return;
			}
			//prints the username and password into file every time the user clicks
			//on create account button and enters a new username and password
			printIntoFile(username.getText(), password.getText());
			
			if (state == 5)
			{
				goToState(4);
				return;
			}
		}
		
		if (state == 1)
		{
			// checks the nut chosen by the user and directs them to state two
			if (Cashews.contains(e.getX(), e.getY()))
			{
				// sets cashewsChosen to true and the other two boolean values to false
				// as cashews is the chosen nut.
				cashewsChosen = true;
				peanutsChosen = false;
				treeNutsChosen = false;
				goToState(2);
			}
			
			else if (Peanuts.contains(e.getX(), e.getY()))
			{
				cashewsChosen = false;
				peanutsChosen = true;
				treeNutsChosen = false;
				goToState(2);
			}
			
			else if (TreeNuts.contains(e.getX(), e.getY()))
			{
				cashewsChosen = false;
				peanutsChosen = false;
				treeNutsChosen = true;
				goToState(2);
			}
		}
		
		else if (state == 2)
		{
			// directs the user to state one if the back button is clicked on
			if (back.contains(e.getX(), e.getY()))
			{
				goToState(1);
			}
			// checks the importer the graph is to be displayed for and takes the user
			// to state three when an option (importer) is chosen
			
			if (peanutsChosen == true)
			{
				importValuesIndex = 0;// variable which tracks the index of the box chosen
				for (GRect option : peanutBoxes)
				{
					if (option.contains(e.getX(), e.getY()))
					{
						goToState(3);
						// calls the displayGraph function and passes the index of the
						// 'option' chosen as a parameter
						//	printIntoFileInt(n); //to print accessed countryIndex into file
						displayGraph(importValuesIndex);
						// exits the loop when the program finds the 'option' the user selected
						
						break;
					}
					
					importValuesIndex = importValuesIndex + 1;
				}
			}
			// checks the importer and displays the graph for cashews and tree nuts
			
			if (cashewsChosen == true)
			{
				importValuesIndex = 0;
				
				for (GRect option : cashewBoxes)
				{
					if (option.contains(e.getX(), e.getY()))
					{
						goToState(3);
						//	printIntoFileInt(n);	  //to print accessed countryIndex into file (did not work)
						displayGraph(importValuesIndex);
						break;
					}
					
					importValuesIndex = importValuesIndex + 1;
				}
			}
			
			if (treeNutsChosen == true)
			{
				importValuesIndex = 0;
				
				for (GRect option : treeNutBoxes)
				{
					if (option.contains(e.getX(), e.getY()))
					{
						goToState(3);
						//printIntoFileInt(n); //to print accessed countryIndex into file
						displayGraph(importValuesIndex);
						break;
					}
					
					importValuesIndex = importValuesIndex + 1;
				}
			}
		}
		
		else if (state == 3)
		{
			// takes the user to state two if the back button is clicked on
			if (back.contains(e.getX(), e.getY()))
			{
				goToState(2);
			}
		}
		
		//state 4 and state 5 help handle the disappearance and appearance of sign in button
		else if (state == 4) 
		{
			goToState(4);
		}
		else if (state == 5)
		{
			goToState(5);
		}
	}
	
	// method which switches states
	public void goToState(int newState)
	{
		removeAll();									//clears the screen when the user switches to a different state
		valueOfImports.setLabel(""); 	// resets the label to an empty string
		state = newState;						// resets state
		if (state == 1)
		{
			// sets the background and adds it to the screen
			GImage backgroundImage1 =
					new GImage(MediaTools.loadImage("backgroundImage1.jpg"), 0, 0);
			backgroundImage1.scale(.9);
			add(backgroundImage1);
			
			// adds images
			add(Cashews);
			add(Peanuts);
			add(TreeNuts);
			
			// adds names of nuts
			GLabel peanuts = new GLabel("PEANUTS", 280, 400);
			peanuts.setFont(new Font("Serif", Font.BOLD, 20));
			add(peanuts);
			GLabel treeNuts = new GLabel("TREE NUTS", 680, 400);
			treeNuts.setFont(new Font("Serif", Font.BOLD, 20));
			add(treeNuts);
			GLabel cashews = new GLabel("CASHEWS", 480, 550);
			cashews.setFont(new Font("Serif", Font.BOLD, 20));
			add(cashews);
			// sets and adds the label that asks the user to select a nut
			GLabel introductionLabel = new GLabel("CHOOSE A NUT", 230, 200);
			introductionLabel.setFont(new Font("Serif", Font.BOLD, 80));
			Color labelColor = new Color(0, 102, 153);
			introductionLabel.setColor(labelColor);
			add(introductionLabel);
			
			Hello = new GLabel("Hello, "+UNstring+"!" +" Welcome! ",50,50);
			//adds the welcome message
			add(Hello);
			Hello.setFont(new Font("Serif", Font.BOLD, 26));
		}
		
		else if (state == 2)
		{
			// adds the background
			GImage backgroundImage2 = new GImage(MediaTools.loadImage("backgroundImage2.jpg"), 0, 0);
			backgroundImage2.scale(.9);
			backgroundImage2.sendToBack();
			add(backgroundImage2);
			
			if (peanutsChosen == true)
			{
				// adds and sets the label for importers of peanuts
				GLabel importersPeanuts = new GLabel("IMPORTERS OF PEANUTS", 350, 50);
				importersPeanuts.setFont(new Font("Dialog", Font.BOLD, 30));
				importersPeanuts.setColor(new Color(51, 102, 153));
				add(importersPeanuts);
				peanutBoxes.clear();		// clears the array list which stores boxes used to
				// display names of importers of peanuts
				// sets the y co-ordinate of the upper left corner of each box to hundred
				firstYCoordinate = 100;
				
				// keeps track of the number of boxes added adds six boxes for six importers
				int i = 0;
				while (i < 6)
				{
					box = new GRect(100, firstYCoordinate, 150, 25);
					add(box);
					peanutBoxes.add(box);
					
					// increments the y co-ordinate of the upper left corner of boxes by 50
					firstYCoordinate = firstYCoordinate + 50;
					i = i + 1;
				}
				// keeps track of the line to start storing country names at y co-ordinate of the lower left 
				//corner of the label that displays the name of each importer in the menu state
				j = 29;
				labelYCoordinate = 118;
				
				// adds names of countries from line 29 through 35 to the menu state
				// when peanuts are chosen
				while (j < 35)
				{
					//sets and adds names of peanuts importers
					countries = new GLabel(countryNames.get(j), 115, labelYCoordinate);
					countries.setFont(new Font("Serif", Font.BOLD, 15));
					add(countries);
					// increments y co-ordinate of the lower left corner of the 'countries' label
					labelYCoordinate = labelYCoordinate + 50;
					j = j + 1;							// increments number of lines by one
				}
			}
			else if (cashewsChosen == true)
			{
				// adds and sets the label for importers of cashews
				GLabel importersCashews = new GLabel("IMPORTERS OF CASHEWS", 350, 50);
				importersCashews.setFont(new Font("Dialog", Font.BOLD, 30));
				importersCashews.setColor(new Color(51, 102, 153));
				add(importersCashews);
				firstYCoordinate = 100;
				int i = 0;
				
				// adds eight boxes for eight importers
				while (i < 8)
				{
					box = new GRect(100, firstYCoordinate, 150, 25);
					add(box);
					cashewBoxes.add(box);
					firstYCoordinate = firstYCoordinate + 50;
					i = i + 1;
				}
				
				j = 11;
				
				labelYCoordinate = 118;
				
				// adds names of countries from line 11 through 18 to the menu state when cashews are chosen
				while (j < 19)
				{
					// sets and adds names of cashew importers
					countries = new GLabel(countryNames.get(j), 115, labelYCoordinate);
					countries.setFont(new Font("Serif", Font.BOLD, 15));
					add(countries);
					labelYCoordinate = labelYCoordinate + 50;
					j = j + 1;
				}
			}
			
			else if (treeNutsChosen == true)
			{
				// adds and sets the label for importers of tree nuts
				GLabel importersTreeNuts = new GLabel("IMPORTERS OF TREE NUTS", 350, 50);
				importersTreeNuts.setFont(new Font("Dialog", Font.BOLD, 30));
				importersTreeNuts.setColor(new Color(51, 102, 153));
				add(importersTreeNuts);
				firstYCoordinate = 100;
				int i = 0;
				
				// adds ten boxes for ten importers
				while (i < 10)
				{
					box = new GRect(100, firstYCoordinate, 150, 25);
					add(box);
					treeNutBoxes.add(box);
					firstYCoordinate = firstYCoordinate + 50;
					i = i + 1;
				}
				
				j = 0;
				labelYCoordinate = 118;
				
				// adds names of countries from line through 0 to 9 to the menu state
				// when tree nuts are chosen
				while (j < 10)
				{
					// sets and adds names of tree nuts importers
					countries = new GLabel(countryNames.get(j), 115, labelYCoordinate);
					countries.setFont(new Font("Serif", Font.BOLD, 15));
					add(countries);
					labelYCoordinate = labelYCoordinate + 50;
					j = j + 1;
				}
			}
			
			// adds the back button and the back label to state 2
			add(back);
			add(backLabel);
			
		}
		
		else if (state == 3)
		{
			GImage backgroundImage3 = new GImage(MediaTools.loadImage("backgroundImage3.png"), 0, 0);
			add(backgroundImage3);
			// sets the index the mouse was previously over to -1 as soon as state 3 is switched to
			previousIndex = -1;
			masterListIndex = 0;							// resets masterList index to 0
			countries.setLocation(500, 20);		// resets the position of the label for state 3
			add(back);
			add(backLabel);
			add(valueOfImports);
		}
		
		else if (state == 4)
		{
			//adds the background for state 4 
			GImage backgroundImage4 = new GImage(MediaTools.loadImage("backgroundImage4.jpg"), 0 ,0);
			backgroundImage4.scale(1.5);
			add(backgroundImage4);
			
			
			
			getGCanvas().setLayout(null);
			
			username = new TextField();						//creates textfield to take in username
			username.setBounds(450, 200, 200, 60);	//to set the size and location of textfield
			password = new TextField();
			password.setBounds(450, 300, 200, 60);
			getGCanvas().add(username);
			getGCanvas().add(password);
			username.setText(""); 							//to clear the text in textfield everytime user clicks on the sign in/create account button
			password.setText("");								//to allow the user to enter new text
			
			//to create the sign in button
			signIn = new GRect(640, 380, 120, 50);
			add(signIn);
			signInLabel = new GLabel("Sign in", 660, 410);
			signInLabel.setFont(new Font("Serif", Font.BOLD, 26));
			add(signInLabel);
			
			//for the create account button
			createAccount = new GRect(640, 450, 220, 50);
			createAccountlbl = new GLabel("Create Account", 660, 480); 
			createAccountlbl.setFont(new Font("Serif", Font.BOLD, 26));
			add(createAccountlbl);
			add(createAccount);
			
			usernameLbl = new GLabel ("Username : ", 300,240); //label to indicate textfield accepting username
			usernameLbl.setFont(new Font("Serif", Font.BOLD, 24));
			add(usernameLbl);
			passwordLbl = new GLabel ("Password : ", 300,340);//label to indicate textfield accepting password
			passwordLbl.setFont(new Font("Serif", Font.BOLD, 24));
			add(passwordLbl);
		}
		
		else if (state == 5)
		{
			removeAll();
			
			//adds the background for state 5
			GImage backgroundImage4 = new GImage(MediaTools.loadImage("backgroundImage4.jpg"), 0 ,0);
			backgroundImage4.scale(1.5);
			add(backgroundImage4);
			
			//no sign in button in state 5! 
			
getGCanvas().setLayout(null);
			
			username = new TextField();						//creates textfield to take in username
			username.setBounds(450, 200, 200, 60);	//to set the size and location of textfield
			password = new TextField();
			password.setBounds(450, 300, 200, 60);
			getGCanvas().add(username);
			getGCanvas().add(password);
			username.setText(""); 							//to clear the text in textfield everytime user clicks on the sign in/create account button
			password.setText("");								//to allow the user to enter new text
			
			
			//for the create account button
			createAccount = new GRect(640, 450, 220, 50);
			createAccountlbl = new GLabel("Create Account", 660, 480); 
			createAccountlbl.setFont(new Font("Serif", Font.BOLD, 26));
			add(createAccountlbl);
			add(createAccount);
			
			usernameLbl = new GLabel ("Username : ", 300,240); //label to indicate textfield accepting username
			usernameLbl.setFont(new Font("Serif", Font.BOLD, 24));
			add(usernameLbl);
			passwordLbl = new GLabel ("Password : ", 300,340);//label to indicate textfield accepting password
			passwordLbl.setFont(new Font("Serif", Font.BOLD, 24));
			add(passwordLbl);
		
		}
	}
	
	// method which displays graphs in the third state
	private void displayGraph(int n)
	{
		add(instructions);					// displays the instructions to the user on the screen
		add(arrowBody);
		add(upperArrow);
		add(lowerArrow);
		add(label);
		
		if (peanutsChosen == true)
		{
			countries.setLabel(countryNames.get(29 + n));
			countries.setFont(new Font("Serif", Font.BOLD, 20));
			add(countries);
			Rects.clear();					// clears the array list which stores bars of all bar graphs
			// sets the y co-ordinate of the upper left corner of the first bar in each bar graph to 50
			yStart = 50;
			masterListIndex = 0;
			initialGreen = 204;
			initialBlue = 204;
			// sets the X co-ordinate of the first marking(vertical line) along the X axis to 140
			markX = 110;
			// sets the first mark (numbers displayed below markings) to 0
			firstMark = 0;
			while (masterListIndex < 16)
			{
				// displays bars for peanut importers and multiplies the length of each bar by 5
				GRect bar = new GRect(60, yStart,
						(double)masterList.get(masterListIndex).get(29 + n) * 5, 10);
				bar.setFilled(true);
				Color pinkShade = new Color(255, initialGreen, initialBlue);
				bar.setColor(pinkShade);
				add(bar);
				Rects.add(bar);											// adds bars of bar graphs to the array list Rects
				initialGreen = initialGreen - 5;
				initialBlue = initialBlue - 5;
				//gets the value of imports from the chosen country for the succeeding year
				masterListIndex = masterListIndex + 1;
				yStart = yStart + 20;
				// creates markings(vertical lines) along the X axis
				GLine markings = new GLine(markX, 375, markX, 385);
				// creates marks (numbers below markings)
				GLabel marks = new GLabel("" + firstMark, markX - 55, 395);
				
				if (markX < 740)
				{
					markX = markX + 50;								// increments X co-ordinate for each marking by 50
					add(markings);
				}
				// adjusts the mark for each marking
				if (firstMark <= 130)
				{
					firstMark = firstMark + 10;			// increments the number displayed by ten
					add(marks);
				}
			}
			
			// sets the y co-ordinate of the lower left corner of the first label displaying years to 60
			int yearsYStart = 60;
			int j = 0;
			
			// gets years from the array list of years and displays them next to the bars
			while (j < 16)
			{
				int year = yearsList.get(j);
				GLabel yearLabel = new GLabel("" + year, 25, yearsYStart);
				add(yearLabel);
				j = j + 1;
				// shifts labels for succeeding years down by 20 pixels
				yearsYStart = yearsYStart + 20;
			}
			add(xAxis);
			add(lowerXArrow);
			add(upperXArrow);
			add(xAxisLabel);
			add(yAxis);
			add(leftYArrow);
			add(rightYArrow);
			add(yAxisLabel);
		}
		
		else if (cashewsChosen == true)
		{
			countries.setLabel(countryNames.get(11 + n));
			countries.setFont(new Font("Serif", Font.BOLD, 20));
			add(countries);
			Rects.clear();
			yStart = 50;
			masterListIndex = 0;
			initialGreen = 204;
			initialBlue = 204;
			// x co-ordinate of the starting point of each marking along the X-axis
			markX = 110;
			firstMark = 0;
			
			while (masterListIndex < 16)
			{
				// displays the bars for cashew importers
				GRect bar = new GRect(60, yStart,
						(double)masterList.get(masterListIndex).get(11 + n), 10);
				bar.setFilled(true);
				Color pinkShade = new Color(255, initialGreen, initialBlue);
				bar.setColor(pinkShade);
				add(bar);
				Rects.add(bar);
				masterListIndex = masterListIndex + 1;
				yStart = yStart + 20;
				initialGreen = initialGreen - 5;
				initialBlue = initialBlue - 5;
				// creates markings(vertical lines) along the X axis
				GLine markings = new GLine(markX, 375, markX, 385);
				
				// creates marks (numbers below markings)
				GLabel marks = new GLabel("" + firstMark, markX - 55, 395);
				
				if (markX < 740)
				{
					markX = markX + 50;
					add(markings);
				}
				
				if (firstMark <= 650)
				{
					firstMark = firstMark + 50;
					add(marks);
				}
			}
			
			int yearsYStart = 60;
			int j = 0;
			
			// displays years next to bars
			while (j < 16)
			{
				int year = yearsList.get(j);
				GLabel yearLabel = new GLabel("" + year, 25, yearsYStart);
				add(yearLabel);
				j = j + 1;
				yearsYStart = yearsYStart + 20;
			}
			add(xAxis);
			add(lowerXArrow);
			add(upperXArrow);
			add(xAxisLabel);
			add(yAxis);
			add(leftYArrow);
			add(rightYArrow);
			add(yAxisLabel);
		}
		
		else if (treeNutsChosen == true)
		{
			countries.setLabel(countryNames.get(n));
			countries.setFont(new Font("Serif", Font.BOLD, 20));
			add(countries);
			
			Rects.clear();
			yStart = 50;
			masterListIndex = 0;
			initialGreen = 204;
			initialBlue = 204;
			markX = 110;
			firstMark = 0;
			
			while (masterListIndex < 16)
			{
				// displays bars for tree nut importers
				GRect bar = new GRect(60, yStart,
						(double)masterList.get(masterListIndex).get(n), 10);
				bar.setFilled(true);
				Color pinkShade = new Color(255, initialGreen, initialBlue);
				bar.setColor(pinkShade);
				add(bar);
				Rects.add(bar);
				// creates markings(vertical lines) along the X axis
				GLine markings = new GLine(markX, 375, markX, 385);
				GLabel marks = new GLabel("" + firstMark, markX - 55, 395);// creates
				// creates marks (numbers below markings)
				if (markX < 750)
				{
					markX = markX + 50;						// increments X co-ordinate for each marking by 50
					add(markings);
				}
				
				// adjusts the mark for each marking
				if (firstMark <= 650)
				{
					firstMark = firstMark + 50;		// increments the number displayed by fifty
					add(marks);
				}
				
				int yearsYStart = 60;
				int j = 0;
				
				while (j < 16)
				{
					int year = yearsList.get(j);
					GLabel yearLabel = new GLabel("" + year, 25, yearsYStart);
					add(yearLabel);
					j = j + 1;
					yearsYStart = yearsYStart + 20;
				}
				initialGreen = initialGreen - 5;
				initialBlue = initialBlue - 5;
				masterListIndex = masterListIndex + 1;
				yStart = yStart + 20;
			}
			add(xAxis);
			add(lowerXArrow);
			add(upperXArrow);
			add(xAxisLabel);
			add(yAxis);
			add(leftYArrow);
			add(rightYArrow);
			add(yAxisLabel);
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		int currentIndex = 0;// sets the index of the bar the mouse is currently over to 0
		while (currentIndex < Rects.size())
		{
			GRect chosenBar = Rects.get(currentIndex);
			// checks if the mouse pointer is inside a bar and displays appropriate labels
			if (chosenBar.contains(e.getX(), e.getY()))
			{
				if (peanutsChosen == true)
				{
					valueOfImports.setLabel("Imports are valued at "
							+ masterList.get(currentIndex).get(29 + importValuesIndex)
							+ " million dollars.");
					valueOfImports.setFont(new Font("Serif", Font.BOLD, 20));
					
				}
				
				else if (cashewsChosen == true)
				{
					
					valueOfImports.setLabel("Imports are valued at "
							+ masterList.get(currentIndex).get(11 + importValuesIndex)
							+ " million dollars.");
					valueOfImports.setFont(new Font("Serif", Font.BOLD, 20));
					
				}
				
				else if (treeNutsChosen == true)
				{
					valueOfImports.setLabel("Imports are valued at "
							+ masterList.get(currentIndex).get(importValuesIndex)
							+ " million dollars.");
					valueOfImports.setFont(new Font("Serif", Font.BOLD, 20));
					
				}
				
				// moves the bar the mouse was previously over, to its original position
				// and the bar the mouse is currently over, to the right
				if (currentIndex != previousIndex && previousIndex != -1)
				{
					Rects.get(previousIndex).move(-10, 0);
					Rects.get(currentIndex).move(10, 0);
				}
				
				// makes sure that the first bar the mouse is over moves to the right
				if (previousIndex == -1)
				{
					Rects.get(currentIndex).move(10, 0);
				}
				// sets m equal to i so that it is the index of the last bar chosen
				previousIndex = currentIndex;
			}
			currentIndex = currentIndex + 1;// increments index by one
		}
	}
	
	//function overloading to print countryindex into file
	
	/*public void printIntoFileInt(double countryIndex)
	{
		
		try (PrintWriter printwriter =
				new PrintWriter(new FileWriter("account.txt", true)))
		{
			printwriter.print("," + countryIndex);
			// printwriter.println();
			printwriter.close();
		}
		catch (IOException e)
		{
			System.out.print("File not found: " + e.getMessage());
			return;
		}
	}*/
	
	//function used to write every new username and password into the file
	public void printIntoFile(String str1, String str2)
	{
		
		try (PrintWriter printwriter =
				new PrintWriter(new FileWriter("account.txt", true)))
		{
			
			printwriter.println(); 
			//makes sure every entry is written in a new line
			//username and password get written into the file seperated by a comma
			printwriter.print(str1 + "," + str2); 
			
			printwriter.close(); //to close the file
		}
		
		catch (IOException e)
		{
			System.out.print("File not found: " + e.getMessage());
			return;
		}
	}
}
