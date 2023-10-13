import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FerryTicketGUI extends JFrame {
    private JLabel fromLabel, toLabel, dateLabel, adultsLabel, childrenLabel;
    private JComboBox<String> fromComboBox, toComboBox;
    private JTextField dateTextField;
    private JCheckBox returnTicketCheckBox;
    private JButton bookTicketsButton;
    private JTextField adultsTextField, childrenTextField;

    public FerryTicketGUI() {
        // Set frame properties
        setTitle("Ferry Ticket Booking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400); // Adjusted height

        // Initialize components
        fromLabel = new JLabel("From:");
        toLabel = new JLabel("To:");
        dateLabel = new JLabel("Departure Date:");
        adultsLabel = new JLabel("Adults:");
        childrenLabel = new JLabel("Children:"); // New label

        String[] locations = {"Pangkor Island", "Marina Island", "Teluk Batik"};
        fromComboBox = new JComboBox<>(locations);
        toComboBox = new JComboBox<>(locations);

        dateTextField = new JTextField(10);
        adultsTextField = new JTextField(5);
        childrenTextField = new JTextField(5); // New input field

        returnTicketCheckBox = new JCheckBox("Return Ticket");
        bookTicketsButton = new JButton("Book Tickets");

        // Set layout manager
        setLayout(new FlowLayout());

        // Add components to the frame
        add(fromLabel);
        add(fromComboBox);
        add(toLabel);
        add(toComboBox);
        add(dateLabel);
        add(dateTextField);
        add(adultsLabel);
        add(adultsTextField);
        add(childrenLabel);
        add(childrenTextField); // Add the children input field
        add(returnTicketCheckBox);
        add(bookTicketsButton);


        bookTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String from = fromComboBox.getSelectedItem().toString();
                String to = toComboBox.getSelectedItem().toString();
                String date = dateTextField.getText();
                int numAdults = 0;
                int numChildren = 0;

                try {
                    numAdults = Integer.parseInt(adultsTextField.getText());
                    // Parse children input if provided
                    if (!childrenTextField.getText().isEmpty()) {
                        numChildren = Integer.parseInt(childrenTextField.getText());
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numbers of adults and children.");
                    return;
                }

                boolean isReturnTicket = returnTicketCheckBox.isSelected();

                if (from.isEmpty() || to.isEmpty() || date.isEmpty() || adultsTextField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all required fields.");
                    return;
                }

                if (from.equals(to)) {
                    JOptionPane.showMessageDialog(null, "'From' and 'To' destinations cannot be the same.");
                    return;
                }

                boolean isAvailable = checkAvailability(from, to, date, numAdults + numChildren);

                if (isAvailable) {
                    double ticketPrice = calculateTicketPrice(from, to, date, numAdults, numChildren, isReturnTicket);

                    // Format the booking details in a ticket-style format
                    String bookingDetails = String.format(
                            "*********** Ferry Ticket ***********\n" +
                                    "From: %s\nTo: %s\nDate: %s\nAdults: %d\nChildren: %d\nReturn Ticket: %s\n" +
                                    "Total Price: RM%.2f\n************************************",
                            from, to, date, numAdults, numChildren, isReturnTicket ? "Yes" : "No", ticketPrice);

                    JOptionPane.showMessageDialog(null, bookingDetails, "Booking Details", JOptionPane.PLAIN_MESSAGE);

                    storeBooking(from, to, date, numAdults, numChildren, ticketPrice, isReturnTicket);
                } else {
                    JOptionPane.showMessageDialog(null, "Tickets are not available for the selected route and date.");
                }
            }
        });
    }
    private boolean checkAvailability(String from, String to, String date, int numPassengers) {
        // Implement your own availability logic here
        return true;
    }

    private double calculateTicketPrice(String from, String to, String date, int numAdults, int numChildren, boolean isReturnTicket) {

        double adultBasePrice = 20.0;
        double childBasePrice = 10.0;

        double totalPrice = (adultBasePrice * numAdults) + (childBasePrice * numChildren); // Adjusted pricing for adults and children
        if (isReturnTicket) {
            totalPrice *= 2;
        }
        return totalPrice;
    }
    private void storeBooking(String from, String to, String date, int numAdults, int numChildren, double ticketPrice, boolean isReturnTicket) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FerryTicketGUI().setVisible(true);
            }
        });
    }
}
