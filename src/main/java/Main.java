import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws SQLException {
    Connection conn = null;

    try {
      conn = DriverManager.getConnection("jdbc:sqlite:src/data/StudentExample.db");

      Statement stmt = conn.createStatement();
      stmt.execute("CREATE TABLE IF NOT EXISTS Employee ("
          + "    eid         INTEGER        PRIMARY KEY,"
          + "    fname       VARCHAR,"
          + "    lname       VARCHAR,"
          + "    job_title   VARCHAR,"
          + "    zipcode     NUMERIC (5),"
          + "    payrate     NUMERIC (5, 2),"
          + "    dept_number INTEGER"
          + ");");

      stmt.close();
    } catch (SQLException e) {
      System.out.println("Could not connect to database or create table");
      e.printStackTrace();
    }

    int id = 1115;
    Scanner scan = new Scanner(System.in);

    String nameInserts = "INSERT INTO Employee (eid, fname, lname, zipcode, dept_number) VALUES "
        + "(?, ?, ?, 62522, 8);";

    for (int i = 0; i<4; i++) {
      String firstName = "";
      String lastName = "";

      System.out.println("What do you want your first name to be?");
      firstName = scan.nextLine();
      System.out.println("What do you want your last name to be?");
      lastName = scan.nextLine();

      try {
        PreparedStatement pstmt = conn.prepareStatement(nameInserts);
        pstmt.setInt(1, id + i);
        pstmt.setString(2, firstName);
        pstmt.setString(3, lastName);
        pstmt.executeUpdate();

        pstmt.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    Statement selectStmt = conn.createStatement();
    ResultSet rsAllSearch = selectStmt.executeQuery("SELECT * FROM Employee;");

    while (rsAllSearch.next()) {
      System.out.println("\n------------------\nReturned results: \n");
      System.out.println("EID = " + rsAllSearch.getInt("eid"));
      System.out.println("First Name = " + rsAllSearch.getString("fname"));
      System.out.println("Last Name = " + rsAllSearch.getString("lname"));
      System.out.println("Zipcode = " + rsAllSearch.getInt("zipcode"));
      System.out.println("\n------------------");
    }


    try {
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      System.out.println("Could not close the Database");
      e.printStackTrace();
    }


  }

}
