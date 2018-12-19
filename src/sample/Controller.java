package sample;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;


public class Controller {

    public ChoiceBox<String> odsjekBox;
    public DatePicker datum;
    public TextField JMBG;
    public TextField imeField;
    public TextField adresaField;
    public TextField telefonField;
    public TextField mailField;
    public TextField prezimeField;
    public TextField brojIndeksaField;
    public ToggleGroup status;
    public CheckBox boracko;
    public RadioButton red;
    HashMap<Control, Boolean> greske = new HashMap<>();

    @FXML
    public ChoiceBox<String> godinaBox;
    @FXML
    private ComboBox<String> mjestoBox;
    @FXML
    public ChoiceBox<String> ciklusBox;
    ObservableList<String> mjesto = FXCollections.observableArrayList("Sarajevo", "Zenica", "Tuzla", "Bihać", "Banja Luka", "Konjic", "Mostar");
    ObservableList<String> odsjek = FXCollections.observableArrayList("AE", "EE", "RI", "TK");
    ObservableList<String> godina = FXCollections.observableArrayList("prva", "druga", "treća");
    ObservableList<String> ciklus = FXCollections.observableArrayList("bachelor", "master", "doktorski studij", "stručni studij");


    @FXML
    private void initialize() {

        odsjekBox.setValue("AE");
        odsjekBox.setItems(odsjek);
        godinaBox.setValue("prva");
        godinaBox.setItems(godina);
        mjestoBox.setValue("Sarajevo");
        mjestoBox.setItems(mjesto);
        ciklusBox.setValue("bachelor");
        ciklusBox.setItems(ciklus);
        prezimeField.setPromptText("Unesite prezime");
        imeField.setPromptText("Unesite ime");
        brojIndeksaField.setPromptText("Unesite broj indeksa");
        JMBG.setPromptText("Unesite JMBG");
        mailField.setPromptText("Unesite e-mail");
        telefonField.setPromptText("Unesite broj telefona");
        adresaField.setPromptText("Unesite adresu");
        status.selectToggle(red);

        datum.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate o, LocalDate n) {
                if (!greske.containsKey(datum)) greske.put(datum, true);
                if (!greske.containsKey(JMBG) || greske.get(JMBG)) {
                    JMBG.setStyle("-fx-background-color: rgba(255,45,86,0.74)");
                    datum.setStyle("-fx-background-color: rgba(255,45,86,0.74)");

                } else if (!izdvoji().equals(datum.getValue())) {
                    greske.replace(datum, true);
                    datum.setStyle("-fx-background-color: rgba(255,45,86,0.74)");
                } else {
                    greske.replace(datum, false);
                    datum.setStyle("-fx-background-color: rgba(72,128,85,0.4)");
                }
            }
        });

        telefonField.textProperty().addListener((obs, ov, nv) -> {
                    boolean greskao = false;
                    if (!greske.containsKey(telefonField)) greske.put(telefonField, true);
                    if (telefonField.getText().length() > 0 && telefonField.getText().charAt(0) != '+') greskao = true;
                    for (int i = 1; i < telefonField.getText().length(); i++) {
                        if (!Character.isDigit(telefonField.getText().charAt(i))) greskao = true;
                    }
                    if (telefonField.getText().length() != 12) greskao = true;
                    if (greskao) {
                        greske.replace(telefonField, true);
                        telefonField.setStyle("-fx-background-color: rgba(255,45,86,0.74)");
                    } else {
                        greske.replace(telefonField, false);
                        telefonField.setStyle("-fx-background-color: rgba(72,128,85,0.4)");
                    }

                }
        );

        mailField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> obs, String o, String n) {
                EmailValidator validator = new EmailValidator();
                if (!greske.containsKey(mailField)) greske.put(mailField, true);
                if (!validator.validate(n)) {
                    greske.replace(mailField, true);
                    mailField.setStyle("-fx-background-color: rgba(255,45,86,0.74)");
                } else {
                    greske.replace(mailField, false);
                    mailField.setStyle("-fx-background-color: rgba(72,128,85,0.4)");
                }
            }
        });
        ValidationSupport validationSupport = new ValidationSupport();
        imeField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean o, Boolean n) {
                if (!n) {
                    validationSupport.registerValidator(imeField, Validator.createEmptyValidator("This field cannot be empty!"));
                }

            }
        });
        prezimeField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean o, Boolean n) {
                if (!n) {
                    validationSupport.registerValidator(prezimeField, Validator.createEmptyValidator("This field cannot be empty!"));
                }

            }
        });


    }

    public void krajUnosa(KeyEvent keyEvent) {
        if (!greske.containsKey(imeField)) greske.put(imeField, false);
        greske.replace(imeField, oboji(imeField));
    }

    public void krajUnosaP(KeyEvent keyEvent) {
        if (!greske.containsKey(prezimeField)) greske.put(prezimeField, false);
        greske.replace(prezimeField, oboji(prezimeField));
    }

    private boolean oboji(TextField ime) {

        boolean greskaO = false;
        if (ime.getText().length() < 1 || ime.getText().length() > 20 || !Character.isLetter(ime.getText().charAt(ime.getText().length() - 1))) {
           /* ime.getStyleClass().removeAll("poljeIspravno");
            ime.getStyleClass().add("poljeNeispravno");*/
            ime.setStyle("-fx-background-color: rgba(255,45,86,0.74)");
            greskaO = true;
        } else {
            /*ime.getStyleClass().removeAll("poljeNeispravno");
            ime.getStyleClass().add("poljeIspravno");*/
            ime.setStyle("-fx-background-color: rgba(72,128,85,0.4)");
            greskaO = false;
        }

        return greskaO;
    }


    public void submit(ActionEvent actionEvent) {

       /* for(Map.Entry<Control,Boolean> a: greske.entrySet()){
            if(a.getValue()==true) System.out.println(" "+a.getKey().toString());

        }*/

        if (greske.containsValue(true) || !(((greske.size() == 8 && !greske.containsKey(mjestoBox)) ||
                (greske.size() == 8 && !greske.containsKey(adresaField)) || (greske.size() == 7 && !greske.containsKey(mjestoBox) && !greske.containsKey(adresaField))
                || greske.size() == 9))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("POGREŠAN UNOS");
            if (greske.containsValue(true)) alert.setHeaderText("Neispravni podaci");
            else alert.setHeaderText("Nepotpuni podaci");
            alert.setContentText("Unesite ponovo!");

            alert.showAndWait();


        } else {
            System.out.println("Osnovni podaci: " + imeField.getText() + " " + prezimeField.getText() + " " + brojIndeksaField.getText());
            System.out.println("Detaljni lični podaci: " + JMBG.getText() + " " + datum.getValue().toString() + " " + mjestoBox.getValue());
            System.out.println("Kontakt podaci: " + adresaField.getText() + " " + telefonField.getText() + " " + mailField.getText());
            System.out.println("Podaci o studiju: Odsjek " +
                    " " + odsjekBox.getValue() + " " + godinaBox.getValue() + " godina Ciklus: " + ciklusBox.getValue()
                    + " " + ((RadioButton) status.getSelectedToggle()).getText() + " Boracka:kategorija " + boracko.getText());


            Node source = (Node) actionEvent.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }

    }


    public void unosIndeksa(KeyEvent keyEvent) {
        boolean greskaO = false;
        try {
            Integer indeks = Integer.parseInt(brojIndeksaField.getText());
            if (indeks > 0) brojIndeksaField.setStyle("-fx-background-color: rgba(72,128,85,0.4)");
            greskaO = false;

        } catch (NumberFormatException a) {
            brojIndeksaField.setStyle("-fx-background-color: rgba(255,45,86,0.74)");
            greskaO = true;
        }
        if (!greske.containsKey(brojIndeksaField)) greske.put(brojIndeksaField, false);
        greske.replace(brojIndeksaField, greskaO);

    }


    public void unosMjesta(KeyEvent keyEvent) {
        boolean greskaO = false;
        try {
            if (mjestoBox.getValue().length() > 0) {
                Integer.parseInt(mjestoBox.getValue().substring(mjestoBox.getValue().length() - 1));
                // System.out.println(mjestoBox.getValue().substring(mjestoBox.getValue().length()-1));
                mjestoBox.setStyle("-fx-background-color: rgba(255,45,86,0.74)");
            }
            greskaO = true;
        } catch (NumberFormatException a) {
            // System.out.println("desilo se"+mjestoBox.getValue().length());
            mjestoBox.setStyle("-fx-background-color: rgba(72,128,85,0.4)");
            greskaO = false;
        }
        if (!greske.containsKey(mjestoBox)) greske.put(mjestoBox, false);
        greske.replace(mjestoBox, greskaO);

    }

    private LocalDate izdvoji() {
        int god;
        if(JMBG.getText().charAt(4)=='9') god=Integer.parseInt(JMBG.getText(4, 7))+1000;
        else god=Integer.parseInt(JMBG.getText(4, 7))+2000;
        int a = Integer.parseInt(JMBG.getText(0, 2)), b = Integer.parseInt(JMBG.getText(2, 4));
        return LocalDate.of(god, b, a);
    }

    public void unosJMBG(KeyEvent keyEvent) {
        boolean greskaO = false;

        try {
            if (JMBG.getText().length() >= 7) {
                if (izdvoji().compareTo(LocalDate.now()) >= 1) throw new Exception();
            }
            if (JMBG.getText().length() != 13) throw  new Exception();
            JMBG.setStyle("-fx-background-color: rgba(72,128,85,0.4)");
            greskaO = false;
            System.out.println(datum.getValue()+" "+izdvoji());
            if (greske.containsKey(datum) && datum.getValue().equals(izdvoji())) {
                greske.replace(datum, false);
                datum.setStyle("-fx-background-color: rgba(72,128,85,0.4)");
            } else if (greske.containsKey(datum) && !datum.getValue().equals(izdvoji())) {
                greske.replace(datum, true);
                datum.setStyle("-fx-background-color: rgba(255,45,86,0.74)");
            }
        } catch (Exception e) {
            JMBG.setStyle("-fx-background-color: rgba(255,45,86,0.74)");
            greskaO = true;
        }
        if (!greske.containsKey(JMBG)) greske.put(JMBG, false);
        greske.replace(JMBG, greskaO);

    }


    public void unosAdresa(KeyEvent keyEvent) {
        boolean greskaO = false;

               if(adresaField.getText().length()>0&& !(Character.isLetterOrDigit(adresaField.getText().charAt(adresaField.getText().length() - 1))
                || Character.isWhitespace(adresaField.getText().charAt(adresaField.getText().length() - 1)))) {

            adresaField.setStyle("-fx-background-color: rgba(255,45,86,0.74)");
            greskaO = true;
        } else {
            adresaField.setStyle("-fx-background-color: rgba(72,128,85,0.4)");
            greskaO = false;
        }
        if (!greske.containsKey(adresaField)) greske.put(adresaField, false);
        greske.replace(adresaField, greskaO);

    }
}