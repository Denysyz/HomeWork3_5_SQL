

import java.sql.*;
import java.util.Scanner;

/**
 * Created by dende on 04.11.2016.
 */
public class Main {
    static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/mydb";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "0000";
    static Connection con;
    public static void main(String[] args) {
        try {
            con = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("1 - Add apartment");
        System.out.println("2 - Search apartment");
        System.out.println("0 - Exit");
        while (true){
            Scanner sc = new Scanner(System.in);
            int v  = sc.nextInt();
            if( v == 1){
                addApartment();
            }else if(v == 2){
                serch();
            }else if (v == 0){
                break;
            }else{
                System.out.println("error command");
            }
        }
    }
    private static void addApartment(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter region");
        String region = sc.nextLine();
        System.out.println("Enter adress");
        String adress = sc.nextLine();
        System.out.println("Enter area");
        int area = sc.nextInt();
        System.out.println("Enter rooms");
        int rooms = sc.nextInt();
        System.out.println("Enter price");
        int price = sc.nextInt();

        try {
            Statement st = con.createStatement();
            st.execute("insert into apartments values('" + region + "', '" + adress + "', " + area + ", " +
                    rooms + ", " + price +");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Succesfull");
    }
    private static void serch(){
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM apartments");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                for(int i = 1;i <= 5; i++){
                    System.out.print(rs.getString(i) + ", ");

                }
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
