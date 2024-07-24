package TicketReservation;

import java.util.*;

public class TicketBooker {

	//63 Berths (Upper,Middle,Lower)+(18 RAC passengers)
	//10 waiting list tickets ->21 L,21 M.21 U ,18 RAC,10 WL
	
	static int availableLowerBerths=21;
	static int availableMiddleBerths=21;
	static int availableUpperBerths=21;
	static int availableRacTickets=18;
	static int availableWaitingList=10;
	
	static Queue<Integer> waitingList=new LinkedList<>();//Queue of  WL Passengers
	static Queue<Integer> racList=new LinkedList<>();//Queue of  WL Passengers
	static List<Integer> bookedTicketList=new ArrayList <>(); //list of Booked TicketPassengers
	
	static List<Integer>LowerBerthPositions =new ArrayList<>(Arrays.asList(1));//Normally 1,2,...21
	static List<Integer>UpperBerthPositions =new ArrayList<>(Arrays.asList(1));//Normally 1,2,...21
	static List<Integer>MiddleBerthPositions =new ArrayList<>(Arrays.asList(1));//Normally 1,2,...21
	static List<Integer>racPositions =new ArrayList<>(Arrays.asList(1));//Normally 1,2,...18
	static List<Integer>waitingListPositions =new ArrayList<>(Arrays.asList(1));//Normally 1,2,...10	
	
	static Map<Integer,Passenger> passengers=new HashMap<>();
	
	//book ticket
	public void bookTicket(Passenger p,int berthInfo,String allotedBerth) {
		//Assign the seat number and type of berth(L,U,M)
		p.number=berthInfo;
		p.alloted=allotedBerth;
		//add passenger to map
		passengers.put(p.passengerId,p);
		//add passenger id to the list of booked tickets
		bookedTicketList.add(p.passengerId);
		System.out.println(".....................Booked succesfully!........................");
	}
	
	//adding to RAC
	public void addToRAC(Passenger p,int racInfo,String allotedRAC) {
		//assign seat number and type(RAC)
		p.number=racInfo;
		p.alloted=allotedRAC;
		//Add Passenger to map
		passengers.put(p.passengerId,p);
		//Add Passenger id to the queuq of Rac Tickets
		racList.add(p.passengerId);
		//Decrease available Rac tickets by 1
		availableRacTickets--;
		//remove the position that was allowed to the passenger
		racPositions.remove(0);
		
		System.out.println("..................Rac Added Successfully........................");
	}
		
	   //adding to WL 
		public void addToWaitingList(Passenger p,int waitingListInfo,String allotedWL) {
			
			//assign seat number and type(WL)
			p.number=waitingListInfo;
			p.alloted=allotedWL;
			//add passenger to the map
			passengers.put(p.passengerId, p);
			//add passenger id to the queue of WL tickets
			waitingList.add(p.passengerId);
			//decrease availability WL tickets by 1
			availableWaitingList--;
			//remove the position that was alloted to the passenger
			waitingListPositions.remove(0);
			
			System.out.println("*************added to waitinglist successfully***************");
		}
		//cancel ticket
		public void cancelTicket(int passengerId) {
			
			//Remove the passenger from the map
			Passenger p=passengers .get(passengerId);
			passengers.remove(Integer.valueOf(passengerId));
			
			//Remove the booked tickets from the list
			bookedTicketList.remove(Integer.valueOf(passengerId));
			
			
			//take the booked position which is now free
			int positionBooked=p.number;
			
			System.out.println("............Cancelled Successfully...........");
			
		//add the free position to the corresponding type of list (either L,M,U)
		if (p.alloted.equals("L")) {
			availableLowerBerths++;
			LowerBerthPositions.add(positionBooked);
		}
		else if(p.alloted.equals("M")) {
			availableMiddleBerths++;
			MiddleBerthPositions.add(positionBooked);
			
		}
		if(racList.size()>0) {
			
			Passenger passengerFromRAC=passengers.get(racList.poll());
			int positionRac=passengerFromRAC.number;
			racPositions.add(positionRac);
			racList.remove(Integer.valueOf(passengerFromRAC.passengerId));
			availableRacTickets++;	
		}
		if(waitingList.size()>0) {
			Passenger passengerFromWaitingList=passengers.get(waitingList.poll());
			int positionWL=passengerFromWaitingList.number;
			waitingListPositions.add(positionWL);
			waitingList.remove(Integer.valueOf(passengerFromWaitingList.passengerId));
			
			passengerFromWaitingList.number=racPositions.get(0);
			passengerFromWaitingList.alloted="RAC";
			racPositions.remove(0);
			racList.add(passengerFromWaitingList.passengerId);
			
			availableWaitingList++;
			availableRacTickets--;
			
			
		}
		   // now we have a passenger from RAc to whom we can book a ticket, 
          //so book the cancelled ticket to the RAC passenger
		  Main.bookTicket(p);
  }
		


public void printAvailable() {
	System.out.println("Available Lower Berths" +availableLowerBerths);
	System.out.println("Available Middle Berths" +availableMiddleBerths);
	System.out.println("Available Upper Berths" +availableUpperBerths);
	System.out.println("Available RACs" +availableRacTickets);
	System.out.println("Available Waiting List" +availableWaitingList);
	System.out.println("*************************************************");

}
//print all occupied passengers from all types including WL
	public void printPassengers() {
		if (passengers.size()==0) {
			System.out.println("No details of Passengers");
			return;
		}
		for(Passenger p:passengers.values())
		{
			System.out.println("PASSENGER ID"+p.passengerId);
			System.out.println("Name"+p.name);
			System.out.println("Age"+p.age);
			System.out.println("Status"+p.number+p.alloted);
			System.out.println("*****************************");
			
		}
	}

}
