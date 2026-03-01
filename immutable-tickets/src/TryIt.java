import com.example.tickets.IncidentTicket;
import com.example.tickets.TicketService;

import java.util.*;

public class TryIt {

    public static void main(String[] args) {
        TicketService svc = new TicketService();

        IncidentTicket t = svc.createTicket("TCK-1001", "reporter@example.com", "Payment failing on checkout");
        System.out.println("Created: " + t);

        // updates return new instances
        IncidentTicket t2 = svc.assign(t, "agent@example.com");
        IncidentTicket t3 = svc.escalateToCritical(t2);
        System.out.println("\nAfter updates (new instance): " + t3);
        System.out.println("Original unchanged: " + t);

        // tags list is not mutable from outside
        List<String> tags = t3.getTags();
        try {
            tags.add("HACKED_FROM_OUTSIDE");
            System.out.println("\nBUG: tags were mutable!");
        } catch (UnsupportedOperationException e) {
            System.out.println("\nExternal tag mutation blocked (immutable)");
        }
    }
}
