
package railwayReservation;
import java.util.*;
public class Admin {
	public static LinkedList<Train> trainList = new LinkedList<>();
	String securityKey = "admin123";

	void displayAdminMenu() {
		int choice = 0;
		do {
			System.out.println("\n\t***Admin Menu***");
			System.out.println("\t1. Add  trains ");
			System.out.println("\t2. Remove trains.");
			System.out.println("\t3. Display all trains.");
			System.out.println("\t4. Business analysis");//te functions nantar decide karu
			System.out.println("\t5. Exit");
			Scanner s = new Scanner(System.in);
			int status = 0;
			do {
				status = 0;
				try {
					System.out.print("\n\tEnter your choice: ");
					choice = s.nextInt(); //s.nextLine();
				}catch(InputMismatchException e) {
					System.out.println("\tThis choice is invalid.Enter a number between 1 to 5");
					status = 1;
					s.nextLine();
				}
				if(status==0) s.nextLine();
			}while(status==1);
			switch(choice) {
			case 1: this.addTrains();
					break;
			case 2: this.removeTrains();
					break;
			case 3: this.displayTrains();
					break;
			case 4: System.out.println("\n\tWhat do you want to analyze?");
					break;
			case 5: System.out.println("\n\tClosing admin window....");
					break;
			default: System.out.println("\n\tInvalid choice!");
					break;
			}
		}while(choice!=5);
	}
	
	public void addTrains() {
		Scanner sc = new Scanner(System.in);
		char choice;
		Validation v = new Validation();
		do {
			System.out.print("\n\t\tEnter the train number: ");	
			int trainNo=sc.nextInt();
			sc.nextLine();
			System.out.print("\n\t\tEnter the name of the train: ");
			String trainName=sc.nextLine();
			boolean isSrc=false; boolean isDest=false;
			String source,destination;
			do {
				do {
					System.out.print("\n\t\tEnter the source place of the train: ");
					source=sc.nextLine();
					isSrc=v.placeValidation(source);
					if(!isSrc) {
						System.out.println("\t\tThis place is invalid.");
					}
				}while(!isSrc);
				do {
					System.out.print("\n\t\tEnter the destination place of the train: ");
					destination=sc.nextLine();
					isDest=v.placeValidation(destination);
					if(!isDest) {
						System.out.println("\t\tThis place is invalid.");
					}
				}while(!isDest);
				if(source.equalsIgnoreCase(destination)) {
					System.out.println("\t\tSource and Destination cannot be same.Please enter correct source and destination");
				}
			}while(source.equalsIgnoreCase(destination));
			boolean isValid=false; String date;//int status=0;
			do {
				System.out.print("\n\t\tEnter date of journey in dd/mm/yyyy format only: ");
				date = sc.nextLine();
				isValid=v.dateValidation(date);
				if(!isValid) {
					System.out.println("\t\tThe date format is invalid.");
				}
		
			}while(!isValid);
		
			boolean deptTime=false; String departure;
			do {
				System.out.print("\n\t\tEnter departure time in 24 hour format as hh:mm only: ");
				departure = sc.nextLine();
				deptTime=v.timeValidation(departure);
				if(!deptTime) {
					System.out.println("\t\tThe time format is invalid.");
				}
			}while(!deptTime);
		
			boolean arrTime=false; String arrival;
			do {
				System.out.print("\n\t\tEnter arrival time in 24 hour format as hh:mm only: ");
				arrival = sc.nextLine();
				arrTime=v.timeValidation(arrival);
				if(!arrTime) {
					System.out.println("\t\tThe time format is invalid.");
				}
				//sc.nextLine();
			}while(!arrTime);
		
		
			System.out.print("\n\t\tEnter the maximum capacity of the train: ");
			int maxSeat=sc.nextInt(); sc.nextLine();
			System.out.print("\n\t\tEnter the maximum waiting list capacity of the train: ");
			int maxWait=sc.nextInt(); sc.nextLine();
			System.out.print("\n\t\tIs the train sleepercoach?(y/n): ");
			String slp = sc.nextLine();
			boolean sl = slp.equals("y")||slp.equals("Y") ? true:false;
			System.out.print("\n\t\tIs the train A/C?(y/n): ");
			String ac = sc.nextLine();
			boolean a = ac.equals("y")||ac.equals("Y") ? true:false;
			System.out.print("\n\t\tEnter price of ticket: ");
			int price = sc.nextInt(); sc.nextLine();
			Train t=new Train(trainNo,trainName,a,sl,date,source,destination,maxSeat,maxWait, departure, arrival,price);
			trainList.add(t);
			System.out.println("\n\t\tTrain "+trainNo+" "+trainName+" added successfully");
			
			System.out.print("\n\t\tDo you want to continue adding trains? (y/n): ");
			choice=sc.next().charAt(0);sc.nextLine();
			//sc.close();
		
		}while(choice=='y'||choice=='Y');
	}
	
	public void removeTrains() {
		Scanner scan = new Scanner(System.in);
		int trainNo;
		int flag=0;
		String ch = "y";
		do {
			flag = 0;
			System.out.print("\n\t\tEnter the train number which you want to remove: ");
			trainNo = scan.nextInt(); scan.nextLine();
			for(Train t1 : trainList) {
				if(t1.trainNo==trainNo) {
					trainList.remove(t1);
					System.out.println("\n\t\tTrain no. "+trainNo+" successfully removed.");
					flag=1;
					break;
				}
			}
			if(flag!=1) {
				System.out.println("\t\tTrain not found.");
			}
			System.out.print("\n\t\tDo you want to continue removing trains? (y/n): ");
			ch = scan.nextLine();
		}while(ch.equals("y")||ch.equals("Y"));
		//System.out.println("Successfully removed.");
		//scan.close();
	}
	void displayTrains() {
		if(trainList.size()==0) {
			System.out.println("\n\t\tNo trains are added yet. Please add the trains to check the availability.");
		}
		else {
			System.out.println("\n\t\tFollowing are the details of all available trains:");
			System.out.println("\t\t________________________________________________________________________________________________________________________");//85
			System.out.println("\t\t|no.|        name        |   date   |     source    |  destination  |Arrival|departure|price|  ac  |sleeper|seats|waiting|");
			System.out.println("\t\t|___|____________________|__________|_______________|_______________|_______|_________|_____|______|_______|_____|_______|");
			for(Train t : trainList) {
				System.out.format("\t\t|%3d|%20s|%10s|%15s|%15s|%7s|%9s|%5d|%6s|%7s|%5d|%7d|\n",t.trainNo,t.trainName,t.date,t.Source,t.Destination,t.arrivalTime,t.departureTime,t.price,t.ac,t.sleeperCoach,t.maxSeat,t.maxWait);
			}
			System.out.println("\t\t|___|____________________|__________|_______________|_______________|_______|_________|_____|______|_______|_____|_______|");
		}
	}
}

