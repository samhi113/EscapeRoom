import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EscapeRoom {

    /*
    Solution:
    1. Go to lamp
    2. Turn on lamp
    3. Go to dresser
    4. Go to door
    5. Use key on lock
    6. Open door
    7. Walk out and you won
     */

    static JFrame frame;
    static JPanel panel;
    static JButton startButton, button1, button2, button3, button4;
    static JTextArea textLabel, imageLabel;

    static Icon homeIcon = UIManager.getIcon("FileChooser.homeFolderIcon");
    static Icon errorIcon = UIManager.getIcon("OptionPane.errorIcon");

    static String currentPlace = "door";
    static String[] rooms = {"bed", "door", "dresser", "lamp"};
    static String desiredRoom;
    static boolean hasKey, lightOn;
    static int thingsPunched = 0;

    public static void main(String[] args) {

        new EscapeRoom();

    }

    public EscapeRoom(){
        frame = new JFrame("Escape Room");
        panel = new JPanel();

        hasKey = false;
        lightOn = false;

        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        startButton = new JButton("Start Game");
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        button4 = new JButton();
        textLabel = new JTextArea("Welcome! Click to begin.");
        imageLabel = new JTextArea("");

        textLabel.setBounds(325, 25, 250, 200);
        textLabel.setBackground(Color.gray);
        textLabel.setEditable(false);
        textLabel.setLineWrap(true);
        textLabel.setWrapStyleWord(true);

        imageLabel.setBounds(25, 25, 250, 200);
        imageLabel.setBackground(Color.gray);
        imageLabel.setEditable(false);
        imageLabel.setLineWrap(true);
        imageLabel.setWrapStyleWord(true);

        startButton.setBounds(25, 275, 550, 100);
        button1.setBounds(25, 300, 119, 40);
        button2.setBounds(169, 300, 118, 40);
        button3.setBounds(312, 300, 119, 40);
        button4.setBounds(456, 300, 118, 40);

        button4.setText("Change Rooms");

        button1.addActionListener(new button1Listener());
        button2.addActionListener(new button2Listener());
        button3.addActionListener(new button3Listener());
        button4.addActionListener(new switchRoomsListener());
        startButton.addActionListener(new startGameListener());

        setupPanel();

        panel.add(textLabel);
        panel.setBackground(Color.gray);

        frame.add(panel);
        panel.setLayout(null);
        frame.setVisible(true);
    }

    public void setupPanel(){

        panel.add(startButton);
        panel.add(textLabel);

    }

    public void switchRooms(){

        desiredRoom = (String)JOptionPane.showInputDialog(null, "Pick a room.", "Door", JOptionPane.QUESTION_MESSAGE, homeIcon, rooms, rooms[2]);

        if (currentPlace == desiredRoom){

            JOptionPane.showMessageDialog(null, "You're already there! Pick a different place!", "Error", JOptionPane.PLAIN_MESSAGE, errorIcon);
            switchRooms();

        } else if (desiredRoom == "dresser"){

            currentPlace = "dresser";
            goToDresser();

        } else if (desiredRoom == "lamp"){

            currentPlace = "lamp";
            goToLamp();

        } else if (desiredRoom == "bed"){

            currentPlace = "bed";
            goToBed();

        } else if (desiredRoom == "door"){

            currentPlace = "door";
            goToDoor();

        } else {

            JOptionPane.showMessageDialog(null, "That's not a room! Pick again!");
            switchRooms();

        }
    }

    public void goToDresser(){

        imageLabel.setText(" _______\n" +
                "|_o_ | _o_|\n" +
                "|___o___|\n" +
                "|___o___|\n" +
                "|___o___| \n" +
                "_|_|__|_|_");

        if (lightOn){
            textLabel.setText("The Dresser. You see a key, an 80s plane ticket, and some other things.");
            button1.setText("Grab Key");
            button2.setText("Look at plane ticket");
            button3.setText("Punch dresser");
        } else {
            textLabel.setText("The Dresser. You hear it creak, but can't see much.");
            button1.setText("");
            button2.setText("");
            button3.setText("Punch dresser");
        }
        panel.updateUI();
    }

    public void goToDoor(){
        imageLabel.setText(" __________\n" +
                "|__________|\n" +
                "|__________|\n" +
                "|__________|\n" +
                "|__________|\n" +
                "|_________o|\n" +
                "|__________|\n" +
                "|__________|\n" +
                "|__________|\n" +
                "|__________|\n" +
                "|__________|\n" +
                "|__________|");
        textLabel.setText("The Door. The room is dark and the door is locked. How can you escape?");
        button1.setText("Open door");
        button2.setText("");
        button3.setText("Punch door");

        panel.updateUI();
    }

    public void goToBed(){
        imageLabel.setText("      ( )___ \n" +
                "    ( )//__/)_____________( )\n" +
                "    | |(___)//#/_/#/_/#/_/# ( )/  | |\n" +
                "    | |____|#| |#|_|#|_|#|_ | |   | |\n" +
                "    | | ___|_|#|_|#|_|#|_|# | | / | |\n" +
                "    | |____|#|_|#|_|#|_|#|_| |");

        textLabel.setText("The Bed. The sheets are mangled but feel comfortable.");
        button1.setText("");
        button2.setText("");
        button3.setText("Punch bed");

        panel.updateUI();
    }

    public void goToLamp(){
        imageLabel.setText(
                "  _ _ _\n" +
                " /         \\ \n"+
                "/_____\\ \n"+
                "    /     \\ \n"+
                "    \\__/\n");
        button2.setText("");
        button3.setText("Punch lamp");
        if (lightOn){
            textLabel.setText("The Lamp. It's... well, a lamp that's on.");
            button1.setText("Turn Lamp Off");
        } else {
            textLabel.setText("The Lamp. It's... well, a lamp that's off.");
            button1.setText("Turn Lamp On");
        }

        panel.updateUI();
    }

    private class button1Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (currentPlace.toLowerCase()){
                case "dresser": grabKey();
                break;
                case "door": openDoor();
                break;
                case "lamp": lightOn();
            }
        }
    }

    private class button2Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentPlace.toLowerCase() == "dresser") {
                planeTicket();
            }
        }
    }

    private class button3Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (currentPlace.toLowerCase()){
                case "dresser": punchDresser();
                    break;
                case "door": punchDoor();
                    break;
                case "lamp": punchLamp();
                    break;
                case "bed": punchBed();
            }
        }
    }

    private class startGameListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            panel.remove(startButton);

            panel.add(button1);
            panel.add(button2);
            panel.add(button3);
            panel.add(button4);
            panel.add(imageLabel);

            panel.updateUI();

            goToDoor();

        }
    }

    private class switchRoomsListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            switchRooms();
        }
    }

    public void youWin(){
        textLabel.setText("The Door opens. You have won.");
        panel.setBackground(Color.yellow);
        textLabel.setBackground(Color.yellow);
        imageLabel.setBackground(Color.yellow);
    }

    public void openDoor(){
        if (currentPlace.toLowerCase() == "door"){
            if (hasKey) {
                youWin();
            } else {
                textLabel.setText("The Door. It's locked. How can we escape?");
            }
        }

            panel.updateUI();
    }

    public void lightOn(){
        if (currentPlace.toLowerCase() == "lamp"){
            if (lightOn) {
                textLabel.setText("The Lamp. You have turned the lamp off.");
                lightOn = false;
            } else {
                textLabel.setText("The Lamp. You have turned the lamp on.");
                lightOn = true;
            }
        }

        panel.updateUI();
    }

    public void planeTicket(){
        if (lightOn && currentPlace.toLowerCase() == "dresser"){
            textLabel.setText("The Dresser. The ticket was from San Antonio to BWI for 06/24/87. But, who cares?");
        }

        panel.updateUI();
    }

    public void punchDresser(){
        if (currentPlace.toLowerCase() == "dresser"){
            textLabel.setText("The Dresser. OW! What the hell was I thinking?");
            thingsPunched += 1;
            checkPunchedAmount();
        }

        panel.updateUI();
    }

    public void punchDoor(){
        if (currentPlace.toLowerCase() == "dresser"){
            textLabel.setText("The Door. OW! What the hell was I thinking?");
            thingsPunched += 1;
            checkPunchedAmount();
        }

            panel.updateUI();
    }

    public void punchBed(){
        if (currentPlace.toLowerCase() == "bed"){
            textLabel.setText("The Bed. OW! What the hell was I thinking?");
            thingsPunched += 1;
            checkPunchedAmount();
        }

        panel.updateUI();
    }

    public void punchLamp(){
        if (currentPlace.toLowerCase() == "lamp"){
            textLabel.setText("The Lamp. As your fist approaches, you reconsider it and stop.");
            checkPunchedAmount();
        }

            panel.updateUI();
    }

    public void grabKey(){
        if (lightOn && currentPlace.toLowerCase() == "dresser"){
            hasKey = true;
            textLabel.setText("The Dresser. You grabbed the key.");
        }

        panel.updateUI();
    }

    public void checkPunchedAmount(){
        if (thingsPunched >= 3){
            textLabel.setText(textLabel.getText() + " My hand really hurts thanks to your dumb decision.");
        }

        panel.updateUI();
    }

}
