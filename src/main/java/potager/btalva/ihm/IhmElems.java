package potager.btalva.ihm;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ilog.concert.IloException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import potager.btalva.calc.ContraintesModMath;
import potager.btalva.calc.ModMath;
import potager.btalva.calc.MotherModMath;
import potager.btalva.mng.DataPotager;
import potager.btalva.mng.ParamsPotager;
import potager.btalva.mng.RootMng;
import potager.btalva.results.EcritureFichierResultats;
import potager.btalva.results.RecuperationResultats;
import potager.btalva.utils.UtilsLogs;


@SuppressWarnings("restriction")
public class IhmElems {

	private static final Logger LOGGER = LoggerFactory.getLogger(IhmElems.class);
	
	private static final String ERROR_TITLE = "Erreur!";
	
	public enum FilterModeExcel {

		XLSX_FILES("xlsx files (*.xlsx)", "*.xlsx");
		


		private ExtensionFilter extensionFilter;

		FilterModeExcel(String extensionDisplayName, String... extensions){
			extensionFilter = new ExtensionFilter(extensionDisplayName, extensions);
		}

		public ExtensionFilter getExtensionFilter(){
			return extensionFilter;
		}
	}

	private Scene laScene = null;
	private Stage leStage = null;
	
	private Font mainFont = null;
	
	private TextField mnTxtFieldNbParts = null;

	


	
	private TextArea mnTraceTxtArea;
	


	private Button mnBtnLancerCalcul;
	private BorderPane mnBorderPane;
	private MenuBar mnMenuBar;
	private GridPane mnGridPane;
	private BorderPane mnCalculBorderPane;
	
	private MenuItem mnDataMenuItem;

	private String mnCurrLogFile;


	public IhmElems(Stage stagggg, Scene sceeeeene) {
		super();
		this.leStage = stagggg;
		this.laScene = sceeeeene;
		this.mnCurrLogFile = UtilsLogs.renvCurrentLogFile();
		
	}
	
	
	
	public TextField getMnTxtFieldNbParts() {
		return mnTxtFieldNbParts;
	}



	public void setMnTxtFieldNbParts(TextField mnTxtFieldNbParts) {
		this.mnTxtFieldNbParts = mnTxtFieldNbParts;
	}



	private void ecrDansTraceChoixFichierParametrage(File file, String info) {
		if (file != null ) {


			this.mnTraceTxtArea.appendText(info);
			this.mnTraceTxtArea.appendText(file.getAbsolutePath());
			this.mnTraceTxtArea.appendText(System.lineSeparator());
		}


	}
	
	private void ecrDansTrace(String str) {
		this.mnTraceTxtArea.appendText(str);
		this.mnTraceTxtArea.appendText(System.lineSeparator());
	}
	
	


	private void buildMenubar() {


		// Create menus
		Menu actionsMenu = new Menu("Actions");
		


		MenuItem effaceTraceItem = new MenuItem("Effacer la trace");
		effaceTraceItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				mnTraceTxtArea.clear();
				

			}
		});
		MenuItem exitItem = new MenuItem("Quitter");
		exitItem.setOnAction(actionEvent -> this.leStage.close());
		
		Menu paramMenu = new Menu("Paramètres");
		MenuItem paramMenuItem = new MenuItem("Lire le fichier de paramétrage");
		

		paramMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				File file = null;
				try {
					String paramRep = RootMng.getInstance().getRootDirApp()+"/param";
					File paramDir = new File(paramRep);
					boolean isGoodParamDir = false;
					if (paramDir.exists() == true) {
						if (paramDir.isDirectory() == true) {
							isGoodParamDir = true;
						}
					}
					if (isGoodParamDir == false ) {
						Alert infoErrDirParamDlg = new Alert(AlertType.ERROR);
						infoErrDirParamDlg.setTitle(ERROR_TITLE);
						infoErrDirParamDlg.setHeaderText("Répertoire de paramétrage inexistant:"+ paramRep);
						infoErrDirParamDlg.setContentText("Ayez la bonté de le créer et d'y mettre au moins un fichier de paramétrage.");
						infoErrDirParamDlg.showAndWait();
						return;
					}
					FileChooser fileChooserParamFile = new FileChooser();
					fileChooserParamFile.getExtensionFilters().setAll(
							Stream.of(FilterModeExcel.XLSX_FILES)
							.map(FilterModeExcel::getExtensionFilter)
							.collect(Collectors.toList()));
					fileChooserParamFile.setInitialDirectory(paramDir);
					file = fileChooserParamFile.showOpenDialog(leStage);
					if (file != null) {
						mnTraceTxtArea.clear();
						ecrDansTraceChoixFichierParametrage(file, "FICHIER DE PARAMETRAGE: ");
					
						ParamsPotager.newInstance();
						boolean ficParamCorrect = ParamsPotager.getInstance().lireFichierParametrage(file.getAbsolutePath());
						if (ficParamCorrect == false) {
							Alert infoErrFicParamDlg = new Alert(AlertType.ERROR);
							infoErrFicParamDlg.setTitle(ERROR_TITLE);
							infoErrFicParamDlg.setHeaderText("Fichier de paramétrage non conforme: "+ file.getAbsolutePath());
							
							infoErrFicParamDlg.setContentText("Voir la cause dans le fichier log de l'application "+
									mnCurrLogFile);
							infoErrFicParamDlg.showAndWait();
							return;
						}
					
						mnTraceTxtArea.appendText(ParamsPotager.getInstance().getAssocsPlantes().toStringSpecial());
						mnTraceTxtArea.appendText(ParamsPotager.getInstance().getBacsParams().toStringSpecial());
						mnTraceTxtArea.appendText(System.lineSeparator());
						mnTraceTxtArea.appendText(System.lineSeparator()+"Plus de détails dans: " + mnCurrLogFile);
						mnDataMenuItem.setDisable(false);
						mnBtnLancerCalcul.setDisable(true);
				
					}
						
				} catch (Exception e) {
					
						LOGGER.error(e.toString());
						
						Alert infoErrFicParamDlg = new Alert(AlertType.ERROR);
						infoErrFicParamDlg.setTitle(ERROR_TITLE);
						infoErrFicParamDlg.setHeaderText("Fichier de paramétrage non conforme: "+ file.getAbsolutePath());
						infoErrFicParamDlg.setContentText(e.toString());
						infoErrFicParamDlg.showAndWait();
					
				}
				

			}
		});

		Menu dataMenu = new Menu("Données");
		mnDataMenuItem = new MenuItem("Lire le fichier de données d'entrée (plantes à semer)");
		mnDataMenuItem.setDisable(true);

		mnDataMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				File file = null;
				try {
					String dataRep = RootMng.getInstance().getRootDirApp()+"/data";
					File dataDir = new File(dataRep);
					boolean isGoodDataDir = false;
					if (dataDir.exists() == true) {
						if (dataDir.isDirectory() == true) {
							isGoodDataDir = true;
						}
					}
					if (isGoodDataDir == false ) {
						Alert infoErrDirDataDlg = new Alert(AlertType.ERROR);
						infoErrDirDataDlg.setTitle(ERROR_TITLE);
						infoErrDirDataDlg.setHeaderText("Répertoire de données inexistant:"+ dataRep);
						infoErrDirDataDlg.setContentText("Ayez la bonté de le créer et d'y mettre au moins un fichier de données.");
						infoErrDirDataDlg.showAndWait();
						return;
					}
					FileChooser fileChooserDataFile = new FileChooser();
					fileChooserDataFile.getExtensionFilters().setAll(
							Stream.of(FilterModeExcel.XLSX_FILES)
							.map(FilterModeExcel::getExtensionFilter)
							.collect(Collectors.toList()));
					fileChooserDataFile.setInitialDirectory(dataDir);
					file = fileChooserDataFile.showOpenDialog(leStage);
					if (file != null) {
						mnTraceTxtArea.clear();
						ecrDansTraceChoixFichierParametrage(file, "FICHIER DE DONNEES: ");
					
						DataPotager.newInstance();
						boolean ficDataCorrect = DataPotager.getInstance().lireFichierData(file.getAbsolutePath());
						if (ficDataCorrect == false) {
							Alert infoErrFicDataDlg = new Alert(AlertType.ERROR);
							infoErrFicDataDlg.setTitle(ERROR_TITLE);
							infoErrFicDataDlg.setHeaderText("Fichier de données non conforme: "+ file.getAbsolutePath());
							infoErrFicDataDlg.setContentText("Voir la cause dans le fichier log de l'application "+
									mnCurrLogFile);
							infoErrFicDataDlg.showAndWait();
							return;
						}
					
						mnTraceTxtArea.appendText(DataPotager.getInstance().getInputDataPotagerBernard().toStringSpecial());
					
						mnTraceTxtArea.appendText(System.lineSeparator());
						mnTraceTxtArea.appendText(System.lineSeparator()+"Plus de détails dans: " + mnCurrLogFile);
						mnBtnLancerCalcul.setDisable(false);
				
					}
						
				} catch (Exception e) {
					
						LOGGER.error(e.toString());						
						Alert infoErrFicDataDlg = new Alert(AlertType.ERROR);
						infoErrFicDataDlg.setTitle(ERROR_TITLE);
						infoErrFicDataDlg.setHeaderText("Fichier de données d'entrée non conforme: "+ file.getAbsolutePath());
						infoErrFicDataDlg.setContentText(e.toString());
						infoErrFicDataDlg.showAndWait();
					
				}
				

			}
		});
		Menu helpMenu = new Menu("?");
		MenuItem aboutItem = new MenuItem("A propos du logiciel");

		aboutItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle(PotagerIhm.NOM_LOGICIEL);
				alert.setHeaderText("Version: "+ PotagerIhm.VERSION);
				alert.setContentText("Date: "+ PotagerIhm.DATE_VERSION);

				alert.showAndWait();

			}
		});










		// Add menuItems to the Menus

		actionsMenu.getItems().addAll( effaceTraceItem, exitItem);
		paramMenu.getItems().addAll(paramMenuItem);
		dataMenu.getItems().addAll(mnDataMenuItem);
		helpMenu.getItems().addAll(aboutItem);
		
		
		

		// Create MenuBar
		this.mnMenuBar = new MenuBar();
		
		// Add Menus to the MenuBar
		this.mnMenuBar.getMenus().addAll(actionsMenu, paramMenu, dataMenu, helpMenu);
		this.mnMenuBar.prefWidthProperty().bind(this.leStage.widthProperty());
		
	}
	

	

	
	
	

	private void buildGridpane() {

		GridPane root = new GridPane();

		

		root.setPadding(new Insets(20));
		root.setHgap(25);
		root.setVgap(15);
		
		

		
				

		BorderPane lBorderPaneTrace = new  BorderPane();
		lBorderPaneTrace.setPadding(new Insets(10, 10, 10, 10));
		

		Label labelTrace = new Label("TRACE ET RESULTATS");
		labelTrace.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.ITALIC, 18));


		lBorderPaneTrace.setTop(labelTrace);
		BorderPane.setMargin(labelTrace, new Insets(10, 10, 20, 10));
		BorderPane.setAlignment(labelTrace, Pos.CENTER);


		// TextArea
		TextArea textArea = new TextArea();
		textArea.setText("");

		String style=
				"-fx-text-fill: blue;"+
						"-fx-background-color: black;"+
						"-fx-font-size: 18;" +
						"-fx-font-weight: " + "bold;";  
		textArea.setStyle(style);   
		textArea.setPrefHeight((double)850);
		textArea.setPrefWidth((double)1200);
		textArea.setEditable(false);
		this.mnTraceTxtArea = textArea;

		lBorderPaneTrace.setCenter(textArea);

		root.add(lBorderPaneTrace,0, 0,1,1);
		
		root.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
	   
		this.mnGridPane = root;

	}

	private void buildBorderpane() {

		this.mnBorderPane = new BorderPane();
		this.mnBorderPane.setTop(this.mnMenuBar);
		this.mnBorderPane.setCenter(this.mnGridPane);
		this.mnBorderPane.setBottom(this.mnCalculBorderPane);
		this.mnBorderPane.prefHeightProperty().bind(this.laScene.heightProperty());
		this.mnBorderPane.prefWidthProperty().bind(this.laScene.widthProperty());
	
	}
	

	
	
	
	private void buildCalculBorderPane() {
		BorderPane bpn = new BorderPane();

		bpn.setPadding(new Insets(10, 10, 10, 10));

		mnBtnLancerCalcul= new Button("Lancer le calcul");
		mnBtnLancerCalcul.setFont(mainFont);
		mnBtnLancerCalcul.setDisable(true);
		
		
		mnBtnLancerCalcul.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mnTraceTxtArea.clear();
				ModMath lModMath = new ModMath();
				
				
				try {
					
					// création de l'instance CPLEX
					lModMath.creerInstancePbCplex();
				
					// CREATION DES VARIABLES
					lModMath.creerVarsCarresOccupesParPlantes();
					ecrDansTrace("Nombre de variables booléennes d'affection des plantes dans les carrés des bacs créées: "+
							lModMath.getListeCepVars().size());

					lModMath.creerVarsIndicsNbAssocRecommandeesBacs();
					ecrDansTrace("Nombre de variables entières comptant les indicateurs de couples d'associations recommandées dans les bacs créées: "+
							lModMath.getListeIndicNarbVars().size());
					
					
					// CREATION DES CONTRAINTES
					ContraintesModMath lContraintesModMath = new ContraintesModMath(lModMath);
					lContraintesModMath.creerContraintesModMathComplet();
					
					ecrDansTrace("Nb de contraintes définissant le tableau IndicNbAssocRecommandeesBac créées: "+
							lContraintesModMath.getNbContraintesDefTableauIndicNbAssocRecommandeesBac());
					
					
					ecrDansTrace("Nb de contraintes d'inégalité de type pas plus de "+
							ParamsPotager.getInstance().getBacsParams().getNbMaxCarresParBac()
							+" plantes par bac créées: " + 
							lContraintesModMath.getNbContraintesPasPlusDeNbMaxCarresParBacPlantesParBac());
					
					
					
					ecrDansTrace("Nb de contraintes d'égalité créées disant que toutes les plantes doivent être semées: "+
							lContraintesModMath.getNbContraintesToutesLesPlantesDoiventEtreSemeesEtUnePlanteVaDansUnSeulBac());
					
					
					
					ecrDansTrace("Nb de contraintes créées disant que toutes les plantes d'un même bac doivent être compatibles: "+
							lContraintesModMath.getNbContraintesPlantesDansUnBacDoiventEtreCompatibles());
				
					ecrDansTrace("Début du calcul...");
					lModMath.creerFonctionObjectifMaximizeIndicNbAssocsPlantesRecommandees();
					boolean faisable = lModMath.resoudrePlnePb(MotherModMath.TIME_5_MINS, MotherModMath.INTEGRALITY_TOLERANCE_STRICT);
					if (faisable == false) {
						ecrDansTrace("Fin du calcul: pas de solution!");
					} else {
						ecrDansTrace("Fin du calcul: il existe au moins une solution!");
						RecuperationResultats lRecuperationResultats = new RecuperationResultats(lModMath);
						lRecuperationResultats.renseignerResultatsPourTousLesBacs();
						lRecuperationResultats.ecrireResultatsPourTousLesBacs();
						String wbn = EcritureFichierResultats.ecrireFichierResultats(lRecuperationResultats);
						afficherDlgSuccesCalc(wbn);
						
					}
					mnTraceTxtArea.appendText(System.lineSeparator()+"Plus de détails dans: " + mnCurrLogFile);
					
					
				} catch (IloException | IOException e) {
					
					e.printStackTrace();
					String fatalErr = e.toString();
					LOGGER.error(fatalErr);
					afficherDlgEchecCalc(fatalErr);
				}			

				// destruction de l'instance CPLEX
				lModMath.detruireInstancePbCplex();

			}
		});
		
		
		FlowPane fpnber = new FlowPane();
		fpnber.setPadding(new Insets(5, 5, 5, 5));
		fpnber.setHgap(5); //inutile car un seul élément , mais bon...
		fpnber.getChildren().add(mnBtnLancerCalcul);

		bpn.setLeft(fpnber);
		// Set margin for left area.
		BorderPane.setMargin(fpnber, new Insets(10, 10, 10, 10));
		BorderPane.setAlignment(fpnber, Pos.BOTTOM_LEFT);



		this.mnCalculBorderPane = bpn;

	}


	private void afficherDlgSuccesCalc(String resFile) {
		ButtonType affRepResBtnTyp = new ButtonType("Afficher le répertoire des résultats EXCEL", ButtonData.OK_DONE);
		ButtonType fermeDlgBtnTyp = new ButtonType("Fermer la fenêtre", ButtonData.CANCEL_CLOSE);
		
		Alert infoResSuccesDlg = new Alert(AlertType.INFORMATION);
		infoResSuccesDlg.getButtonTypes().clear();

		infoResSuccesDlg.getButtonTypes().addAll(fermeDlgBtnTyp, affRepResBtnTyp);

		infoResSuccesDlg.setTitle("Calcul terminé avec succès! ");
		infoResSuccesDlg.setHeaderText("Veillez ouvrir le répertoire des résultats: les fichiers sont horodatés.");
		infoResSuccesDlg.setContentText("Fichier résultat: "+ resFile +
				System.lineSeparator()+"Plus de détails dans: " + mnCurrLogFile);
		infoResSuccesDlg.setResizable(true);
		infoResSuccesDlg.getDialogPane().setPrefSize(700, 300);

		Optional<ButtonType> result = infoResSuccesDlg.showAndWait();
		if (result.orElse(fermeDlgBtnTyp) == affRepResBtnTyp) {
			File file = new File(RootMng.getInstance().getRootDirApp()+"/resultats");
			try {
				Desktop.getDesktop().open(file);
			} catch (IOException e) {
				String err = e.toString();
				LOGGER.error("Exception levée: {}", err);
				
				afficherDlgEchecCalc(err);
			}

		}

	}
	
	
	private void afficherDlgEchecCalc(String errDetails) {
		mnTraceTxtArea.appendText("Calcul de chargement en échec!\n");
		mnTraceTxtArea.appendText("Exception levée: "+ errDetails+"\n");
		LOGGER.error("Exception levée: {}", errDetails);
		Alert infoResEchecDlg = new Alert(AlertType.ERROR);
		infoResEchecDlg.setTitle("Calcul en échec!");
		infoResEchecDlg.setHeaderText("Un erreur s'est produite. Elle a été enregistrée.");
		infoResEchecDlg.setContentText(errDetails + System.lineSeparator()+"Plus de détails dans: " + mnCurrLogFile);
		infoResEchecDlg.setResizable(true);
		infoResEchecDlg.getDialogPane().setPrefSize(700, 300);
		infoResEchecDlg.showAndWait();

	}




	public void initialize() {
		
		this.mainFont = Font.font("Arial", FontWeight.BOLD, 14);
		buildMenubar();
		buildGridpane();
		buildCalculBorderPane();
		buildBorderpane();


	}



	public BorderPane getMnBorderPane() {
		return mnBorderPane;
	}
	public void setMnBorderPane(BorderPane mnBorderPane) {
		this.mnBorderPane = mnBorderPane;
	}
	public MenuBar getMnMenuBar() {
		return mnMenuBar;
	}
	public void setMnMenuBar(MenuBar mnMenuBar) {
		this.mnMenuBar = mnMenuBar;
	}
	public GridPane getMnGridPane() {
		return mnGridPane;
	}
	public void setMnGridPane(GridPane mnGridPane) {
		this.mnGridPane = mnGridPane;
	}


	public Scene getLaScene() {
		return laScene;
	}


	public void setLaScene(Scene laScene) {
		this.laScene = laScene;
	}



}
