package hr.tpopovic.huntforblackbeard.adapter.in;

import hr.tpopovic.huntforblackbeard.IocContainer;
import hr.tpopovic.huntforblackbeard.application.domain.model.Location;
import hr.tpopovic.huntforblackbeard.application.domain.model.Piece;
import hr.tpopovic.huntforblackbeard.application.port.in.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class GameController {

    @FXML
    AnchorPane gamePane;
    @FXML
    ComboBox<String> selectedPieceComboBox;
    @FXML
    ImageView jane;
    @FXML
    ImageView ranger;
    @FXML
    ImageView brand;
    @FXML
    ImageView adventure;
    @FXML
    Text numberOfMovesText;
    @FXML
    Button finishTurnButton;
    @FXML
    Button searchForPiratesButton;

    private FXLocations locations;
    private FXPieces pieces;
    private FXPiece currentlySelectedPiece;
    private NumberOfMovesHandler numberOfMovesHandler;
    private MovementFetcher movementFetcher;
    private final ForMovingPieces forMovingPieces = IocContainer.getInstance(ForMovingPieces.class);
    private final ForFinishingTurn forFinishingTurn = IocContainer.getInstance(ForFinishingTurn.class);
    private final ForDiscoveringPirateSightings forDiscoveringPirateSightings = IocContainer.getInstance(ForDiscoveringPirateSightings.class);
    private final DocumentationGenerator documentationGenerator = IocContainer.getInstance(DocumentationGenerator.class);

    @FXML
    public void initialize() {
        InitializationResult result = new GameInitializer(
                gamePane,
                jane,
                ranger,
                brand,
                adventure,
                selectedPieceComboBox,
                finishTurnButton,
                searchForPiratesButton,
                numberOfMovesText
        ).initialize();
        this.locations = result.locations();
        this.pieces = result.pieces();
        this.currentlySelectedPiece = result.currentlySelectedPiece();
        this.numberOfMovesHandler = result.numberOfMovesHandler();
        this.movementFetcher = result.movementFetcher();
    }

    @FXML
    void onPieceSelected() {
        currentlySelectedPiece = pieces.findByName(selectedPieceComboBox.getValue());
        movementFetcher.updateMapWithAvailablePositionsForCurrentlySelectedPiece(currentlySelectedPiece);
        searchForPiratesButton.setDisable(currentlySelectedPiece.hasStartedSearching());
    }

    @FXML
    void onMovementButtonPressed(ActionEvent event) {
        Piece.Name currentlySelectedPieceName = Piece.Name.findByName(selectedPieceComboBox.getValue());
        FXLocation fxLocation = locations.findByButton(event.getSource());
        Location.Name location = Location.Name.findById(fxLocation.id());
        if (currentlySelectedPiece.hasStartedSearching()) {
            searchForPiratesButton.setDisable(true);
            PirateSightingCommand command = new PirateSightingCommand(currentlySelectedPieceName, location);
            PirateSightingResult result = forDiscoveringPirateSightings.discover(command);
            handlePirateSightingResult(result);
        } else {
            searchForPiratesButton.setDisable(false);
            MovementCommand movementCommand = new MovementCommand(currentlySelectedPieceName, location);
            MovementResult result = forMovingPieces.move(movementCommand);
            switch (result) {
                case MovementResult.Success success -> movementSuccess(success, fxLocation);
                case MovementResult.Failure failure -> movementFailure(failure);
            }
        }
    }

    @FXML
    void onFinishTurnButtonPressed() {
        CompletableFuture<TurnFinishResult> result = forFinishingTurn.finishTurn();
        FinishTurnHandler.finish(result);
        disableButtons();
    }

    @FXML
    void onSearchForPiratesButtonPressed() {
        searchForPiratesButton.setDisable(true);
        currentlySelectedPiece.setStartedSearching(true);
        Piece.Name pieceName = Piece.Name.findByName(currentlySelectedPiece.name());
        PirateSightingStartCommand command = new PirateSightingStartCommand(pieceName);
        PirateSightingResult result = forDiscoveringPirateSightings.startDiscovery(command);
        handlePirateSightingResult(result);
    }

    @FXML
    void openChat() {
        ChatOpener.openChatWindow();
    }

    @FXML
    void openReplay() {
        ReplayOpener.openReplayWindow();
    }

    @FXML
    void generateDocumentation() {
        documentationGenerator.generate(gamePane.getScene().getWindow());
    }

    private void handlePirateSightingResult(PirateSightingResult result) {
        switch (result) {
            case PirateSightingResult.Found _ -> searchForPiratesFound();
            case PirateSightingResult.Sighted sighted -> searchForPiratesSighted(sighted);
            case PirateSightingResult.NotSighted _ -> searchForPiratesNotSighted();
            case PirateSightingResult.Failure failure -> searchForPiratesFailure(failure);
        }
    }

    private void searchForPiratesNotSighted() {
        locations.forEach(location -> location.button().setVisible(false));
        AlertManager.showInfo("Pirate Sighting", "No pirates sighted at this location.");
    }

    private void searchForPiratesSighted(PirateSightingResult.Sighted sighted) {
        locations.findById(sighted.getLocation().id).pirateSightingImageView().setVisible(true);
        Map<Boolean, Set<FXLocation>> movementAvailableToLocations = locations.partitionByContains(sighted.getAvailableDestinations());
        movementAvailableToLocations.getOrDefault(true, Set.of())
                .forEach(location -> location.button().setVisible(true));
        movementAvailableToLocations.getOrDefault(false, Set.of())
                .forEach(location -> location.button().setVisible(false));
    }

    private void searchForPiratesFound() {
        pieces.getAdventure().imageView().setVisible(true);
        locations.forEach(location -> location.button().setVisible(false));
    }

    private void searchForPiratesFailure(PirateSightingResult.Failure failure) {
        AlertManager.showInfo("Pirate Sighting Failure", failure.getMessage());
    }

    private void disableButtons() {
        locations.forEach(location -> location.button().setDisable(true));
        selectedPieceComboBox.setDisable(true);
        finishTurnButton.setDisable(true);
        searchForPiratesButton.setDisable(true);
    }

    private void movementSuccess(MovementResult.Success success, FXLocation fxLocation) {
        movementFetcher.updateMapWithAvailablePositionsForCurrentlySelectedPiece(currentlySelectedPiece);
        currentlySelectedPiece.changeLocation(fxLocation);
        numberOfMovesHandler.setNumberOfMoves(success.getNumberOfMoves());
    }

    private void movementFailure(MovementResult.Failure failure) {
        AlertManager.showInfo("Movement Error", failure.getMessage());
    }

}
