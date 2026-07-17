package com.anmol.rr.Prjoject3;
import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.sound.sampled.*;

class RailwayReservationSystem {

    static Scanner sc = new Scanner(System.in);

    static final String USER_FILE = "users.txt";
    static final String BOOKING_FILE = "bookings.txt";
    static final String SEAT_FILE = "seats.txt";

    static String loggedUser = "";
    static int lastFoodBill = 0;

    static String[] trains = {
            "Vande Bharat Express",
            "Rajdhani Express",
            "Shatabdi Express",
            "Lucknow Mail",
            "Gorakhdham Express",
            "Saryu Express",
            "Tejas Express",
            "Duronto Express",
            "Garib Rath",
            "Intercity Express",
            "Pushpak Express",
            "Mahanagari Express",
            "Amrapali Express",
            "Magadh Express",
            "Punjab Mail",
            "Howrah Mail",
            "Humsafar Express",
            "Sampark Kranti",
            "Utkal Express",
            "Kashi Vishwanath Express"
    };

    static String[] routes = {
            "Lucknow -> Kanpur -> Delhi",
            "Delhi -> Agra -> Jhansi",
            "Mumbai -> Surat -> Ahmedabad",
            "Chennai -> Bangalore -> Mysore",
            "Kolkata -> Asansol -> Dhanbad",
            "Patna -> Varanasi -> Lucknow",
            "Jaipur -> Ajmer -> Udaipur",
            "Bhopal -> Itarsi -> Nagpur",
            "Hyderabad -> Vijayawada -> Visakhapatnam",
            "Pune -> Nashik -> Manmad",
            "Gorakhpur -> Lucknow -> Delhi",
            "Prayagraj -> Kanpur -> Delhi",
            "Jammu -> Pathankot -> Amritsar",
            "Guwahati -> New Jalpaiguri -> Kolkata",
            "Bhubaneswar -> Cuttack -> Kharagpur",
            "Indore -> Ujjain -> Ratlam",
            "Ranchi -> Bokaro -> Dhanbad",
            "Kochi -> Trivandrum -> Kanyakumari",
            "Dehradun -> Haridwar -> Delhi",
            "Varanasi -> Prayagraj -> Bhopal"
    };

    static String[] foodName = {
            "Veg Thali",
            "Deluxe Veg Thali",
            "Soya Chaap",
            "Veg Biryani",
            "Paneer Butter Masala",
            "Butter Naan",
            "Masala Dosa",
            "Idli Sambhar",
            "Chole Bhature",
            "Rajma Chawal",
            "Burger",
            "Pizza",
            "Sandwich",
            "Tea",
            "Coffee",
            "Cold Drink",
            "Water Bottle",
            "Ice Cream",
            "Samosa",
            "Gulab Jamun"
    };

    static int[] foodPrice = {
            150, 220, 250, 180, 200,
            40, 120, 90, 130, 140,
            110, 250, 100, 20, 40,
            50, 20, 80, 25, 40
    };
    static int sleeperSeat = 72;
    static int ac3Seat = 50;
    static int ac2Seat = 30;
    static int ac1Seat = 20;

    static void registerUser() {

        try {

            System.out.print("Enter Username : ");
            String user = sc.nextLine();

            System.out.print("Enter Password : ");
            String pass = sc.nextLine();

            File file = new File(USER_FILE);

            if (file.exists()) {

                BufferedReader br = new BufferedReader(new FileReader(file));

                String line;

                while ((line = br.readLine()) != null) {

                    String data[] = line.split(",");

                    if (data[0].equals(user)) {

                        System.out.println("Username Already Exists.");

                        br.close();

                        return;

                    }

                }

                br.close();

            }

            FileWriter fw = new FileWriter(USER_FILE, true);

            fw.write(user + "," + pass + "\n");

            fw.close();

            System.out.println("Registration Successful.");

        } catch (Exception e) {

            System.out.println(e);

        }

    }

    static boolean loginUser() {

        try {

            System.out.print("Username : ");

            String user = sc.nextLine();

            System.out.print("Password : ");

            String pass = sc.nextLine();

            BufferedReader br = new BufferedReader(new FileReader(USER_FILE));

            String line;

            while ((line = br.readLine()) != null) {

                String data[] = line.split(",");

                if (data[0].equals(user) && data[1].equals(pass)) {

                    loggedUser = user;

                    br.close();

                    return true;

                }

            }

            br.close();

        } catch (Exception e) {

            System.out.println(e);

        }

        return false;

    }

    static void searchTrain() {

        System.out.println("\n========= TRAIN LIST =========");

        for (int i = 0; i < trains.length; i++) {

            System.out.println((i + 1) + ". " + trains[i]);

        }

    }

    static void showRoutes() {

        System.out.println("\n========= ROUTES =========");

        for (int i = 0; i < routes.length; i++) {

            System.out.println((i + 1) + ". " + routes[i]);

        }

    }

    static void seatAvailability() {


        System.out.println("\n========= Seat Availability =========");

        System.out.println("Sleeper : " + sleeperSeat);

        System.out.println("3AC : " + ac3Seat);

        System.out.println("2AC : " + ac2Seat);

        System.out.println("1AC : " + ac1Seat);
    }

    static void bookTicket() {

        try {
            System.out.print("Enter Number of Passengers (1-5): ");
            int totalPassenger = Integer.parseInt(sc.nextLine());

            if (totalPassenger < 1 || totalPassenger > 5) {
                System.out.println("Maximum 5 Passengers Allowed.");
                return;
            }

            String[] passengerName = new String[totalPassenger];
            int[] age = new int[totalPassenger];
            String[] gender = new String[totalPassenger];

            String From;
            String To;
            String journeyDate;
            String trainName;
            String coach;
            int fare = 0;

            System.out.println("\n========== BOOK TICKET ==========");
            System.out.println("Total Passengers : " + totalPassenger);
            for (int i = 0; i < totalPassenger; i++) {

                System.out.println("\nPassenger " + (i + 1));

                System.out.print("Name : ");
                passengerName[i] = sc.nextLine();

                System.out.print("Age : ");
                age[i] = Integer.parseInt(sc.nextLine());

                System.out.print("Gender : ");
                gender[i] = sc.nextLine();
            }

            System.out.print("From : ");
            From = sc.nextLine();

            System.out.print("To : ");
            To = sc.nextLine();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            while (true) {

                System.out.print("Journey Date (DD/MM/YYYY) : ");
                journeyDate = sc.nextLine();

                try {

                    LocalDate enteredDate = LocalDate.parse(journeyDate, formatter);

                    if (enteredDate.isBefore(LocalDate.now())) {

                        System.out.println("❌ Journey date cannot be in the past.");
                        continue;

                    }

                    break;

                } catch (Exception e) {

                    System.out.println("❌ Invalid Date Format! Please enter date in DD/MM/YYYY format.");

                }
            }

            searchTrain();
            System.out.print("\nChoose Train Number : ");

            int trainChoice = Integer.parseInt(sc.nextLine());

            if (trainChoice < 1 || trainChoice > trains.length) {

                System.out.println("Invalid Train.");

                return;

            }

            trainName = trains[trainChoice - 1];
3333
            System.out.println("\nChoose Coach");

            System.out.println("1. Sleeper");
            System.out.println("2. 3AC");
            System.out.println("3. 2AC");
            System.out.println("4. 1AC");

            int coachChoice = Integer.parseInt(sc.nextLine());

            switch (coachChoice) {

                case 1:
                    coach = "Sleeper";
                    fare = 350;
                    coach = "Sleeper";
                    fare = 350;

                    if (sleeperSeat < totalPassenger) {

                        System.out.println("Not Enough Seats.");
                        return;

                    }

                    sleeperSeat -= totalPassenger;
                    break;

                case 2:
                    coach = "3AC";
                    fare = 850;
                    coach = "3AC";
                    fare = 850;

                    if (ac3Seat < totalPassenger) {

                        System.out.println("Not Enough Seats.");
                        return;

                    }

                    ac3Seat -= totalPassenger;
                    break;

                case 3:
                    coach = "2AC";
                    fare = 1300;
                    coach = "2AC";
                    fare = 1300;

                    if (ac2Seat < totalPassenger) {

                        System.out.println("Not Enough Seats.");
                        return;

                    }

                    ac2Seat -= totalPassenger;
                    break;

                case 4:
                    coach = "1AC";
                    fare = 2200;
                    coach = "1AC";
                    fare = 2200;

                    if (ac1Seat < totalPassenger) {

                        System.out.println("Not Enough Seats.");
                        return;

                    }

                    ac1Seat -= totalPassenger;
                    break;

                default:
                    System.out.println("Invalid Coach.");
                    return;

            }
            int totalFare = 0;

            for (int i = 0; i < totalPassenger; i++) {

                int passengerFare = fare;

                if (age[i] < 5) {
                    passengerFare = 0;
                } else if (age[i] >= 60) {
                    passengerFare = (int) (fare * 0.80);
                }

                totalFare += passengerFare;
            }

            Random random = new Random();
            java.time.LocalDateTime now = java.time.LocalDateTime.now();

            String bookingTime = now.format(
                    java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

            String pnr = "PNR" + (100000 + random.nextInt(900000));

            int coachNo = 1 + random.nextInt(10);

            int seatNo = 1 + random.nextInt(72);

            FileWriter fw = new FileWriter(BOOKING_FILE, true);

            StringBuilder passengerData = new StringBuilder();

            for (int i = 0; i < totalPassenger; i++) {

                passengerData.append(passengerName[i])
                        .append("-")
                        .append(age[i])
                        .append("-")
                        .append(gender[i]);

                if (i != totalPassenger - 1) {
                    passengerData.append("|");
                }
            }

            fw.write(loggedUser + "," +
                    pnr + "," +
                    passengerData.toString() + "," +
                    From + "," +
                    To + "," +
                    journeyDate + "," +
                    trainName + "," +
                    coach + "," +
                    coachNo + "," +
                    seatNo + "," +
                    totalFare + "," +
                    bookingTime + "," +
                    lastFoodBill + "," +
                    (totalFare + lastFoodBill) + "\n");
            saveSeats();
            fw.close();

            System.out.println("\n========== TICKET ==========");

            System.out.println("PNR           : " + pnr);

            System.out.println("Total Passengers : " + totalPassenger);
            System.out.println("Passenger Details");

            for (int i = 0; i < totalPassenger; i++) {

                System.out.println("--------------------------------");

                System.out.println("Passenger " + (i + 1));

                System.out.println("Name     : " + passengerName[i]);

                System.out.println("Age      : " + age[i]);

                System.out.println("Gender   : " + gender[i]);

            }
            System.out.println("Train          : " + trainName);

            System.out.println("From          : " + From);

            System.out.println("To            : " + To);

            System.out.println("Journey Date  : " + journeyDate);

            System.out.println("Coach         : " + coach);

            System.out.println("Coach No      : " + coachNo);

            System.out.println("Seat No       : " + seatNo);

            System.out.println("Total Fare    : Rs." + totalFare);
            System.out.println("Food Bill     : Rs." + lastFoodBill);

            System.out.println("Grand Total   : Rs." + (totalFare + lastFoodBill));

            System.out.println("============================");

        } catch (Exception e) {

            System.out.println("Booking Failed.");

        }

    }

    static void orderFood() {

        System.out.println("\n========== FOOD MENU ==========");

        for (int i = 0; i < foodName.length; i++) {

            System.out.println((i + 1) + ". " + foodName[i] + "    Rs." + foodPrice[i]);

        }

        int total = 0;

        while (true) {

            System.out.print("\nEnter Item Number (0 to Finish) : ");

            int item = Integer.parseInt(sc.nextLine());

            if (item == 0)
                break;

            if (item < 1 || item > foodName.length) {

                System.out.println("Invalid Item");

                continue;

            }

            System.out.print("Quantity : ");

            int qty = Integer.parseInt(sc.nextLine());

            total += foodPrice[item - 1] * qty;

        }

        System.out.println("----------------------------");

        System.out.println("Total Food Bill : Rs." + total);
        lastFoodBill = total;

        System.out.println("----------------------------");

    }

    static void viewBookingHistory() {

        try {

            File file = new File(BOOKING_FILE);

            if (!file.exists()) {
                System.out.println("No Booking Found.");
                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            boolean found = false;

            System.out.println("\n========== BOOKING HISTORY ==========");

            while ((line = br.readLine()) != null) {

                String data[] = line.split(",");

                if (data[0].equals(loggedUser)) {

                    found = true;

                    System.out.println("----------------------------------------");
                    System.out.println("PNR : " + data[1]);
                    String[] passengers = data[2].split("\\|");

                    System.out.println("Total Passengers : " + passengers.length);

                    for (int i = 0; i < passengers.length; i++) {

                        String[] p = passengers[i].split("-");

                        System.out.println("--------------------------------");

                        System.out.println("Passenger " + (i + 1));

                        System.out.println("Name   : " + p[0]);

                        System.out.println("Age    : " + p[1]);

                        System.out.println("Gender : " + p[2]);
                    }
                    System.out.println("From : " + data[3]);
                    System.out.println("To : " + data[4]);
                    System.out.println("Journey Date : " + data[5]);
                    System.out.println("Train : " + data[6]);
                    System.out.println("Coach : " + data[7]);
                    System.out.println("Coach No : " + data[8]);
                    System.out.println("Seat No : " + data[9]);
                    System.out.println("Fare : Rs." + data[10]);
                    System.out.println("Booking Time : " + data[11]);
                }

            }

            if (!found) {

                System.out.println("No Ticket Booked.");

            }

            br.close();

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());

        }

    }

    static void searchByPNR() {

        try {

            System.out.print("Enter PNR Number : ");

            String pnr = sc.nextLine();

            BufferedReader br = new BufferedReader(new FileReader(BOOKING_FILE));

            String line;

            boolean found = false;

            while ((line = br.readLine()) != null) {

                String data[] = line.split(",");

                if (data[1].equalsIgnoreCase(pnr)) {

                    found = true;

                    System.out.println("\nTicket Found");

                    System.out.println("Passenger : " + data[2]);

                    System.out.println("Train : " + data[6]);

                    System.out.println("Journey : " + data[5]);

                    System.out.println("Coach : " + data[7]);

                    System.out.println("Seat : " + data[9]);

                    System.out.println("Fare : Rs." + data[10]);
                    System.out.println("Booking Time : " + data[11]);

                    break;

                }

            }

            if (!found) {

                System.out.println("PNR Not Found.");

            }

            br.close();

        } catch (Exception e) {

            System.out.println(e);

        }

    }

    static void cancelTicket() {

        try {

            System.out.print("Enter PNR Number : ");

            String pnr = sc.nextLine();

            File input = new File(BOOKING_FILE);

            File temp = new File("temp.txt");

            BufferedReader br = new BufferedReader(new FileReader(input));

            PrintWriter pw = new PrintWriter(new FileWriter(temp));

            String line;

            boolean deleted = false;

            while ((line = br.readLine()) != null) {

                String data[] = line.split(",");

                if (data[1].equalsIgnoreCase(pnr)) {

                    deleted = true;

                    continue;

                }

                pw.println(line);

            }

            br.close();

            pw.close();

            input.delete();

            temp.renameTo(input);

            if (deleted)

                System.out.println("Ticket Cancelled Successfully.");

            else

                System.out.println("PNR Not Found.");

        } catch (Exception e) {

            System.out.println(e);

        }

    }

    static void adminLogin() {

        System.out.print("Admin Username : ");

        String u = sc.nextLine();

        System.out.print("Admin Password : ");

        String p = sc.nextLine();

        if (u.equals("admin") && p.equals("admin123")) {

            try {

                BufferedReader br = new BufferedReader(new FileReader(BOOKING_FILE));

                String line;

                System.out.println("\n========== ALL BOOKINGS ==========");

                while ((line = br.readLine()) != null) {

                    System.out.println(line);

                }

                br.close();

            } catch (Exception e) {

                System.out.println("No Booking Record.");

            }

        } else {

            System.out.println("Invalid Admin Login.");

        }

    }

    static void changePassword() {
        try {
            System.out.print("Enter Current Password : ");
            String oldPass = sc.nextLine();
            File input = new File(USER_FILE);
            File temp = new File("users_temp.txt");
            BufferedReader br = new BufferedReader(new FileReader(input));
            PrintWriter pw = new PrintWriter(new FileWriter(temp));
            String line;
            boolean updated = false;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(loggedUser)) {
                    if (data[1].equals(oldPass)) {
                        System.out.print("Enter New Password : ");
                        String np = sc.nextLine();
                        pw.println(data[0] + "," + np);
                        updated = true;
                    } else pw.println(line);
                } else pw.println(line);
            }
            br.close();
            pw.close();
            input.delete();
            temp.renameTo(input);
            if (updated) System.out.println("Password Changed Successfully.");
            else System.out.println("Current Password Incorrect.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void saveSeats() {

        try {

            FileWriter fw = new FileWriter(SEAT_FILE);

            fw.write(sleeperSeat + "," +
                    ac3Seat + "," +
                    ac2Seat + "," +
                    ac1Seat);

            fw.close();

        } catch (Exception e) {

            System.out.println(e);

        }

    }

    static void loadSeats() {

        try {

            File file = new File(SEAT_FILE);

            if (!file.exists()) {

                saveSeats();
                return;

            }

            BufferedReader br = new BufferedReader(new FileReader(file));

            String line = br.readLine();

            br.close();

            String[] data = line.split(",");

            sleeperSeat = Integer.parseInt(data[0]);
            ac3Seat = Integer.parseInt(data[1]);
            ac2Seat = Integer.parseInt(data[2]);
            ac1Seat = Integer.parseInt(data[3]);

        } catch (Exception e) {

            System.out.println(e);

        }

    }
    static void playAlertSound() {
        try {
            java.net.URL soundURL = RailwayReservationSystem.class.getResource("/alert.wav");

            if (soundURL == null) {
                System.out.println("alert.wav file not found");
                return;
            }

            AudioInputStream audio = AudioSystem.getAudioInputStream(soundURL);

            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void userMenu() {

        int choice;

        do {

            System.out.println("\n======================================");
            System.out.println("        RAILWAY RESERVATION");
            System.out.println("======================================");
            System.out.println("Welcome : " + loggedUser);
            System.out.println("--------------------------------------");

            System.out.println("1. Search Train");

            System.out.println("2. Show Routes");

            System.out.println("3. Seat Availability");

            System.out.println("4. Book Ticket");

            System.out.println("5. Food Order");

            System.out.println("6. View Booking History");

            System.out.println("7. Search Ticket By PNR");

            System.out.println("8. Cancel Ticket");

            System.out.println("9. Change Password");

            System.out.println("10. Emergency SOS");
            System.out.println("11. Logout");;
            System.out.println("--------------------------------------");
            System.out.print("Enter Choice : ");

            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {


                case 1:
                    searchTrain();
                    break;

                case 2:
                    showRoutes();
                    break;

                case 3:
                    seatAvailability();
                    break;

                case 4:
                    bookTicket();
                    break;

                case 5:
                    orderFood();
                    break;

                case 6:
                    viewBookingHistory();
                    break;
                case 7:
                    searchByPNR();
                    break;
                case 8:
                    cancelTicket();
                    break;

                case 9:
                    changePassword();
                    break;
                case 10:
                    playAlertSound();
                    System.out.println("\n******** EMERGENCY SOS ********");
                    System.out.println("Emergency Alert Activated");
                    System.out.println("*******************************");
                    break;
                case 11:
                    System.out.println("Logout Successful.");
                    loggedUser = "";
                    break;

                default:
                    System.out.println("Invalid Choice.");

            }

        } while (choice != 11);

    }

    public static void main(String args[]) {
        loadSeats();

        int choice;

        do {

            System.out.println("\n======================================");
            System.out.println("      RAILWAY RESERVATION SYSTEM");
            System.out.println("======================================");
            System.out.println("1. Register");

            System.out.println("2. Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");
            System.out.print("Enter Choice : ");

            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:

                    registerUser();

                    break;

                case 2:

                    if (loginUser()) {

                        System.out.println("\nLogin Successful.");

                        userMenu();

                    } else {

                        System.out.println("Invalid Username or Password.");

                    }

                    break;

                case 3:

                    adminLogin();

                    break;

                case 4:

                    System.out.println("\nThank You For Using Railway Reservation System.");

                    break;

                default:

                    System.out.println("Invalid Choice.");

            }

        } while (choice != 4);

    }
}

