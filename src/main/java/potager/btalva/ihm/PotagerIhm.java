package potager.btalva.ihm;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import potager.btalva.mng.RootMng;
import potager.btalva.utils.RootDirApp;

@SuppressWarnings("restriction")
public class PotagerIhm extends Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(PotagerIhm.class);

	public static final String VERSION = "v1.0.5";
	public static final String DATE_VERSION = "13/04/2024";
	public static final String NOM_LOGICIEL = "Potager de Lou Naffrou du bon côté";
	public static final double LARGEUR_FENETRE_APPLI_PAR_DEFAUT = 1300.0;
	public static final double HAUTEUR_FENETRE_APPLI_PAR_DEFAUT = 1000.0;
	
	private static final String TITRE_FENETRE_APPLI = NOM_LOGICIEL + " " + VERSION;

	
	@Override
	public void start(Stage stage) {


		RootMng.newInstance();

		Properties props = new Properties();
		try {
			props.load(PotagerIhm.class.getResource("params.properties").openStream()); //$NON-NLS-1$
		} catch (IOException e) {
			LOGGER.error("Unable to read params.properties resource file {}", e.toString()); //$NON-NLS-1$

			throw new RuntimeException(e);
		}

		RootMng.getInstance().setRootDirApp(RootDirApp.calcRootDirApp(props));

		

		Group rootGrp = new Group();
		Scene scene = new Scene(rootGrp, LARGEUR_FENETRE_APPLI_PAR_DEFAUT, HAUTEUR_FENETRE_APPLI_PAR_DEFAUT, Color.LIGHTGOLDENRODYELLOW);
		IhmElems lIhmElems = new IhmElems(stage, scene);
		lIhmElems.initialize();
		rootGrp.getChildren().add(lIhmElems.getMnBorderPane());
		


		stage.setOnCloseRequest((event) -> {
			Platform.exit();
		});
		stage.setTitle(TITRE_FENETRE_APPLI);
		stage.setScene(scene);

		stage.setResizable(false);
		stage.show();


	}

	public static void main(String[] args) {

		Application.launch(args);
	}

}