/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Arco;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	private boolean creato = false;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnConnessioneMassima"
    private Button btnConnessioneMassima; // Value injected by FXMLLoader

    @FXML // fx:id="btnCollegamento"
    private Button btnCollegamento; // Value injected by FXMLLoader

    @FXML // fx:id="txtMinuti"
    private TextField txtMinuti; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMese"
    private ComboBox<Integer> cmbMese; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM1"
    private ComboBox<Match> cmbM1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM2"
    private ComboBox<Match> cmbM2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doConnessioneMassima(ActionEvent event) {
    	txtResult.clear();
    	if(!creato) {
    		txtResult.setText("Creare prima il grafo");
    		return;
    	}
    	else {
    		for(Arco a : model.connessioni()) {
        		txtResult.appendText(a+"\n");
        	}
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	Integer mese = cmbMese.getValue();
    	if(mese==null) {
    		txtResult.setText("Selezionare un mese dalla tendina");
    		return;
    	}
    	String minuti = txtMinuti.getText();
    	int min;
    	try {
    		min = Integer.parseInt(minuti);
    	} catch(NumberFormatException e) {
    		txtResult.setText("Inserire un numero intero di minuti");
    		return;
    	}
    	model.creaGrafo(min, mese);
    	cmbM1.getItems().addAll(model.getVertici());
    	cmbM2.getItems().addAll(model.getVertici());
    	creato = true;
    	txtResult.setText("Grafo creato con:\n"+model.getVertici().size()+" vertici\n"+model.getArchi().size()+" archi");
    }

    @FXML
    void doCollegamento(ActionEvent event) {
    	txtResult.clear();
    	if(!creato) {
    		txtResult.setText("Creare prima il grafo");
    		return;
    	}
    	else {
    		Match partenza = cmbM1.getValue();
        	Match arrivo = cmbM2.getValue();
        	if(partenza == null || arrivo == null) {
        		txtResult.setText("Selezionare i match dalla tendina");
        		return;
        	}
        	List<Match> percorso = model.percorso(partenza, arrivo);
        	if(percorso==null)
        		txtResult.setText("Percorso non trovato");
        	else {
        		txtResult.setText("Percorso trovato con peso "+model.getPesoPercorso()+":\n\n");
            	for(Match m : percorso) {
            		txtResult.appendText(m+"\n");
            	}
        	}
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnessioneMassima != null : "fx:id=\"btnConnessioneMassima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCollegamento != null : "fx:id=\"btnCollegamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMinuti != null : "fx:id=\"txtMinuti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMese != null : "fx:id=\"cmbMese\" was not injected: check your FXML file 'Scene.fxml'.";        assert cmbM1 != null : "fx:id=\"cmbM1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbM2 != null : "fx:id=\"cmbM2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	List<Integer> mesi = new LinkedList<>();
    	for(int i=1; i<=12; i++) {
    		mesi.add(i);
    	}
    	cmbMese.getItems().addAll(mesi);
    }
    
    
}
