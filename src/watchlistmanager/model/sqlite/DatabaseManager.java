//    Copyright (C) 2017 MD. Ibrahim Khan
//
//    Project Name: 
//    Author: MD. Ibrahim Khan
//    Author's Email: ib.arshad777@gmail.com
//
//    Redistribution and use in source and binary forms, with or without modification,
//    are permitted provided that the following conditions are met:
//
//    1. Redistributions of source code must retain the above copyright notice, this
//       list of conditions and the following disclaimer.
//
//    2. Redistributions in binary form must reproduce the above copyright notice, this
//       list of conditions and the following disclaimer in the documentation and/or
//       other materials provided with the distribution.
//
//    3. Neither the name of the copyright holder nor the names of the contributors may
//       be used to endorse or promote products derived from this software without
//       specific prior written permission.
//
//    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
//    ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
//    WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
//    IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
//    INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING
//    BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
//    DATA, OR PROFITS; OR BUSINESS INTERRUPTIONS) HOWEVER CAUSED AND ON ANY THEORY OF
//    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
//    OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
//    OF THE POSSIBILITY OF SUCH DAMAGE.

package watchlistmanager.model.sqlite;

/**
 *
 * @author Arshad
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.chart.PieChart;
import watchlistmanager.MainLoader;
import watchlistmanager.model.data.Catagory;
import watchlistmanager.model.data.EntryDetailed;
import watchlistmanager.model.data.Entry;
import watchlistmanager.model.logging.AppLogger;

//Last error code 2019
public class DatabaseManager {

    // Database specific String variables
    private final String databaseFileLocation = AppLogger.DATA_DIR + "/WatchListDatabase.sqlite3";
    private final String databaseLocation = "jdbc:sqlite:" + databaseFileLocation;

    public static String TABLE_CATAGORY = "watchlistcatagory";
    public static String[] COLUMNS_CATAGORY = {"index", "name", "tablename"};
    public static String[] COLUMNS_CATAGORY_DATATYPE = {"INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT", "TEXT"};
    
    public static String[] COLUMNS_ENTRY = {"index", "name", "info", "status", "episodes", "seen", "timecreated", "timemodified"};
    public static String[] COLUMNS_ENTRY_DATATYPE = {"INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT", "TEXT"};
    //Database connectivity variables
    private Connection databaseConn;
    private Statement databaseStmt;
    private ResultSet databaseRs;

    // Load database driver
    static {
        try {
            Class.forName("org.sqlite.JDBC").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    // Constructor for database class
    public DatabaseManager() {
        
        init();
    }

    private void init() {
        try {
            // Get connection from the database location
            databaseConn = DriverManager.getConnection(databaseLocation);
        } catch (SQLException e) {
            MainLoader.logger.log(e);
        }

        try {
            // Create statement from the received connection
            databaseStmt = databaseConn.createStatement();
        } catch (SQLException e) {
            MainLoader.logger.log(e);
        }

        // Run this method every time this class is initialized.
        // Checks for table, will create table if not found.
        
        try {
            // Tries to select the first column from the database table.
            // If error encountered, it will call method to create the table.
            databaseRs = databaseStmt.executeQuery("SELECT '" + COLUMNS_CATAGORY[0] + "' FROM " + TABLE_CATAGORY);
        } catch (SQLException e) {
            MainLoader.logger.log(e);
            // Calls method to create table
            createCatagoryTable();
        }
    }

    // Table Creation method
    private void createCatagoryTable() {
        String tmp = "";
        if (COLUMNS_CATAGORY.length == COLUMNS_CATAGORY_DATATYPE.length) {
            // Produce formated String for SQL Query, intended for automation
            tmp = tmp + "'" + COLUMNS_CATAGORY[0] + "'" + " " + COLUMNS_CATAGORY_DATATYPE[0];
            for (int i = 1; i < COLUMNS_CATAGORY.length; i++) {
                tmp = tmp + ",'" + COLUMNS_CATAGORY[i] + "'" + " " + COLUMNS_CATAGORY_DATATYPE[i];
            }
        }
        try {
            // Query to create database
            databaseStmt.execute("CREATE TABLE " + TABLE_CATAGORY + "(" + tmp + ");");
        } catch (SQLException e) {
            MainLoader.logger.log(e);
        }
    }

    // Close database connection and statement connection.
    public void closeDatabase() {
        try {
            this.databaseStmt.close();
            this.databaseConn.close();
        } catch (SQLException e) {
            MainLoader.logger.log(e);
        }
    }

    public ObservableList getCatagoryList() {
        ObservableList<Catagory> catagoryList = FXCollections.observableArrayList();
        
        try {
            databaseRs = databaseStmt.executeQuery("SELECT * FROM " + TABLE_CATAGORY + ";");
        } catch (SQLException e) {
            MainLoader.logger.log(e);
        }

        try {
            while (databaseRs.next()) {
                catagoryList.add(new Catagory(databaseRs.getString(COLUMNS_CATAGORY[0]),databaseRs.getString(COLUMNS_CATAGORY[1]),databaseRs.getString(COLUMNS_CATAGORY[2])));
            }
        } catch (SQLException e) {
            MainLoader.logger.log(e);
        }
        return catagoryList;
    }
    
    public ObservableList getSortedCatagoryList() {
        ObservableList<Catagory> catagoryList = FXCollections.observableArrayList();
        
        try {
            databaseRs = databaseStmt.executeQuery("SELECT * FROM " + TABLE_CATAGORY + ";");
        } catch (SQLException e) {
            MainLoader.logger.log(e);
        }

        try {
            while (databaseRs.next()) {
                catagoryList.add(new Catagory(databaseRs.getString(COLUMNS_CATAGORY[0]),databaseRs.getString(COLUMNS_CATAGORY[1]),databaseRs.getString(COLUMNS_CATAGORY[2])));
            }
        } catch (SQLException e) {
            MainLoader.logger.log(e);
        }
        return new SortedList(catagoryList).sorted();
    }
    
    public ObservableList getEntryList(Catagory selection) {
        ObservableList<Entry> entryList = FXCollections.observableArrayList();
        try {
            databaseRs = databaseStmt.executeQuery("SELECT `" 
                    + COLUMNS_ENTRY[0] + "`,`"
                    + COLUMNS_ENTRY[1] + "` FROM " + selection.getTableName() + ";");
        } catch (SQLException e) {
            MainLoader.logger.log(e);
        }
        
        try {
            while (databaseRs.next()) {
                entryList.add(new Entry(databaseRs.getString(COLUMNS_ENTRY[0]),databaseRs.getString(COLUMNS_ENTRY[1]), selection.getTableName()));
            }
        } catch (SQLException e) {
            MainLoader.logger.log(e);
        }
        return entryList;
    }
    
    public ObservableList getSortedEntryList(Catagory selection) {
        ObservableList<Entry> entryList = FXCollections.observableArrayList();
        try {
            databaseRs = databaseStmt.executeQuery("SELECT `" 
                    + COLUMNS_ENTRY[0] + "`,`"
                    + COLUMNS_ENTRY[1] + "` FROM " + selection.getTableName() + ";");
        } catch (SQLException e) {
            MainLoader.logger.log(e);
        }
        
        try {
            while (databaseRs.next()) {
                entryList.add(new Entry(databaseRs.getString(COLUMNS_ENTRY[0]),databaseRs.getString(COLUMNS_ENTRY[1]), selection.getTableName()));
            }
        } catch (SQLException e) {
            MainLoader.logger.log(e);
        }
        return new SortedList(entryList).sorted();
    }
    
    public EntryDetailed getEntryDetailed(Entry entry) {
        EntryDetailed detailed = null;
        try {
            databaseRs = databaseStmt.executeQuery("SELECT * FROM " + entry.getTableName() + " WHERE `" + COLUMNS_ENTRY[0] + "` = " + Integer.parseInt(entry.getIndex_id()) + ";");
        } catch (SQLException e) {
            MainLoader.logger.log(e);
        }

        try {
            databaseRs.next();
            detailed = new EntryDetailed(entry.getTableName(), databaseRs.getString(COLUMNS_ENTRY[0]),databaseRs.getString(COLUMNS_ENTRY[1]),databaseRs.getString(COLUMNS_ENTRY[2]), databaseRs.getString(COLUMNS_ENTRY[3]),databaseRs.getString(COLUMNS_ENTRY[4]),databaseRs.getString(COLUMNS_ENTRY[5]), databaseRs.getString(COLUMNS_ENTRY[6]),databaseRs.getString(COLUMNS_ENTRY[7]));
        } catch (SQLException e) {
            MainLoader.logger.log(e);
        }
        
        return detailed;
    }
    
    public boolean newCatagory(String name) {
        String id = name.toLowerCase().replace(' ', 'z');
        String tableID = "entrytable" + id + String.valueOf((System.currentTimeMillis()) % 1000);
        tableID += String.valueOf(tableID.hashCode() < 0 ? (tableID.hashCode() * -1) : tableID.hashCode());
        String tmp_s = "INSERT INTO '" + TABLE_CATAGORY + "'('" + COLUMNS_CATAGORY[1] + "','" + COLUMNS_CATAGORY[2] + "') VALUES('" + name + "','" + tableID + "');";

        try {
                // Database query to insert data into the table.
                databaseStmt.execute(tmp_s);
        } catch (SQLException e) {
                MainLoader.logger.log(e);
                return false;
        }
        return createEntryTable(tableID);
    }
    
    public void importCatagory(String tableID, String name) {
        String tmp_s = "INSERT INTO '" + TABLE_CATAGORY + "'('" + COLUMNS_CATAGORY[1] + "','" + COLUMNS_CATAGORY[2] + "') VALUES('" + name + "','" + tableID + "');";

        try {
                // Database query to insert data into the table.
                databaseStmt.execute(tmp_s);
        } catch (SQLException e) {
                MainLoader.logger.log(e);
        }
        createEntryTable(tableID);
    }
    
    public boolean newEntry(Catagory selection, String name, String info, String status, String episodes, String seen) {
        String currentTime = String.valueOf(System.currentTimeMillis());
        String tmp_s = "INSERT INTO '" + selection.getTableName() + "'('" 
                + COLUMNS_ENTRY[1] + "','"
                + COLUMNS_ENTRY[2] + "','"
                + COLUMNS_ENTRY[3] + "','"
                + COLUMNS_ENTRY[4] + "','"
                + COLUMNS_ENTRY[5] + "','"
                + COLUMNS_ENTRY[6] + "','"
                + COLUMNS_ENTRY[7] + "') VALUES('" 
                + name + "','" 
                + info + "','" 
                + status + "','" 
                + episodes + "','" 
                + seen + "','" 
                + currentTime + "','" 
                + currentTime + "');";

        try {
                // Database query to insert data into the table.
                databaseStmt.execute(tmp_s);
                
                return true;
        } catch (SQLException e) {
                MainLoader.logger.log(e);
                return false;
        }
    }
    
    public void importEntry(String tableID, String id, String name, String info, String status, String episodes, String seen) {
        String currentTime = String.valueOf(System.currentTimeMillis());
        String tmp_s = "INSERT INTO '" + tableID + "'('"
                + COLUMNS_ENTRY[0] + "','"
                + COLUMNS_ENTRY[1] + "','"
                + COLUMNS_ENTRY[2] + "','"
                + COLUMNS_ENTRY[3] + "','"
                + COLUMNS_ENTRY[4] + "','"
                + COLUMNS_ENTRY[5] + "','"
                + COLUMNS_ENTRY[6] + "','"
                + COLUMNS_ENTRY[7] + "') VALUES('" 
                + id + "','" 
                + name + "','" 
                + info + "','" 
                + status + "','" 
                + episodes + "','" 
                + seen + "','" 
                + currentTime + "','" 
                + currentTime + "');";

        try {
                // Database query to insert data into the table.
                databaseStmt.execute(tmp_s);
        } catch (SQLException e) {
                MainLoader.logger.log(e);
        }
    }
    
    public boolean editCatagory(Catagory selection, String newName) {
        try {
            databaseStmt.execute("UPDATE " + TABLE_CATAGORY + " SET "
                + COLUMNS_CATAGORY[1] + " = '" + newName + "' "
                + "WHERE `" + COLUMNS_CATAGORY[0] + "` = " + Integer.parseInt(selection.getIndexID()) + ";");
            return true;
        } catch (SQLException e) {
            MainLoader.logger.log(e);
            return false;
        }
    }
    
    public boolean editEntry(EntryDetailed entry) {
        try {
            databaseStmt.execute("UPDATE " + entry.getTable() + " SET "
                + COLUMNS_ENTRY[1] + " = '" + entry.getName() + "', "
                + COLUMNS_ENTRY[2] + " = '" + entry.getInfo() + "', "
                + COLUMNS_ENTRY[3] + " = '" + entry.getStatus() + "', "
                + COLUMNS_ENTRY[4] + " = '" + entry.getEpisodes() + "', "
                + COLUMNS_ENTRY[5] + " = '" + entry.getSeen() + "', "
                + COLUMNS_ENTRY[6] + " = '" + entry.getTime_created() + "', "
                + COLUMNS_ENTRY[7] + " = '" + entry.getTime_modified() + "' "
                + "WHERE `" + COLUMNS_ENTRY[0] + "` = " + Integer.parseInt(entry.getIndex()) + ";");
            return true;
        } catch (SQLException e) {
            MainLoader.logger.log(e);
            return false;
        }
    }
    
    public boolean deleteCatagory(Catagory selection) {
        try {
            databaseStmt.execute("DELETE FROM " + TABLE_CATAGORY + " WHERE `" + COLUMNS_CATAGORY[0] + "`=" + selection.getIndexID() + " ;");
            databaseStmt.execute("DROP TABLE '" + selection.getTableName() + "';");
            return true;
        } catch (SQLException e) {
            MainLoader.logger.log(e);
            return false;
        }
    }
    
    public boolean deleteEntry(Entry selection) {
        try {
            databaseStmt.execute("DELETE FROM " + selection.getTableName() + " WHERE `" + COLUMNS_ENTRY[0] + "`=" + selection.getIndex_id() + " ;");
            return true;
        } catch (SQLException e) {
            MainLoader.logger.log(e);
            return false;
        }
    }
    
    private boolean createEntryTable(String table) {
        String tmp = "";
        if (COLUMNS_ENTRY.length == COLUMNS_ENTRY_DATATYPE.length) {
            // Produce formated String for SQL Query, intended for automation
            tmp = tmp + "'" + COLUMNS_ENTRY[0] + "'" + " " + COLUMNS_ENTRY_DATATYPE[0];
            for (int i = 1; i < COLUMNS_ENTRY.length; i++) {
                tmp = tmp + ",'" + COLUMNS_ENTRY[i] + "'" + " " + COLUMNS_ENTRY_DATATYPE[i];
            }
        }
        try {
            // Query to create database
            databaseStmt.execute("CREATE TABLE " + table + "(" + tmp + ");");
            return true;
        } catch (SQLException e) {
            MainLoader.logger.log(e);
            return false;
        }
    }
    
    public ObservableList<PieChart.Data> getChartData(boolean showExtra) {
        ObservableList<PieChart.Data> result = FXCollections.observableArrayList();
        try {
            // Query to create database
            databaseRs = databaseStmt.executeQuery("SELECT * FROM " + TABLE_CATAGORY + ";");
        } catch (SQLException e) {
            MainLoader.logger.log(e);
        }
        ArrayList<String> nameList = new ArrayList();
        ArrayList<String> tableList = new ArrayList();
        try {
            while(databaseRs.next()) {
                nameList.add(databaseRs.getString(COLUMNS_CATAGORY[1]));
                tableList.add(databaseRs.getString(COLUMNS_CATAGORY[2]));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int size = tableList.size();
        int count;
        for(int i = 0; i < size; i++) {
            try {
                databaseRs = databaseStmt.executeQuery("SELECT COUNT(*) FROM " + tableList.get(i) + ";");
                if(showExtra) {
                    count = databaseRs.getInt(1);
                    result.add(new PieChart.Data(nameList.get(i) + "(" + count + ")" , count));
                } else {
                    result.add(new PieChart.Data(nameList.get(i), databaseRs.getInt(1)));
                }                
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    public boolean exportToFile(File file) {
        ObservableList<Catagory> catList = getCatagoryList();
        ObservableList<Entry> entryList;
        EntryDetailed detailed;
        PrintWriter writer;
        
        try {
            writer = new PrintWriter(new FileOutputStream(file));
        } catch (FileNotFoundException ex) {
            MainLoader.logger.log(ex);
            return false;
        }
        
        writer.println("[WatchListManager]");
        writer.println(String.valueOf(catList.size()));
        
        for(Catagory cat : catList) {
            writer.println(cat.getTableName() + ";" + cat.getName());
        }
        
        for(Catagory cat : catList) {
            entryList = getEntryList(cat);
            for(Entry entry : entryList) {
                detailed = getEntryDetailed(entry);
                writer.println(entry.getTableName() + ";" + detailed.getIndex()
                + ";" + detailed.getName()
                + ";" + detailed.getInfo()
                + ";" + detailed.getEpisodes()
                + ";" + detailed.getStatus()
                + ";" + detailed.getSeen());
            }
        }
        writer.close();
        return true;
    }
    
    public boolean importFromFile(File file) {
        closeDatabase();
        File dbFile = new File(databaseFileLocation);
        dbFile.delete();
        init();
        
        ObservableList<Catagory> catList;
        ObservableList<Entry> entryList;
        EntryDetailed detailed;
        BufferedReader reader;
        
        try {
            reader = new BufferedReader(new FileReader(file));
            String tmp = reader.readLine();
            if(!tmp.equals("[WatchListManager]")) {
                return false;
            }
            int count = Integer.parseInt(reader.readLine());
            
            for(int i = 0; i < count; i++) {
                tmp = reader.readLine();
                String[] tmp2 = tmp.split(";");
                importCatagory(tmp2[0], tmp2[1]);
            }
            while((tmp = reader.readLine()) != null) {
                String[] tmp2 = tmp.split(";");
                importEntry(tmp2[0], tmp2[1], tmp2[2], tmp2[3], tmp2[4], tmp2[5], tmp2[6]);
            }
        } catch (FileNotFoundException ex) {
            MainLoader.logger.log(ex);
            return false;
        } catch (IOException ex) {
            MainLoader.logger.log(ex);
            return false;
        }
        
        return true;
    }
}
