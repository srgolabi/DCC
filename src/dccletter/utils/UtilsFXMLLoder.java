/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dccletter.utils;

import dccletter.DCCLetter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;

/**
 *
 * @author reza
 */
public class UtilsFXMLLoder<T> extends FXMLLoader{

    private T page;
    
    public UtilsFXMLLoder(String address) {
        super(DCCLetter.class.getResource(address));
        try {
            page =(T) load();
        } catch (IOException ex) {
            Logger.getLogger(UtilsFXMLLoder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public T getPage(){
        return this.page;
    }
    
}
