/* MULTITHREADING Theater.java
 * EE422C Project 6 submission by
 * Shawheen Attar
 * sma3464
 * 16190
 * Slip days used: 0
 * Spring 2019
 */

package assignment6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Theater {

	List<Seat> seats = new ArrayList<Seat>();
	List<Seat> takenSeats = new ArrayList<Seat>();
	List<Ticket> booked = new ArrayList<Ticket>();
	
	String showName;
	int totalClients;
	
	boolean soldOutPosted;

    /**
     * the delay time you will use when print tickets
     */
    private int printDelay = 50; // 50 ms.  Use it in your Thread.sleep()

    public void setPrintDelay(int printDelay) {
        this.printDelay = printDelay;
    }

    public int getPrintDelay() {
        return printDelay;
    }

    /**
     * Represents a seat in the theater
     * A1, A2, A3, ... B1, B2, B3 ...
     */
    static class Seat {
        private int rowNum;
        private int seatNum;

        public Seat(int rowNum, int seatNum) {
            this.rowNum = rowNum;
            this.seatNum = seatNum;
        }

        public int getSeatNum() {
            return seatNum;
        }

        public int getRowNum() {
            return rowNum;
        }        

        @Override
        public String toString() {
            String result = "";
            int tempRowNumber = rowNum + 1;
            do {
                tempRowNumber--;
                result = ((char) ('A' + tempRowNumber % 26)) + result;
                tempRowNumber = tempRowNumber / 26;
            } while (tempRowNumber > 0);
            result += seatNum;
            return result;
        }
        
        @Override
        public boolean equals(Object o) {
        	Seat other = (Seat) o;
        	if (other.getRowNum() == this.getRowNum() &&
        			other.getSeatNum() == this.getSeatNum()) {
        		return true;
        	}
        	return false;
        }

    }

    /**
     * Represents a ticket purchased by a client
     */
    static class Ticket {
        private String show;
        private String boxOfficeId;
        private Seat seat;
        private int client;
        public static final int ticketStringRowLength = 31;


        public Ticket(String show, String boxOfficeId, Seat seat, int client) {
            this.show = show;
            this.boxOfficeId = boxOfficeId;
            this.seat = seat;
            this.client = client;
        }

        public Seat getSeat() {
            return seat;
        }

        public String getShow() {
            return show;
        }

        public String getBoxOfficeId() {
            return boxOfficeId;
        }

        public int getClient() {
            return client;
        }

        @Override
        public String toString() {
            String result, dashLine, showLine, boxLine, seatLine, clientLine, eol;

            eol = System.getProperty("line.separator");

            dashLine = new String(new char[ticketStringRowLength]).replace('\0', '-');

            showLine = "| Show: " + show;
            for (int i = showLine.length(); i < ticketStringRowLength - 1; ++i) {
                showLine += " ";
            }
            showLine += "|";

            boxLine = "| Box Office ID: " + boxOfficeId;
            for (int i = boxLine.length(); i < ticketStringRowLength - 1; ++i) {
                boxLine += " ";
            }
            boxLine += "|";

            seatLine = "| Seat: " + seat.toString();
            for (int i = seatLine.length(); i < ticketStringRowLength - 1; ++i) {
                seatLine += " ";
            }
            seatLine += "|";

            clientLine = "| Client: " + client;
            for (int i = clientLine.length(); i < ticketStringRowLength - 1; ++i) {
                clientLine += " ";
            }
            clientLine += "|";

            result = dashLine + eol +
                    showLine + eol +
                    boxLine + eol +
                    seatLine + eol +
                    clientLine + eol +
                    dashLine;

            return result;
        }
    }

    public Theater(int numRows, int seatsPerRow, String show) {
    	
    	for (int i = 0; i < numRows; i++) {
    		for (int j = 1; j <= seatsPerRow; j++) {
    			seats.add(new Seat(i, j));
    		}
    	}
    	    	
    	this.showName = show;
    	
    	this.soldOutPosted = false;
    }
    
    public Ticket bookTicket(String boxOfficeId) {
    	
    	Seat best = bestAvailableSeat();
    	
    	if (best != null) {
    		totalClients++;
        	Ticket t = printTicket(boxOfficeId, best, totalClients);
        	return t;
    	}
        		
    	return null;
    }

    /**
     * Calculates the best seat not yet reserved
     *
     * @return the best seat or null if theater is full
     */
    public Seat bestAvailableSeat() {
    	
    	
    	for (Seat s: seats) {
    		
    		if (this.takenSeats.size() == 0) return s;
    		
    		if (takenSeats.contains(s)) {
    			continue;
    		} else {
    			return s;
    		}
    		
    	}
    	
        return null;
    }

    /**
     * Prints a ticket for the client after they reserve a seat
     * Also prints the ticket to the console
     *
     * @param seat a particular seat in the theater
     * @return a ticket or null if a box office failed to reserve the seat
     */
    public Ticket printTicket(String boxOfficeId, Seat seat, int client) {
    	
    	if (seat == null) {
    		return null;
    	}
    	
    	Ticket newTicket = new Ticket(showName, boxOfficeId, seat, client);
    	takenSeats.add(seat);
    	booked.add(newTicket);
    	
    	System.out.println(newTicket);
    	
        return newTicket;
    }

    /**
     * Lists all tickets sold for this theater in order of purchase
     *
     * @return list of tickets sold
     */
    public List<Ticket> getTransactionLog() {
        // TODO: Implement this method
        return booked;
    }
}
