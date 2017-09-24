package com.jpmorgan.fx;

import com.jpmorgan.difflib.algorithm.DiffException;
import com.jpmorgan.difflib.text.DiffRow;
import com.jpmorgan.difflib.text.DiffRowGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Controller {

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button check;


    @FXML
    private TextField text1;


    @FXML
    private TextField text2;

   @FXML
   private ListView listViewLeft;


    @FXML
    private ListView listViewRight;


    private File file1 = null;
    private File file2 = null;

    public void button1Action(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        file1 = fileChooser.showOpenDialog(null);
        text1.setText(file1.getName());
        System.out.println(file1.getName());
    }

    public void button2Action(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        file2 = fileChooser.showOpenDialog(null);
        text2.setText(file2.getName());
        System.out.println(file2.getName());
    }


    public void checkAction(ActionEvent actionEvent) {

        Scanner s1 = null;
        Scanner s2 = null;
        ArrayList<String> list1 = new ArrayList<String>();
        ArrayList<String> list2 = new ArrayList<String>();
        try {
            s1 = new Scanner(file1);
            s2 = new Scanner(file2);

            while (s1.hasNext()){
                list1.add(s1.next());
            }
            while (s2.hasNext()){
                list2.add(s2.next());
            }

            DiffRowGenerator generator = DiffRowGenerator.create()
                    .showInlineDiffs(true)
                    .inlineDiffByWord(true)
                    .oldTag(f -> "")
                    .newTag(f -> "---")
                    .build();
            List<DiffRow> rows = generator.generateDiffRows(list1,list2);

            for (DiffRow row : rows) {
                if((row.getTag().equals(DiffRow.Tag.CHANGE)) || (row.getTag().equals(DiffRow.Tag.INSERT))) {

                    listViewLeft.getItems().add(row.getOldLine());
                    listViewRight.getItems().add(row.getNewLine());
                    System.out.println("|" + row.getOldLine() + "|" + row.getNewLine() + "|");
                }
            }

        } catch (FileNotFoundException | DiffException e) {
            e.printStackTrace();
        }

        if (s1 != null) {
            s1.close();
        }
        if (s2 != null) {
            s2.close();
        }

    }
}


