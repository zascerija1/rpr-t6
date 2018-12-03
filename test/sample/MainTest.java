package sample;

import com.sun.javafx.robot.FXRobot;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.robot.Motion;


@ExtendWith(ApplicationExtension.class)
class MainTest {

    private TextField polje;
    @Start
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Main.class.getResource("sample.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();

    }



    @Test
    void jmbgTestFail (FxRobot robot) {
        polje = robot.lookup("#JMBG").queryAs(TextField.class);
        robot.clickOn("#JMBG");
        robot.write("ahshh3");//neispravan format, treba pocrvenit
        assertEquals("-fx-background-color: rgba(255,45,86,0.74)", polje.getStyle());
    }

    @Test
    void jmbgTestSucc(FxRobot robot) {
        polje = robot.lookup("#JMBG").queryAs(TextField.class);
        robot.clickOn("#JMBG");
        robot.write("0101100710006");//Ispravno
        assertEquals("-fx-background-color: rgba(72,128,85,0.4)", polje.getStyle());
    }

    @Test
    void mjestoTest(FxRobot robot){
        ComboBox place;
        place= robot.lookup("#mjestoBox").queryAs(ComboBox.class);
        robot.clickOn("#mjestoBox");
        robot.eraseText(8);
        robot.write("Zagreb");//dodavanje novog mjesta
        robot.press(KeyCode.ENTER);
        robot.clickOn("#datum");
        assertEquals("Zagreb", place.getValue());

    }

    @Test
    void imeField(FxRobot robot) {

        polje = robot.lookup("#imeField").queryAs(TextField.class);
        robot.clickOn("#imeField").targetWindow();
      robot.write("Freddie3");//Neispravno
        assertEquals("-fx-background-color: rgba(255,45,86,0.74)", polje.getStyle());
    }

    @Test
    void mailField(FxRobot robot) {

        polje = robot.lookup("#mailField").queryAs(TextField.class);
        robot.clickOn("#mailField").targetWindow();
        robot.write("brian_98@etf.unsa.ba");//Ispravno
        assertEquals("-fx-background-color: rgba(72,128,85,0.4)", polje.getStyle());
    }

    @Test
    void mailFieldFail(FxRobot robot) {

        polje = robot.lookup("#mailField").queryAs(TextField.class);
        robot.clickOn("#mailField").targetWindow();
        robot.write("roger939383@gmail");//Neispravno
        assertEquals("-fx-background-color: rgba(255,45,86,0.74)", polje.getStyle());
    }

    @Test
    void telefonFieldFail(FxRobot robot) {

        polje = robot.lookup("#telefonField").queryAs(TextField.class);
        robot.clickOn("#telefonField").targetWindow();
        robot.write("39493933");//Neispravno
        assertEquals("-fx-background-color: rgba(255,45,86,0.74)", polje.getStyle());
    }

    @Test
    void telefonField(FxRobot robot) {

        polje = robot.lookup("#telefonField").queryAs(TextField.class);
        robot.clickOn("#telefonField").targetWindow();
        robot.write("+38761737482");//Ispravno
        assertEquals("-fx-background-color: rgba(72,128,85,0.4)", polje.getStyle());
    }
    @Test
    void godinaField(FxRobot robot) {

        ChoiceBox odsjek = robot.lookup("#godinaBox").queryAs(ChoiceBox.class);

        robot.clickOn("#godinaBox").targetWindow();
        robot.press(KeyCode.DOWN);
        robot.press(KeyCode.ENTER);
        assertEquals("druga", odsjek.getValue());
    }













}