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

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import watchlistmanager.MainLoader;
import watchlistmanager.model.data.Catagory;
import watchlistmanager.model.data.Entry;
import watchlistmanager.model.data.EntryDetailed;

public class MainGUIController implements Initializable {
    
    private enum AnchorPaneEnum{Charts, Viewer, AddCatagory, EditCatagory, AddItem, EditItem};
    private boolean pieChartShowExtra = false;
    
    private AnchorPaneEnum currentVisibleAnchorPane = AnchorPaneEnum.Viewer;

    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-YYYY hh:mm a");

    @FXML
    private AnchorPane AnchorPaneMain;

    @FXML
    private AnchorPane AnchorPaneSecondary;


    @FXML
    private AnchorPane AnchorPaneViewer;

    @FXML
    private ListView<?> ViewerListEntrySelection;

    @FXML
    private TextField ViewerTextFieldSearch;

    @FXML
    private TextField ViewerTextFieldIndex;

    @FXML
    private TextField ViewerTextFieldName;

    @FXML
    private TextField ViewerTextFieldInfo;

    @FXML
    private TextField ViewerTextFieldStatus;

    @FXML
    private TextField ViewerTextFieldEpisodes;

    @FXML
    private TextField ViewerTextFieldSeen;

    @FXML
    private TextField ViewerTextFieldDateEdited;

    @FXML
    private TextField ViewerTextFieldDateAdded;

    @FXML
    private TextField ViewerTextFieldCurrentDate;

    @FXML
    private ListView<?> ViewerListCatagory;

    @FXML
    private AnchorPane AnchorPaneAddCatagory;

    @FXML
    private ListView<?> AddCatListExisting;

    @FXML
    private TextField AddCatTextFieldName;

    @FXML
    private AnchorPane AnchorPaneEditCatagory;

    @FXML
    private ListView<?> EditCatListSelectCatagory;

    @FXML
    private TextField EditCatTextFieldName;

    @FXML
    private CheckBox EditCatCheckBoxDelete;

    @FXML
    private AnchorPane AnchorPaneAddItem;

    @FXML
    private ListView<?> AddEntryListEntry;

    @FXML
    private TextField AddEntryTextViewName;

    @FXML
    private TextField AddEntryTextViewInfo;

    @FXML
    private TextField AddEntryTextViewStatus;

    @FXML
    private TextField AddEntryTextViewEpisodes;

    @FXML
    private TextField AddEntryTextViewSeen;

    @FXML
    private ListView<?> AddEntryListCatagory;

    @FXML
    private AnchorPane AnchorPaneEditItem;

    @FXML
    private ListView<?> EditEntryListEntry;

    @FXML
    private TextField EditEntryTextFieldName;

    @FXML
    private TextField EditEntryTextFieldInfo;

    @FXML
    private TextField EditEntryTextFieldStatus;

    @FXML
    private TextField EditEntryTextFieldEpisodes;

    @FXML
    private TextField EditEntryTextFieldSeen;

    @FXML
    private ListView<?> EditEntryListCatagory;

    @FXML
    private CheckBox EditEntryCheckBoxDelete;

    @FXML
    private Label LabelStatus;

    @FXML
    private AnchorPane AnchorPaneCharts;

    @FXML
    private PieChart ChartPieChart;

    @FXML
    void clickedAddCatAdCat(ActionEvent event) {
        String tmp = AddCatTextFieldName.getText();
        if(tmp.equals("")) {
            
        } else {
            boolean result = MainLoader.dbMan.newCatagory(tmp);
            if(result) {
                this.LabelStatus.setText(tmp + " has been addad as catagory");
            } else {
                LabelStatus.setText("ERROR : There was some problem while adding new catagory");
            }
        }
        refreshAddCat();
    }

    @FXML
    void clickedAddCatCancel(ActionEvent event) {
        AnchorPaneManager(AnchorPaneEnum.Viewer);
    }

    @FXML
    void clickedAddEntryAddEntry(ActionEvent event) {
        StringBuilder context = new StringBuilder();
        context.append("Some fields are still empty !\n\n");
        if(AddEntryListCatagory.getSelectionModel().getSelectedIndex() != -1) {
            byte fieldCount = 0;
            if(AddEntryTextViewName.getText().equals("") || AddEntryTextViewName.getText().isEmpty()) {
                context.append("Empty Field : Name\n");
                fieldCount++;
            }
            if(AddEntryTextViewInfo.getText().equals("") || AddEntryTextViewInfo.getText().isEmpty()) {
                context.append("Empty Field : Info\n");
                fieldCount++;
            }
            if(AddEntryTextViewStatus.getText().equals("") || AddEntryTextViewStatus.getText().isEmpty()) {
                context.append("Empty Field : Status\n");
                fieldCount++;
            }
            if(AddEntryTextViewEpisodes.getText().equals("") || AddEntryTextViewEpisodes.getText().isEmpty()) {
                context.append("Empty Field : Episodes\n");
                fieldCount++;
            }
            if(AddEntryTextViewSeen.getText().equals("") || AddEntryTextViewSeen.getText().isEmpty()) {
                context.append("Empty Field : Seen\n");
                fieldCount++;
            } 
            if(fieldCount == 0) {
                boolean result = MainLoader.dbMan.newEntry((Catagory)AddEntryListCatagory.getSelectionModel().getSelectedItem(),
                        AddEntryTextViewName.getText(), AddEntryTextViewInfo.getText(), AddEntryTextViewStatus.getText(),
                        AddEntryTextViewEpisodes.getText(), AddEntryTextViewSeen.getText());
                if(result) {
                    LabelStatus.setText("Entry added successfully");
                } else {
                    LabelStatus.setText("ERROR : There were some problems while add new entry");
                }
                refreshAddEntry();
            } else {
                context.append("\nPlease fill up all fields");
                AlartManager.showWarningAlart("Oops!", "Add Entry", context.toString());
            }
            
        } else {
            AlartManager.showInfoAlart("Oops!", "Add Entry", "Please select a catagory first");
        }
    }

    @FXML
    void clickedAddEntryCancel(ActionEvent event) {
        AnchorPaneManager(AnchorPaneEnum.Viewer);
    }

    @FXML
    void clickedChartGoBack(ActionEvent event) {
        AnchorPaneManager(AnchorPaneEnum.Viewer);
    }

    @FXML
    void clickedChartShowExtra(ActionEvent event) {
        // Default value of pieChartShowExtra id false
        // pieChartShowExtra will change value every time the CheckBox is checked or unchecked
        pieChartShowExtra = !pieChartShowExtra;
        
        refreshChartData();
    }

    @FXML
    void clickedEditCatApply(ActionEvent event) {
        String tmp = EditCatTextFieldName.getText();
        boolean result;
        
        if(!EditCatCheckBoxDelete.isSelected()) {
            if(tmp.equals("") || (EditCatListSelectCatagory.getSelectionModel().getSelectedIndex() == -1)) {
                AlartManager.showWarningAlart("Error", "No Input", "Please choose an existing catagory from list first");
            } else {
                result = MainLoader.dbMan.editCatagory((Catagory)EditCatListSelectCatagory.getSelectionModel().getSelectedItem(), tmp);
                if(result) {
                    LabelStatus.setText("Catagory name edited successfylly");
                } else {
                    LabelStatus.setText("ERROR : There were some problems while editing the catagory name");
                }
            }
        } else {
            if(EditCatListSelectCatagory.getSelectionModel().getSelectedIndex() != -1) {
                if(AlartManager.showConfirmAlart("Edit/Delete Catagory", "Delete Catagory", "Are you sure you want to delete this catagory ?"
                        + "\nDeleting any catagory will also delete all entry under the selected catagory")) {
                    result = MainLoader.dbMan.deleteCatagory((Catagory)EditCatListSelectCatagory.getSelectionModel().getSelectedItem());
                    if(result) {
                        LabelStatus.setText("Catagory deleted successfully");
                    } else {
                        LabelStatus.setText("ERROR : There were some problems while deleting the catagory");
                    }
                } else {
                    LabelStatus.setText("The catagory delete was canceled");
                }
            } else {
                AlartManager.showWarningAlart("Error", "No Input", "Please choose an existing catagory from list first");
            }
        }
        refreshEditCat();
    }

    @FXML
    void clickedEditCatCancel(ActionEvent event) {
        AnchorPaneManager(AnchorPaneEnum.Viewer);
    }

    @FXML
    void clickedEditEntryApply(ActionEvent event) {
        if(EditEntryListEntry.getSelectionModel().getSelectedIndex() != -1) {
            if(EditEntryCheckBoxDelete.isSelected()) {
                if(AlartManager.showConfirmAlart("Warinng", "Delete Entry", "Are you sure you want to delete this entry ?\n It will be deleted permanently")) {
                    LabelStatus.setText("delete");
                    boolean result = MainLoader.dbMan.deleteEntry((Entry)EditEntryListEntry.getSelectionModel().getSelectedItem());
                    if(result) {
                        LabelStatus.setText("Entry deleted successfully");
                    } else {
                        LabelStatus.setText("ERROR : There were some problems while removing the selected entry");
                    }
                    refreshEditEntry();
                } else {
                    LabelStatus.setText("Entry delete operation war canceled");
                }
            } else {
                StringBuilder context = new StringBuilder();
                Entry ent;
                EntryDetailed detailed;
                context.append("Some fields ar still empty !\n\n");
                byte fieldCount = 0;
                ent = (Entry)EditEntryListEntry.getSelectionModel().getSelectedItem();

                if(EditEntryTextFieldName.getText().equals("") || EditEntryTextFieldName.getText().isEmpty()) {
                    context.append("Empty Field : Name\n");
                    fieldCount++;
                }
                if(EditEntryTextFieldInfo.getText().equals("") || EditEntryTextFieldInfo.getText().isEmpty()) {
                    context.append("Empty Field : Info\n");
                    fieldCount++;
                }
                if(EditEntryTextFieldStatus.getText().equals("") || EditEntryTextFieldStatus.getText().isEmpty()) {
                    context.append("Empty Field : Status\n");
                    fieldCount++;
                }
                if(EditEntryTextFieldEpisodes.getText().equals("") || EditEntryTextFieldEpisodes.getText().isEmpty()) {
                    context.append("Empty Field : Episodes\n");
                    fieldCount++;
                }
                if(EditEntryTextFieldSeen.getText().equals("") || EditEntryTextFieldSeen.getText().isEmpty()) {
                    context.append("Empty Field : Seen\n");
                    fieldCount++;
                } 
                if(fieldCount == 0) {
                    detailed = MainLoader.dbMan.getEntryDetailed(ent);
                    detailed.set(EditEntryTextFieldName.getText(), EditEntryTextFieldInfo.getText(), EditEntryTextFieldStatus.getText(), EditEntryTextFieldEpisodes.getText(), EditEntryTextFieldSeen.getText());
                    boolean result = MainLoader.dbMan.editEntry(detailed);
                    if(result) {
                        LabelStatus.setText("Entry edited successfully");
                    } else {
                        LabelStatus.setText("ERROR : There were some problems while editing the entry");
                    }
                    refreshEditEntry();
                } else {
                    context.append("\nPlease fill up all fields");
                    AlartManager.showWarningAlart("Oops!", "Add Entry", context.toString());
                }
            }
        } else {
            AlartManager.showInfoAlart("Oops!", "Edit/Delete Entry", "Please select an existing entry first");
        }
    }

    @FXML
    void clickedEditEntryCancel(ActionEvent event) {
        AnchorPaneManager(AnchorPaneEnum.Viewer);
    }

    @FXML
    void clickedMenuItemAbout(ActionEvent event) {
        AlartManager.showInfoAlart("About", "About the Author", "Ibrahim Khan Arshad"
                + "\nDepartment of Computer Science and Engineering"
                + "\nDaffodil International University"
                + "\nEmail : ib.arshad777@gmail.com");
    }

    @FXML
    void clickedMenuItemAddCatagory(ActionEvent event) {
        AnchorPaneManager(AnchorPaneEnum.AddCatagory);
    }

    @FXML
    void clickedMenuItemAddEntry(ActionEvent event) {
        AnchorPaneManager(AnchorPaneEnum.AddItem);
    }

    @FXML
    void clickedMenuItemEditCatagory(ActionEvent event) {
        AnchorPaneManager(AnchorPaneEnum.EditCatagory);
    }

    @FXML
    void clickedMenuItemEditItem(ActionEvent event) {
           AnchorPaneManager(AnchorPaneEnum.EditItem);
    }

    @FXML
    void clickedMenuItemExit(ActionEvent event) {
        MainLoader.closeApplication();
    }

    @FXML
    void clickedMenuItemExport(ActionEvent event) {
        FileChooser export = new FileChooser();
        export.setInitialDirectory(new File("."));
        export.setTitle("Choose file to export");
        export.setInitialFileName("ExportedWatchList.csv");
        export.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files", "*.csv"));
        File chosenFile = export.showSaveDialog(MainLoader.mainStage);
        if(chosenFile == null) {
            LabelStatus.setText("WatchList export was canceled");
        } else {
            boolean result = MainLoader.dbMan.exportToFile(chosenFile);
            if(result) {
                LabelStatus.setText("List exported to " + chosenFile.getName());
            } else {
                LabelStatus.setText("ERROR : Could not export list to " + chosenFile.getName());
            }
        }
    }

    @FXML
    void clickedMenuItemGoToHome(ActionEvent event) {
        AnchorPaneManager(AnchorPaneEnum.Viewer);
    }
    
    @FXML
    private void clickedMenuItemInfoChart(ActionEvent event) {
        AnchorPaneManager(AnchorPaneEnum.Charts);
    }

    @FXML
    void clickedMenuItemImport(ActionEvent event) {
        FileChooser export = new FileChooser();
        export.setInitialDirectory(new File("."));
        export.setTitle("Choose file to import from");
        export.setInitialFileName("ExportedWatchList.csv");
        export.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files", "*.csv"));
        File chosenFile = export.showOpenDialog(MainLoader.mainStage);
        if(chosenFile == null) {
            LabelStatus.setText("WatchList import was canceled");
        } else {
            boolean result = MainLoader.dbMan.importFromFile(chosenFile);
            if(result) {
                LabelStatus.setText("List imported from " + chosenFile.getName());
            } else {
                LabelStatus.setText("ERROR : Could not import list from " + chosenFile.getName());
            }
        }
    }

    @FXML
    void clickedMenuItemViewEntry(ActionEvent event) {
        AnchorPaneManager(AnchorPaneEnum.Viewer);
    }

    @FXML
    void clickedViewerRefresh(ActionEvent event) {
        refreshViewerList();
    }

    @FXML
    void clickedWelcomeExit(ActionEvent event) {
        MainLoader.closeApplication();
    }

    @FXML
    void clickedWelcomeViewEntry(ActionEvent event) {
        AnchorPaneManager(AnchorPaneEnum.Viewer);
    }
    
    @FXML
    private void clickedViewerListEntrySelection(MouseEvent event) {
        if(ViewerListEntrySelection.getSelectionModel().getSelectedIndex() != -1) {
            Entry ent = (Entry)ViewerListEntrySelection.getSelectionModel().getSelectedItem();
            EntryDetailed detailed = MainLoader.dbMan.getEntryDetailed(ent);

            ViewerTextFieldIndex.setText(detailed.getIndex());
            ViewerTextFieldName.setText(detailed.getName());
            ViewerTextFieldInfo.setText(detailed.getInfo());
            ViewerTextFieldStatus.setText(detailed.getStatus());
            ViewerTextFieldEpisodes.setText(detailed.getEpisodes());
            ViewerTextFieldSeen.setText(detailed.getSeen());
            date.setTime(Long.parseLong(detailed.getTime_created()));
            ViewerTextFieldDateAdded.setText(dateFormat.format(date));
            date.setTime(Long.parseLong(detailed.getTime_modified()));
            ViewerTextFieldDateEdited.setText(dateFormat.format(date));
        }
    }

    @FXML
    private void clickedViewerListCatagory(MouseEvent event) {
        Catagory selectedCat;
        if(ViewerListCatagory.getSelectionModel().getSelectedIndex() != -1) {
            selectedCat = (Catagory)ViewerListCatagory.getSelectionModel().getSelectedItem();
            ViewerListEntrySelection.setItems(MainLoader.dbMan.getSortedEntryList(selectedCat));
        }
        
    }

    @FXML
    private void clickedEditCatListSelectCatagory(MouseEvent event) {
        Catagory selectedCat = (Catagory) EditCatListSelectCatagory.getSelectionModel().getSelectedItem();
        EditCatTextFieldName.setText(selectedCat.getName());
    }

    @FXML
    private void clickedAddEntryListCatagory(MouseEvent event) {
        if(AddEntryListCatagory.getSelectionModel().getSelectedIndex() != -1) {
            Catagory cat = (Catagory)AddEntryListCatagory.getSelectionModel().getSelectedItem();
            AddEntryListEntry.setItems(MainLoader.dbMan.getEntryList(cat));
        }
    }

    @FXML
    private void clickedEditEntryListEntry(MouseEvent event) {
        if(EditEntryListEntry.getSelectionModel().getSelectedIndex() != -1) {
            Entry ent = (Entry)EditEntryListEntry.getSelectionModel().getSelectedItem();
            EntryDetailed detailed = MainLoader.dbMan.getEntryDetailed(ent);

            EditEntryTextFieldName.setText(detailed.getName());
            EditEntryTextFieldInfo.setText(detailed.getInfo());
            EditEntryTextFieldStatus.setText(detailed.getStatus());
            EditEntryTextFieldEpisodes.setText(detailed.getEpisodes());
            EditEntryTextFieldSeen.setText(detailed.getSeen());
        }
    }

    @FXML
    private void clickedEditEntryListCatagory(MouseEvent event) {
        Catagory selectedCat;
        if(EditEntryListCatagory.getSelectionModel().getSelectedIndex() != -1) {
            selectedCat = (Catagory)EditEntryListCatagory.getSelectionModel().getSelectedItem();
            EditEntryListEntry.setItems(MainLoader.dbMan.getSortedEntryList(selectedCat));
        }
    }
    
    @FXML
    private void ViewerTextFieldSearchKeyTyped(KeyEvent event) {
        if(ViewerListCatagory.getSelectionModel().getSelectedIndex() != -1) {
            Catagory cat = (Catagory)ViewerListCatagory.getSelectionModel().getSelectedItem();
            ObservableList list = MainLoader.dbMan.getEntryList(cat);
            //ObservableList ol = ViewerListEntrySelection.getItems();
            FilteredList<Entry> flist = new FilteredList<>(list, p -> true);
            flist.setPredicate((Entry predicate) -> {
                if(ViewerTextFieldSearch.getText().isEmpty() || ViewerTextFieldSearch.getText().equals("")) {
                    return true;
                }
                return predicate.getName().toLowerCase().contains(ViewerTextFieldSearch.getText().toLowerCase());
            });
            ViewerListEntrySelection.setItems((ObservableList)flist.sorted());
        }
    }
    
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert AnchorPaneMain != null : "fx:id=\"AnchorPaneMain\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert AnchorPaneSecondary != null : "fx:id=\"AnchorPaneSecondary\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert AnchorPaneViewer != null : "fx:id=\"AnchorPaneViewer\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert ViewerListEntrySelection != null : "fx:id=\"ViewerListEntrySelection\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert ViewerTextFieldSearch != null : "fx:id=\"ViewerTextFieldSearch\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert ViewerTextFieldIndex != null : "fx:id=\"ViewerTextFieldIndex\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert ViewerTextFieldName != null : "fx:id=\"ViewerTextFieldName\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert ViewerTextFieldInfo != null : "fx:id=\"ViewerTextFieldInfo\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert ViewerTextFieldStatus != null : "fx:id=\"ViewerTextFieldStatus\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert ViewerTextFieldEpisodes != null : "fx:id=\"ViewerTextFieldEpisodes\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert ViewerTextFieldSeen != null : "fx:id=\"ViewerTextFieldSeen\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert ViewerTextFieldDateEdited != null : "fx:id=\"ViewerTextFieldDateEdited\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert ViewerTextFieldDateAdded != null : "fx:id=\"ViewerTextFieldDateAdded\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert ViewerTextFieldCurrentDate != null : "fx:id=\"ViewerTextFieldCurrentDate\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert ViewerListCatagory != null : "fx:id=\"ViewerListCatagory\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert AnchorPaneAddCatagory != null : "fx:id=\"AnchorPaneAddCatagory\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert AddCatListExisting != null : "fx:id=\"AddCatListExisting\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert AddCatTextFieldName != null : "fx:id=\"AddCatTextFieldName\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert AnchorPaneEditCatagory != null : "fx:id=\"AnchorPaneEditCatagory\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert EditCatListSelectCatagory != null : "fx:id=\"EditCatListSelectCatagory\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert EditCatTextFieldName != null : "fx:id=\"EditCatTextFieldName\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert EditCatCheckBoxDelete != null : "fx:id=\"EditCatCheckBoxDelete\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert AnchorPaneAddItem != null : "fx:id=\"AnchorPaneAddItem\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert AddEntryListEntry != null : "fx:id=\"AddEntryListEntry\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert AddEntryTextViewName != null : "fx:id=\"AddEntryTextViewName\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert AddEntryTextViewInfo != null : "fx:id=\"AddEntryTextViewInfo\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert AddEntryTextViewStatus != null : "fx:id=\"AddEntryTextViewStatus\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert AddEntryTextViewEpisodes != null : "fx:id=\"AddEntryTextViewEpisodes\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert AddEntryTextViewSeen != null : "fx:id=\"AddEntryTextViewSeen\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert AddEntryListCatagory != null : "fx:id=\"AddEntryListCatagory\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert AnchorPaneEditItem != null : "fx:id=\"AnchorPaneEditItem\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert EditEntryListEntry != null : "fx:id=\"EditEntryListEntry\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert EditEntryTextFieldName != null : "fx:id=\"EditEntryTextFieldName\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert EditEntryTextFieldInfo != null : "fx:id=\"EditEntryTextFieldInfo\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert EditEntryTextFieldStatus != null : "fx:id=\"EditEntryTextFieldStatus\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert EditEntryTextFieldEpisodes != null : "fx:id=\"EditEntryTextFieldEpisodes\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert EditEntryTextFieldSeen != null : "fx:id=\"EditEntryTextFieldSeen\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert EditEntryListCatagory != null : "fx:id=\"EditEntryListCatagory\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert EditEntryCheckBoxDelete != null : "fx:id=\"EditEntryCheckBoxDelete\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert LabelStatus != null : "fx:id=\"LabelStatus\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert AnchorPaneCharts != null : "fx:id=\"AnchorPaneCharts\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert ChartPieChart != null : "fx:id=\"ChartPieChart\" was not injected: check your FXML file 'MainGUI.fxml'.";

        // Set default windows on initialization
        AnchorPaneAddCatagory.setVisible(false);
        AnchorPaneAddItem.setVisible(false);
        AnchorPaneEditCatagory.setVisible(false);
        AnchorPaneEditItem.setVisible(false);
        AnchorPaneViewer.setVisible(false);
        AnchorPaneSecondary.setVisible(false);
        AnchorPaneCharts.setVisible(false);
        AnchorPaneMain.setVisible(true);
        AnchorPaneManager(AnchorPaneEnum.Viewer);
    }
    
    private void AnchorPaneManager(AnchorPaneEnum anchorPane) {
                
        switch(currentVisibleAnchorPane) {
            case AddCatagory:
            {
                AnchorPaneAddCatagory.setVisible(false);
                break;
            }
            case AddItem:
            {
                AnchorPaneAddItem.setVisible(false);
                break;
            }
            case EditCatagory:
            {
                AnchorPaneEditCatagory.setVisible(false);
                break;
            }
            case EditItem:
            {
                AnchorPaneEditItem.setVisible(false);
                break;
            }
            case Viewer:
            {
                AnchorPaneViewer.setVisible(false);
                break;
            }
        }
        
        if(anchorPane == AnchorPaneEnum.Charts) {
            AnchorPaneSecondary.setVisible(false);
            AnchorPaneCharts.setVisible(true);
            refreshChartData();
        } else {
            AnchorPaneSecondary.setVisible(true);
            AnchorPaneCharts.setVisible(false);
            switch(anchorPane) {
                case AddCatagory:
                {
                    refreshAddCat();
                    AnchorPaneAddCatagory.setVisible(true);
                    break;
                }
                case AddItem:
                {
                    refreshAddEntry();
                    AnchorPaneAddItem.setVisible(true);
                    break;
                }
                case EditCatagory:
                {
                    refreshEditCat();
                    AnchorPaneEditCatagory.setVisible(true);
                    break;
                }
                case EditItem:
                {
                    refreshEditEntry();
                    AnchorPaneEditItem.setVisible(true);
                    break;
                }
                case Viewer:
                {
                    refreshViewerList();
                    AnchorPaneViewer.setVisible(true);
                    break;
                }
            }
        }
        
        currentVisibleAnchorPane = anchorPane;
    }
    
    private void refreshViewerList() {
        ViewerListCatagory.setItems(MainLoader.dbMan.getSortedCatagoryList());
        ViewerListEntrySelection.setItems(null);
        ViewerListEntrySelection.refresh();
        
        ViewerTextFieldIndex.setText("");
        ViewerTextFieldName.setText("");
        ViewerTextFieldInfo.setText("");
        ViewerTextFieldStatus.setText("");
        ViewerTextFieldEpisodes.setText("");
        ViewerTextFieldSeen.setText("");
        ViewerTextFieldDateAdded.setText("");
        ViewerTextFieldDateEdited.setText("");
        ViewerTextFieldSearch.setText("");
        
        date.setTime(System.currentTimeMillis());
        ViewerTextFieldCurrentDate.setText(dateFormat.format(date));
    }
    
    private void refreshAddCat() {
        AddCatTextFieldName.setText("");
        AddCatListExisting.setItems(MainLoader.dbMan.getSortedCatagoryList());
    }
    
    private void refreshEditCat() {
        EditCatTextFieldName.setText("");
        EditCatCheckBoxDelete.setSelected(false);
        EditCatListSelectCatagory.setItems(MainLoader.dbMan.getSortedCatagoryList());
    }
    
    private void refreshAddEntry() {
        AddEntryListCatagory.setItems(MainLoader.dbMan.getSortedCatagoryList());
        AddEntryListEntry.getItems().clear();
        AddEntryListEntry.refresh();
        
        AddEntryTextViewName.setText("");
        AddEntryTextViewInfo.setText("");
        AddEntryTextViewStatus.setText("");
        AddEntryTextViewEpisodes.setText("");
        AddEntryTextViewSeen.setText("");
    }
    
    public void refreshEditEntry() {
        EditEntryListCatagory.setItems(MainLoader.dbMan.getSortedCatagoryList());
        EditEntryListEntry.setItems(null);
        EditEntryListEntry.refresh();
        
        EditEntryTextFieldName.setText("");
        EditEntryTextFieldInfo.setText("");
        EditEntryTextFieldStatus.setText("");
        EditEntryTextFieldEpisodes.setText("");
        EditEntryTextFieldSeen.setText("");
        
        EditEntryCheckBoxDelete.setSelected(false);
    }
    
    private void refreshChartData() {
        ChartPieChart.setData(MainLoader.dbMan.getChartData(pieChartShowExtra));
    }
}
