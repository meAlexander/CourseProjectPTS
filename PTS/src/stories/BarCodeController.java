package stories;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import application.messages.FXOptionPane;
import insidefx.undecorator.UndecoratorScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BarCodeController {
	private final String code = "ptsProject";
	double positionX;
	double positionY;

	UndecoratorScene scene;
	BorderPane root;

	public UndecoratorScene getBarCodeScene(Stage stage, Scene sceneProgram) {
		BorderPane root;
		try {
			root = (BorderPane) FXMLLoader.load(getClass().getResource("BarCode.fxml"));
			scene = new UndecoratorScene(stage, root);

			scene.getUndecorator().setButtonVisibility(true, true, true);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			VBox box = (VBox) root.getCenter();

			stage.setMinWidth(587);
			stage.setMinHeight(348);
			dropImage(box, stage, sceneProgram);
			return scene;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void dropImage(VBox box, Stage stage, Scene sceneProgram) {
		box.setOnDragOver(e -> {
			Dragboard fileOver = e.getDragboard();
			try {
				if (fileOver.hasFiles()) {
					e.acceptTransferModes(TransferMode.LINK);
				}
			} catch (Exception e2) {
				System.out.println("Drag drom problem");
			}
		});
		box.setOnDragDropped(e -> {
			Dragboard fileDropped = e.getDragboard();

			if (fileDropped.hasFiles()) {
				try {
					String barCode = testBarCodeImage(fileDropped.getFiles().get(0));
					System.out.println(barCode);
					if (barCode == null || !barCode.equals(code)) {
						FXOptionPane.showInformation("Invalid login date", "Unfortunately your data is incorect.");
					} else {
						stage.setScene(null);
						stage.setScene(sceneProgram);
						stage.setMinWidth(993);
						stage.setMinHeight(470);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	public String testBarCodeImage(File file) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(file);
		LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

		try {
			Result result = new MultiFormatReader().decode(bitmap);
			return result.getText();
		} catch (NotFoundException e) {
			System.out.println("There is no QR code in the image");
			return null;
		}
	}
}