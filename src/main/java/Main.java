

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

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
        System.out.println("2 - Show apartments");
        System.out.println("3 - Sort Max to Min");
        System.out.println("4 - Sort Min to Max");
        System.out.println("5 - Search apartments");
        System.out.println("0 - Exit");
        while (true){
            Scanner sc = new Scanner(System.in);
            int v  = sc.nextInt();
            if( v == 1){
                addApartment();
            }else if(v == 2){
                viem();
            }else if( v == 3){
                sort("max");
            }else if(v == 4){
                sort("min");
            }else if(v == 5){
                search();
            }else if (v == 0){
                System.out.println("Exit!!!");;
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
    private static void viem(){
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
    private static void sort(String m){
        PreparedStatement ps2;
        ArrayList<Integer> arr = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM apartments");
            ResultSet rs = ps.executeQuery();
            for(int i = 0; rs.next(); i++){
                arr.add(rs.getInt(5));
            }
            Collections.sort(arr);
            for(int i = 0; i < arr.size();i++){
                if(m.equals("max")){
                    ps2 = con.prepareStatement("SELECT * FROM apartments WHERE price LIKE " + arr.get(arr.size() - i - 1));
                }else{
                    ps2 = con.prepareStatement("SELECT * FROM apartments WHERE price LIKE " + arr.get(i));
                }
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    for(int i2 = 1;i2 <= 5; i2++){
                        System.out.print(rs2.getString(i2) + ", ");
                    }
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void search(){
        boolean exist = false;
        System.out.println("Enter region");
        Scanner sc = new Scanner(System.in);
        String reg = sc.nextLine();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM apartments");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if(rs.getString(1).equals(reg)){
                    exist = true;
                    for(int i = 1;i <= 5; i++){
                        System.out.print(rs.getString(i) + ", ");
                    }
                    System.out.println();
                }
            }
            if(!exist){
                System.out.println("Not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
