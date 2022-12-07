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
                "1  - Visa alla Dealer House\n" +
                "2  - Lägga till en ny Dealer House\n" +
                "3  - Lägga till en ny Dealer\n" +
                "4  - Uppdatera en Dealer House\n" +
                "5  - Uppdatera en Dealer\n" +
                "6  - Ta bort en Dealer House\n" +
                "7  - Ta bort en Dealer\n" +
                "8  - Sök efter en Dealer House ID\n" +
                "9  - Sök efter en Dealer\n" +
                "10  - Visa en lista över alla val.");
    }


    private static void selectAll() {
        String sqlDealerHouse = "SELECT * FROM dealer_House";
        String sqlDealer = "SELECT * FROM dealer";


        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            Statement stmt1 = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sqlDealerHouse);
            ResultSet rs1 = stmt1.executeQuery(sqlDealer);
            // loop through the result set
            System.out.println("ID  Dealer House name   Dealer Place");
            while (rs.next()) {
                System.out.println(rs.getInt("dealerHouseID_PK") + "\t" +
                        rs.getString("dealerHouseName") + "\t" +
                        rs.getString("dealerHousePlace"));
            }

            System.out.println("\nID \t dealerName \t dealerYearsOfWork");
            while (rs1.next()) {
                System.out.println(rs1.getInt("dealerID_PK") + "\t" +
                        rs1.getString("dealerName") + "\t" +
                        rs1.getInt("dealerYearsOfWorks"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Metod för användarens inmatningar (som en controller)
    private static void insertDealerHouse() {
        System.out.println("Skriv in dealer House name: ");
        String inputDealerHouseName = scanner.nextLine();

        System.out.println("Skriv in dealer House plats: ");
        String inputDealerPlats = scanner.nextLine();

        insert(inputDealerHouseName, inputDealerPlats);

    }
    private static void insertDealer() {
        System.out.println("Skriv in dealer namn: ");
        String inputDealerName = scanner.nextLine();

        System.out.println("Skriv in dealer års av arbete : ");
        String inputeDealersYearsOfWork = scanner.nextLine();

        insert(inputDealerName, inputeDealersYearsOfWork);

    }

    private static void deleteDealerHouse() {
        System.out.println("Skriv in id:t på Dealer House som ska tas bort: ");
        int inputId = scanner.nextInt();
        delete(inputId);
        scanner.nextLine();
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

    // Metod för insert i bok-tabellen mot databasen
    private static void insert(String dealerHouseName, String dealerHousePLace) {
        String sql = "INSERT INTO dealer_House(dealerHouseName, dealerHousePlace) VALUES(?,?)";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dealerHouseName);
            pstmt.setString(2, dealerHousePLace);
            pstmt.executeUpdate();
            System.out.println("Du har lagt till en ny dealer House name");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Update mot bok-tabellen i databasen
    private static void update(String dealerHouseName, String dealerHousePlace, int ID) {
        String sql = "UPDATE dealer_House SET dealerHouseName = ? , "
                + "dealerHousePlace = ?  "
                + "WHERE dealerHouseID_PK = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param

            pstmt.setString(1, dealerHouseName);
            pstmt.setString(2, dealerHousePlace);
            pstmt.setInt(3, ID);

            // update
            pstmt.executeUpdate();
            System.out.println("Du har uppdaterat vald dealer House");
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
        printActions();
        while (!quit) {
            System.out.println("\nVälj (11 för att visa val):");
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
                    //Ta bort en Dealer House
                    insertDealerHouse();
                    break;

                case 3:
                    // Lägga till en ny Dealer

                case 4:
                    // Uppdatera en Dealer House
                    System.out.println("Skriv namn av Dealer House: ");
                    String dealerHouse = scanner.nextLine();

                    System.out.println("Skriv ort av Dealer House");
                    String dealerPlace = scanner.nextLine();

                    System.out.println("Skriv in ID som du vill update");
                    int ID = scanner.nextInt();

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
