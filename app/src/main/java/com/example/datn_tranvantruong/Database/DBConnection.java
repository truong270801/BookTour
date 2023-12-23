package com.example.datn_tranvantruong.Database;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    Connection connection = null;
    public  Connection createConection(){
        String ip, port,databasename,user,password,url;
        ip = "192.168.43.16";
        port = "1433";
        databasename = "BOOK_TOUR";
        user = "sa";
        password = "2708";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            url = "jdbc:jtds:sqlserver://"+ip+":"+port+";databasename="+databasename+";user="+user+";password="+password+"";
        connection = DriverManager.getConnection(url);
        }catch (Exception e){
            Log.e("Error:", e.getMessage());
        }
         return connection;
        }
    }

