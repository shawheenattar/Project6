/* MULTITHREADING <MyClass.java>
 * EE422C Project 6 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * Slip days used: <0>
 * Spring 2019
 */
package assignment6;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.Thread;

public class BookingClient {
	
	Map<String, Integer> boxOffice;
	Theater movieTheater;

    /**
     * @param office  maps box office id to number of customers in line
     * @param theater the theater where the show is playing
     */
    public BookingClient(Map<String, Integer> office, Theater theater) {
    	this.boxOffice = office;
    	this.movieTheater = theater;
    }

    /**
     * Starts the box office simulation by creating (and starting) threads
     * for each box office to sell tickets for the given theater
     *
     * @return list of threads used in the simulation,
     * should have as many threads as there are box offices
     */
    public List<Thread> simulate() {
    	
    	List<Thread> threads = new ArrayList<Thread>();

    	//    	String office = "BX1";
//    	int clients = boxOffice.get(office);
//    	
//    	for (int i = 0; i < clients; i++) {
//    		Theater.Seat best = movieTheater.bestAvailableSeat();
//    		System.out.println(best);
//    		movieTheater.printTicket(office, best, i);
//        	System.out.println();
//
//    	}
    	
    	for (Map.Entry<String, Integer> entry : boxOffice.entrySet()) {
    		
    		String key = entry.getKey();
    		int value = (int) entry.getValue();
    		
    		BookingLoop booker = new BookingLoop(key, value, movieTheater);
    		threads.add(booker);
    		booker.start();
    	}
    	
        return threads;
    }

    public static void main(String[] args) {
        // TODO: Initialize test data to description
    	
    	Map<String, Integer> office = new HashMap<String, Integer>();
    	office.put("BX1", 3);
    	office.put("BX3", 3);
    	office.put("BX2", 4);
    	office.put("BX5", 3);
    	office.put("BX4", 3);

    	Theater t = new Theater(3, 5, "Ouija");
    	BookingClient book = new BookingClient(office, t);
    	
//    	Theater.Seat best = t.bestAvailableSeat();
//    	System.out.println(best);
//    	
//    	t.printTicket("box1", best, 1);
//    	Theater.Seat best2 = t.bestAvailableSeat();
//    	System.out.println(best2);
    	
    	book.simulate();
    	
    }
}

class BookingLoop extends Thread {
	
	String officeName;
	int clientNum;
	Theater theater;
	
	public BookingLoop(String s, int i, Theater t) {
		this.officeName = s;
		this.clientNum = i;
		this.theater = t;
	}
	
	public void run() {
//		System.out.println(this.officeName + " " + this.clientNum);
//		System.out.println(Thread.currentThread());
		
		for (int i = 0; i < clientNum; i++) {
			Theater.Seat best = theater.bestAvailableSeat();;
			synchronized(best) {
	    		theater.printTicket(officeName, best, i);
			}

    		
//    		synchronized(theater) {
//        		Theater.Ticket ticket = theater.bookTicket(officeName, i);
//			}
			
		}

	}

}
