import java.sql.*;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    private static Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:C:\\Users\\dell\\OneDrive\\Skrivbord\\Labb3_RobertMilicevic.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private static void printActions() {
        System.out.println("\nVälj:\n");
        System.out.println("0  - Stäng av\n" +
                "1  - Visa alla Tabel\n" +
                "2  - Lägga till en ny Försäljare\n" +
                "3  - Lägga till en ny Bil\n" +
                "4  - Uppdatera en Försäljare\n" +
                "5  - Uppdatera en Bil\n" +
                "6  - Ta bort en Försäljare\n" +
                "7  - Ta bort en Bil\n" +
                "8  - Sök efter en Försäljare\n" +
                "9  - Sök efter en Bil\n" +
                "10  - Visa en lista över alla val.");
    }


    private static void selectAll() {
        String sqlDealer = "SELECT * FROM Dealer";
        String sqlCars = "SELECT * FROM Cars";


        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            Statement stmt1 = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sqlDealer);
            ResultSet rs1 = stmt1.executeQuery(sqlCars);
            // loop through the result set

            System.out.println("Försäljare: ");
            while (rs.next()) {
                System.out.println(
                        "Försäljare ID: " + rs.getInt("dealerID_PK") + "\n" +
                                "Försäljare namn: " + rs.getString("dealerName") + "\n" +
                                "Försäljare erarnheten: " + rs.getString("dealerYearsOfWorks") +
                                "\n------------------------------");
            }

            System.out.println("Bilar: ");
            while (rs1.next()) {
                System.out.println(
                        "Bil model ID: " + rs1.getInt("carID_PK") + "\n" +
                                "Bil model namn: " + rs1.getString("carModel") + "\n" +
                                "Bil registreringsnummer: " + rs1.getString("carRegisteringNumber") + "\n" +
                                "Bil plats: " + rs1.getString("carPlace") + "\n" +
                                "Bil pris: " + rs1.getInt("carsPrice") + "\n" +
                                "Bil ISBN: " + rs1.getInt("carsISBN") + "\n" +
                                "Försäljare ID nummer: " + rs1.getInt("dealer_FK") +
                                "\n------------------------------");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Metod för användarens inmatningar (som en controller)
    private static void insertDealer() {
        System.out.println("Skriv in Försäljare name: ");
        String inputDealerNamne = scanner.nextLine();

        System.out.println("Skriv in Försäljare plats: ");
        String inputDealerPlats = scanner.nextLine();

        insertDealerSQL(inputDealerNamne, inputDealerPlats);

    }

    private static void insertDealerSQL(String dealerHouseName, String dealerHousePLace) {
        String sqlDealer = "INSERT INTO dealer(dealerName, dealerYearsOfWorks) VALUES(?,?)";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sqlDealer);
            pstmt.setString(1, dealerHouseName);
            pstmt.setString(2, dealerHousePLace);
            pstmt.executeUpdate();
            System.out.println("Du har lagt till en ny försäljare");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void insertCars() {
        System.out.println("Skriv in Bil model namn: ");
        String inputDealerName = scanner.nextLine();

        System.out.println("Skriv in bil registering nummer: ");
        String inputRegisteringNumer = scanner.nextLine();

        System.out.println("Skriv in bil plats: ");
        String inputBilPlats = scanner.nextLine();

        System.out.println("Skriv in bil pris: ");
        int inputBilPrice = scanner.nextInt();

        System.out.println("Skriv in bil ISBN: ");
        int inputBilISBN = scanner.nextInt();

        System.out.println("Skriv in Försäljere ID: ");
        int inputDealersFK = scanner.nextInt();


        insertCarsSQL(inputDealerName, inputRegisteringNumer, inputBilPlats, inputBilPrice, inputBilISBN, inputDealersFK);
    }

    private static void insertCarsSQL(String inputDealerName, String inputRegisteringNumer, String inputBilPlats, int inputBilPrice, int inputBilISBN, int inputDealersFK) {
        String sqlDealer = "INSERT INTO cars(carModel, carRegisteringNumber, carPlace, carsPrice, carsISBN, dealer_FK) VALUES(?,?,?,?,?,?)";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sqlDealer);
            pstmt.setString(1, inputDealerName);
            pstmt.setString(2, inputRegisteringNumer);
            pstmt.setString(3, inputBilPlats);
            pstmt.setInt(4, inputBilPrice);
            pstmt.setInt(5, inputBilISBN);
            pstmt.setInt(6, inputDealersFK);
            pstmt.executeUpdate();
            System.out.println("Du har lagt till en ny försäljare");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deleteDealerHouse() {
        System.out.println("Skriv in id:t på Dealer House som ska tas bort: ");
        int inputId = scanner.nextInt();
        delete(inputId);
        scanner.nextLine();
    }

    private static void insertDealer() {
        System.out.println("Skriv in Försäljare name: ");
        String inputDealerNamne = scanner.nextLine();

        System.out.println("Skriv in Försäljare plats: ");
        String inputDealerPlats = scanner.nextLine();

        updateDealers();
    }

    private static void updateDealers(String dealerName, String dealersYearsOfWork, int ID) {
        String sql = "UPDATE dealer_House SET dealerHouseName = ? , "
                + "dealerHousePlace = ?  "
                + "WHERE dealerHouseID_PK = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param

            pstmt.setString(1, dealerName);
            pstmt.setString(2, dealersYearsOfWork);
            pstmt.setInt(3, ID);

            // update
            pstmt.executeUpdate();
            System.out.println("Du har uppdaterat vald dealer House");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void searchDealerHouse(int dealerHouseID) {
        String sql = "SELECT * FROM dealer_House WHERE dealerHouseID_PK = ? ";

        try (
                Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

//            String inputForfattare = "Astrid Lindgren";


            // set the value
            pstmt.setInt(1, dealerHouseID);
            //
            ResultSet rs = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("dealerHouseID_PK") + "\t" +
                        rs.getString("dealerHouseName") + "\t" +
                        rs.getString("dealerHousePlace"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Delete mot bok-tabellen i databasen
    private static void delete(int id) {
        String sql = "DELETE FROM dealer_House WHERE dealerHouseID_PK = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();
            System.out.println("Du har tagit bort boken");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

        boolean quit = false;
        //printActions();
        while (!quit) {
            printActions();
            System.out.println("\nVälj (10 för att visa val):");

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 0:
                    System.out.println("\nStänger ner...");
                    quit = true;
                    break;

                case 1:
                    // Select all Dealer House
                    selectAll();
                    break;

                case 2:
                    //Ta bort en Dealer
                    insertDealer();
                    break;

                case 3:
                    // Lägga till en ny Car
                    insertCars();
                    break;

                case 4:
                    // Uppdatera en Dealer House


                    update(dealerHouse, dealerPlace, ID);
                    break;

                case 5:
                    // Uppdatera en Dealer
                case 6:
                    // ta bort en Dealer House
                    deleteDealerHouse();
                    break;

                case 7:
                    //Ta bort en Dealer
                case 8:
                    //sökning efter en Dealer House ID
                    System.out.println("Skriv in vilken dealer house ID vill du söka");
                    int dealerHouseID = scanner.nextInt();
                    searchDealerHouse(dealerHouseID);
                    break;
                case 9:
                    //Sök efter en Dealer
                case 10:
                    printActions();
                    break;
            }
        }

    }

}
