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

package watchlistmanager;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import watchlistmanager.model.logging.AppLogger;
import watchlistmanager.model.sqlite.DatabaseManager;

/**
 *
 * @author Arshad
 */
public class MainLoader extends Application {
    public static Stage mainStage;
    public static Application myApp;
    public static DatabaseManager dbMan;
    public static AppLogger logger;
    
    @Override
    public void start(Stage stage) throws Exception {
        logger = new AppLogger();
        mainStage = stage;
        myApp = this;
        dbMan = new DatabaseManager();
        
        AnchorPane root = FXMLLoader.load(getClass().getResource("/watchlistmanager/view/MainGUI.fxml"));
        Scene scene = new Scene(root, root.getMinWidth(), root.getMinHeight());
        
        stage.setResizable(false);
        stage.setTitle("Watch List Manager");
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/watchlistmanager/view/icons/main_icon.png")));
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void stop() {
        dbMan.closeDatabase();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static void closeApplication() {
        try {
            myApp.stop();
        } catch (Exception ex) {
            Logger.getLogger(MainLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }
}
