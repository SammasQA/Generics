package ru.netology.javaqa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.*;

public class AviaSoulsTest {

    private AviaSouls manager;
    private Ticket ticket1;
    private Ticket ticket2;
    private Ticket ticket3;
    private Ticket ticket4;
    private Ticket ticket5;

    @BeforeEach
    void setUp() {
        manager = new AviaSouls();

        ticket1 = new Ticket("MSK", "SPB", 5000, 1000, 1200); // 200 мин
        ticket2 = new Ticket("MSK", "SPB", 4500, 900, 1100);  // 200 мин
        ticket3 = new Ticket("MSK", "SPB", 6000, 800, 1100);  // 300 мин
        ticket4 = new Ticket("MSK", "KZN", 7000, 1200, 1500);
        ticket5 = new Ticket("MSK", "SPB", 4800, 1300, 1450); // 150 мин

        manager.add(ticket1);
        manager.add(ticket2);
        manager.add(ticket3);
        manager.add(ticket4);
        manager.add(ticket5);
    }


    @Test
    void testTicketCompareTo() {
        assertTrue(ticket2.compareTo(ticket1) < 0);
        assertTrue(ticket1.compareTo(ticket3) < 0);
        assertTrue(ticket3.compareTo(ticket2) > 0);
        assertEquals(0, ticket1.compareTo(ticket1));
    }


    @Test
    void testSearchSortedByPrice() {
        Ticket[] expected = {ticket2, ticket5, ticket1, ticket3};
        Ticket[] actual = manager.search("MSK", "SPB");
        assertArrayEquals(expected, actual);
    }

    @Test
    void testSearchNoMatches() {
        Ticket[] actual = manager.search("MSK", "XYZ");
        assertEquals(0, actual.length);
    }

    @Test
    void testSearchSingleMatch() {
        AviaSouls smallManager = new AviaSouls();
        Ticket single = new Ticket("A", "B", 1000, 0, 100);
        smallManager.add(single);
        Ticket[] actual = smallManager.search("A", "B");
        assertArrayEquals(new Ticket[]{single}, actual);
    }


    @Test
    void testTicketTimeComparator() {
        Comparator<Ticket> comparator = new TicketTimeComparator();

        assertTrue(comparator.compare(ticket5, ticket1) < 0); // 150 < 200
        assertTrue(comparator.compare(ticket2, ticket5) > 0); // 200 > 150
        assertEquals(0, comparator.compare(ticket1, ticket2)); // равное время → 0
        assertTrue(comparator.compare(ticket1, ticket3) < 0); // 200 < 300
    }


    @Test
    void testSearchAndSortByTime() {
        Comparator<Ticket> comparator = new TicketTimeComparator();


        Ticket[] expected = {ticket5, ticket1, ticket2, ticket3};
        Ticket[] actual = manager.searchAndSortBy("MSK", "SPB", comparator);

        assertArrayEquals(expected, actual);
    }

    @Test
    void testSearchAndSortByPriceWithCustomComparator() {
        Comparator<Ticket> priceComparator = Comparator.comparingInt(Ticket::getPrice);
        Ticket[] expected = {ticket2, ticket5, ticket1, ticket3};
        Ticket[] actual = manager.searchAndSortBy("MSK", "SPB", priceComparator);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testSearchAndSortByNoMatches() {
        Comparator<Ticket> comparator = new TicketTimeComparator();
        Ticket[] actual = manager.searchAndSortBy("MSK", "XYZ", comparator);
        assertEquals(0, actual.length);
    }
}