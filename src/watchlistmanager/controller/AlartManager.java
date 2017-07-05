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

package watchlistmanager.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Arshad
 */
class AlartManager {
    
    public static void showInfoAlart(String title, String header, String context) {
        Alert alart = new Alert(Alert.AlertType.INFORMATION);
        alart.setResizable(false);
        Stage tmpStage = (Stage)(alart.getDialogPane().getScene().getWindow());
        tmpStage.getIcons().add(new Image(AlartManager.class.getResourceAsStream("/watchlistmanager/view/icons/main_icon.png")));
        alart.setTitle(title);
        alart.setContentText(context);
        alart.setHeaderText(header);
        alart.setResult(ButtonType.OK);
        
        alart.show();
    }
    
    public static void showWarningAlart(String title, String header, String context) {
        Alert alart = new Alert(Alert.AlertType.WARNING,context, ButtonType.CLOSE);
        alart.setResizable(false);
        Stage tmpStage = (Stage)(alart.getDialogPane().getScene().getWindow());
        tmpStage.getIcons().add(new Image(AlartManager.class.getResourceAsStream("/watchlistmanager/view/icons/main_icon.png")));
        alart.setTitle(title);
        alart.setHeaderText(header);
        
        alart.show();
    }
    
    public static boolean showConfirmAlart(String title, String header, String context) {
        Alert alart = new Alert(Alert.AlertType.CONFIRMATION,context, ButtonType.OK, ButtonType.CANCEL);
        alart.initModality(Modality.APPLICATION_MODAL);
        Stage tmpStage = (Stage)(alart.getDialogPane().getScene().getWindow());
        tmpStage.getIcons().add(new Image(AlartManager.class.getResourceAsStream("/watchlistmanager/view/icons/main_icon.png")));
        alart.setResizable(false);
        alart.setTitle(title);
        alart.setHeaderText(header);
        
        return alart.showAndWait().get().equals(ButtonType.OK);
    }
}
