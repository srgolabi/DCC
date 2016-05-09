package agtp.utils;

import document.DocumentSystem;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author reza
 * @param <T>
 */
public class UtilsStage<T extends ParentControl> {

    public T t;

    public UtilsStage(String fxml, boolean resizable, String title, Modality modality, Window owner) {
        try {
            FXMLLoader loader = new FXMLLoader(DocumentSystem.class.getResource("fxml/" + fxml));
            Parent page = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setResizable(resizable);
            stage.setTitle(title);
            stage.initModality(modality);
            stage.initOwner(owner);
            stage.sizeToScene();
            stage.getIcons().add(new Image(DocumentSystem.class.getResourceAsStream("resourse/app_icon.png")));
            Scene scene = new Scene(page);
            stage.setScene(scene);
            t = loader.getController();
            t.setStage(stage);
        } catch (IOException ex) {
            Logger.getLogger(UtilsStage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public UtilsStage(Class clazz, String title, Modality modality, Stage owner) {
        this(clazz.getSimpleName().toLowerCase().replaceFirst("fxml_", "") + "/" + clazz.getSimpleName() + ".fxml", false, title, modality, owner.getOwner());
    }

    public UtilsStage(Class clazz) {
        try {
            FXMLLoader loader = new FXMLLoader(DocumentSystem.class.getResource("fxml/" + clazz.getSimpleName().toLowerCase().replaceFirst("fxml_", "") + "/" + clazz.getSimpleName() + ".fxml"));
            Parent page = (Parent) loader.load();
            Stage stage = new Stage();
            stage.sizeToScene();
            Scene scene = new Scene(page);
            stage.setScene(scene);
            t = loader.getController();
            t.setStage(stage);
        } catch (IOException ex) {
            Logger.getLogger(UtilsStage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public UtilsStage(Class clazz, boolean resizable, String title, Modality modality, Window owner) {
        this(clazz.getSimpleName().toLowerCase().replaceFirst("fxml_", "") + "/" + clazz.getSimpleName() + ".fxml", resizable, title, modality, owner);
    }
}
