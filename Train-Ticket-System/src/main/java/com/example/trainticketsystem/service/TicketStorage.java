package com.example.trainticketsystem.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.trainticketsystem.model.TrainTicket;

@Service
public class TicketStorage {
    
    @Autowired 
    private TrainTicket trainticket;

    private Map<Integer, List<TrainTicket>> sectionA;
    private Map<Integer, List<TrainTicket>> sectionB;
    private int sectionIdA;
    private int sectionIdB;

    public TicketStorage() {
        this.sectionA = new HashMap<>();
        this.sectionB = new HashMap<>();
        this.sectionIdA = 0;
        this.sectionIdB = 0;
    }

    public void save(TrainTicket ticket) {
        if (ticket.getSeatSection().equals("SectionA")) {
            sectionIdA++;
            ticket.setId(sectionIdA);
            List<TrainTicket> ticketsForSeat = sectionA.getOrDefault(sectionIdA, new ArrayList<>());
            ticketsForSeat.add(ticket);
            sectionA.put(sectionIdA, ticketsForSeat);
        } else if (ticket.getSeatSection().equals("SectionB")) {
            sectionIdB++;
            ticket.setId(sectionIdB);
            List<TrainTicket> ticketsForSeat = sectionB.getOrDefault(sectionIdB, new ArrayList<>());
            ticketsForSeat.add(ticket);
            sectionB.put(sectionIdB, ticketsForSeat);
        }
    }

 

    public Map<Integer, List<TrainTicket>> getSectionA() {
        return sectionA;
    }

    public Map<Integer, List<TrainTicket>> getSectionB() {
        return sectionB;
    }

    public TrainTicket modifySeatBasedOnSection(String seatNumber, String section) {
        try {
            Integer seatNumberInteger = Integer.parseInt(seatNumber);

            if (section.equals("A")) {
                if (sectionA.containsKey(seatNumberInteger)) {
                    List<TrainTicket> tickets = sectionA.get(seatNumberInteger);
                    if (!tickets.isEmpty()) {
                        return tickets.get(0);
                    }
                }
            } else if (section.equals("B")) {
                if (sectionB.containsKey(seatNumberInteger)) {
                    List<TrainTicket> tickets = sectionB.get(seatNumberInteger);
                    if (!tickets.isEmpty()) {
                        return tickets.get(0);
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TrainTicket deleteUser(String seatNumber, String section) {
        try {
            Integer seatNumberInteger = Integer.parseInt(seatNumber);

            if (section.equals("A")) {
                if (sectionA.containsKey(seatNumberInteger)) {
                    List<TrainTicket> tickets = sectionA.get(seatNumberInteger); 
                    sectionA.remove(seatNumberInteger, tickets);
                    return null;
                }
            } else if (section.equals("B")) {
                if (sectionB.containsKey(seatNumberInteger)) {
                    List<TrainTicket> tickets = sectionB.get(seatNumberInteger);
                    sectionB.remove(seatNumberInteger, tickets);
                    return null;
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

   

    public TrainTicket checkModifyISeatSectionAndChange(String seatNumber, String section, TrainTicket modifiedTicket) {
        try {
            Integer seatNumberInteger = Integer.parseInt(seatNumber);

            if (modifiedTicket.getSeatSection().equals("SectionA")) {
                List<TrainTicket> ticketsA = sectionA.get(seatNumberInteger); 
                List<TrainTicket> ticketsB = sectionB.get(seatNumberInteger); 
                if(ticketsB != null) {
                    sectionB.remove(seatNumberInteger);
                    sectionIdA++;
                    ticketsB.get(0).setSeatSection("SectionA");
                    ticketsB.get(0).setId(sectionIdA);
                    sectionA.put(sectionIdA, ticketsB);
                } else if(ticketsA != null) {
                    return ticketsA.get(0);
                }
            } else if (modifiedTicket.getSeatSection().equals("SectionB")) {
                List<TrainTicket> ticketsB = sectionB.get(seatNumberInteger); 
                List<TrainTicket> ticketsA = sectionA.get(seatNumberInteger); 
                if(ticketsA != null) {
                    sectionA.remove(seatNumberInteger);
                    sectionIdB++;
                    ticketsA.get(0).setSeatSection("SectionB");
                    ticketsA.get(0).setId(sectionIdB);

                    sectionB.put(sectionIdB, ticketsA);
                } else if(ticketsB != null) {
                    return ticketsB.get(0);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TrainTicket getTicketBySectionAndId(int ticketId, String seatSection) {
        if (seatSection.equals("SectionA")) {
            List<TrainTicket> ticketsA = sectionA.getOrDefault(ticketId, new ArrayList<>());
            for (TrainTicket ticket : ticketsA) {
                if (ticket.getId() == ticketId) {
                    return ticket;
                }
            }
        } else if (seatSection.equals("SectionB")) {
            List<TrainTicket> ticketsB = sectionB.getOrDefault(ticketId, new ArrayList<>());
            for (TrainTicket ticket : ticketsB) {
                if (ticket.getId() == ticketId) {
                    return ticket;
                }
            }
        }
        return null;
    }

}
