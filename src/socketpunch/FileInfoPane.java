/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketpunch;

import com.olmectron.material.components.MaterialDisplayText;
import com.olmectron.material.constants.MaterialColor;
import javafx.scene.layout.VBox;

/**
 *
 * @author Edgar
 */
public class FileInfoPane extends VBox{
    private InstallableFile sourceFile;
    public FileInfoPane(InstallableFile sourceFile){
        super();
        this.sourceFile=sourceFile;
        initAll();
    }
    private void initAll(){
        MaterialDisplayText nameText=new MaterialDisplayText(sourceFile.getName());
        MaterialDisplayText serialText=new MaterialDisplayText(sourceFile.getSerial());
        MaterialDisplayText regionText=new MaterialDisplayText(sourceFile.getRegion());
        nameText.setFontSize(15);
        nameText.setColorCode(MaterialColor.material.BLACK_87);
        
        serialText.setFontSize(12);
        serialText.setColorCode(MaterialColor.material.BLACK_54);
        
        regionText.setFontSize(13);
        regionText.setColorCode(MaterialColor.material.INDIGO);
        
        getChildren().addAll(serialText,nameText,regionText);
        System.out.println(nameText.getText());
        setMinWidth(200);
        setPrefHeight(2000);
        setStyle(getStyle()+"-fx-background-color: #FFFFFF;");
    }
}
