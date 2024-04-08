package com.example.trainticketsystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.trainticketsystem.model.TrainTicket;
import com.example.trainticketsystem.service.TicketStorage;

@Controller
@RequestMapping("/api/train")
public class TrainTicketController {

    @Autowired
    private TicketStorage ticketStorage;

    @GetMapping("/purchase")
    public String showPurchasePage() {
        return "purchase";
    }

    @PostMapping("/purchase")
    public String purchaseTicket(@RequestParam("from") String from,
                                 @RequestParam("to") String to,
                                 @RequestParam("firstName") String firstName,
                                 @RequestParam("lastName") String lastName,
                                 @RequestParam("email") String email,
                                 @RequestParam("price") double price,
                                 @RequestParam("seatSection") String seatSection,
                                 Model model) {
        TrainTicket ticket = new TrainTicket(from, to, firstName, lastName, email, price, seatSection);
        ticketStorage.save(ticket);
        return "redirect:/api/train/receipt/" + ticket.getId() + "/" + seatSection;
    }
    @GetMapping("/receipt/{ticketId}/{seatSection}")
    public String showReceiptPage(@PathVariable("ticketId") int ticketId, 
                                  @PathVariable("seatSection") String seatSection,
                                  Model model) {
        TrainTicket ticket = ticketStorage.getTicketBySectionAndId(ticketId,seatSection);
        if (ticket != null) {
            model.addAttribute("ticket", ticket);
            model.addAttribute("seatSection", seatSection);
            return "receipt";
        } else {
            return "error";
        }
    }

    @GetMapping("/backToPurchase")
    public String backToPurchase() {
        return "redirect:/api/train/purchase";
    }
    
    @GetMapping("/viewTickets")
    public String viewTickets(Model model) {
        Map<Integer, List<TrainTicket>> sectionA = ticketStorage.getSectionA();
        Map<Integer, List<TrainTicket>> sectionB = ticketStorage.getSectionB();
        model.addAttribute("sectionA", sectionA);
        model.addAttribute("sectionB", sectionB);
        return "viewtickets";
    }
    
    @GetMapping("/modify/{seatNumber}/{section}")
    public String modifyTrainTicket(@PathVariable String seatNumber, @PathVariable String section, Model model) {
        TrainTicket ticket = ticketStorage.modifySeatBasedOnSection(seatNumber, section);
        model.addAttribute("ticket", ticket);
        model.addAttribute("seatNumber", seatNumber);
        model.addAttribute("section", section);
        return "modify";
    }
    
    @GetMapping("/delete/{seatNumber}/{section}")
    public String deleteTrainTicket(@PathVariable String seatNumber, @PathVariable String section, Model model) {
        TrainTicket ticket = ticketStorage.deleteUser(seatNumber, section);
        model.addAttribute("ticket", ticket);
        return "purchase";
    }
    
    @GetMapping("/viewuserandseat")
    public String viewUserAndSeat(Model model) {
        Map<Integer, List<TrainTicket>> sectionA = ticketStorage.getSectionA();
        Map<Integer, List<TrainTicket>> sectionB = ticketStorage.getSectionB();
        model.addAttribute("sectionA", sectionA);
        model.addAttribute("sectionB", sectionB);
        return "viewtickets";
    }
    
    @PostMapping("/modify/{seatNumber}/{section}")
    public String saveModifiedTicket(@PathVariable String seatNumber, @PathVariable String section, @ModelAttribute TrainTicket modifiedTicket) {
        ticketStorage.checkModifyISeatSectionAndChange(seatNumber, section, modifiedTicket);
        return "redirect:/api/train/viewTickets";
    }
}
