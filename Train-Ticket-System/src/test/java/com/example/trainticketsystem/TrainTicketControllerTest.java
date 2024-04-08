package com.example.trainticketsystem;

import com.example.trainticketsystem.controller.TrainTicketController;
import com.example.trainticketsystem.model.TrainTicket;
import com.example.trainticketsystem.service.TicketStorage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainTicketControllerTest {

    @Mock
    private TicketStorage ticketStorage;

    @InjectMocks
    private TrainTicketController controller;
    
    @Mock
    private Model model;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testShowPurchasePage() {
        String viewName = controller.showPurchasePage();
        assertEquals("purchase", viewName);
    }

    @Test
    public void testPurchaseTicket_Success() {
        TrainTicket mockTicket = new TrainTicket("From", "To", "John", "Doe", "john@example.com", 100.0, "SectionA");
        Model model = new BindingAwareModelMap();
        controller.purchaseTicket("From", "To", "John", "Doe", "john@example.com", 100.0, "SectionA", model);
        verify(ticketStorage, times(1)).save(any(TrainTicket.class));
    }

    @Test
    public void testBackToPurchase() {
        String viewName = controller.backToPurchase();
        assertEquals("redirect:/api/train/purchase", viewName);
    }

    @Test
    public void testViewTickets() {
        Map<Integer, List<TrainTicket>> mockSectionA = new HashMap<>();
        mockSectionA.put(1, new ArrayList<>());
        Map<Integer, List<TrainTicket>> mockSectionB = new HashMap<>();
        mockSectionB.put(2, new ArrayList<>());
        when(ticketStorage.getSectionA()).thenReturn(mockSectionA);
        when(ticketStorage.getSectionB()).thenReturn(mockSectionB);
        Model model = new BindingAwareModelMap();
        String viewName = controller.viewTickets(model);
        assertNotNull(model.getAttribute("sectionA"));
        assertNotNull(model.getAttribute("sectionB"));
        assertEquals("viewtickets", viewName);
    }

    @Test
    public void testSaveModifiedTicket() {
        String seatNumber = "A1";
        String section = "SectionA";
        TrainTicket modifiedTicket = new TrainTicket("From", "To", "John", "Doe", "john@example.com", 100.0, "SectionA");
        when(ticketStorage.checkModifyISeatSectionAndChange(seatNumber, section, modifiedTicket)).thenReturn(null);
        String viewName = controller.saveModifiedTicket(seatNumber, section, modifiedTicket);
        assertEquals("redirect:/api/train/viewTickets", viewName);
    }
}
