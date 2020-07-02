/* This is the controller for the NIMGAME. This program is a game that replicates
 * the original rules to the NIM math game.  Uses 'coins' to visually keep track
 * of what moves can be made.  Creates a very simplistic AI that takes turns after
 * the user.  creates a gui for the user to use to play the game.
 * created by ethan infelice
 */
package nim.game;

import java.awt.Color;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;

public class FXMLNimController implements Initializable {
    
    @FXML private Label label; 
    @FXML private Button newGameBtn;    // new game button
    @FXML private TextField removeCoins;    // user textfield for removing coins
    @FXML private TextField fromRow;        // user textfield for which row
    @FXML private Button playersTurn;       // players turn button
    @FXML private TextArea playerError;     // players error text field
    @FXML private TextArea playerTurnText;  // players turn output textarea
    @FXML private TextArea computersTurnText;   // computers turn output textarea
    @FXML private TextArea whoWon;          // winner condition textareas
    @FXML private Circle coin11;            // coin row1 coin1
    @FXML private Circle coin12;            // coin row1 coin2
    @FXML private Circle coin21;            // coin row2 coin1
    @FXML private Circle coin22;            // coin row2 coin2
    @FXML private Circle coin23;            // coin row2 coin3
    @FXML private Circle coin31;            // coin row3 coin1
    @FXML private Circle coin32;            // coin row3 coin2
    @FXML private Circle coin33;            // coin row3 coin3
    @FXML private Circle coin34;            // coin row3 coin4
    @FXML private Circle coin41;            // coin row4 coin1
    @FXML private Circle coin42;            // coin row4 coin2
    @FXML private Circle coin43;            // coin row4 coin3
    @FXML private Circle coin44;            // coin row4 coin4
    @FXML private Circle coin45;            // coin row4 coin5
    
    Game theGame = new Game();      // creates new instance game class
    
    String notAMove = "ENTER VALID MOVE";       // string for user not entering valid move 
    String gameOver = "GAMEOVER PRESS 'NEW GAME'";  // string for game over press new game
    String playerWon = "PLAYER \n WON \n GAME!";    // string for when player wins game
    String computerWon = "COMPUTER \n WON \n GAME!";    // string for when computer wins game    
    
    Circle[] circleArray = new Circle[46];  // creates an array for the circle coin objects
    
    public void circleArray(){
        // sets the coin objects to their respective position in the circle array
        
        circleArray[11] = coin11;
        circleArray[12] = coin12;
        circleArray[21] = coin21;
        circleArray[22] = coin22;
        circleArray[23] = coin23;
        circleArray[31] = coin31;
        circleArray[32] = coin32;
        circleArray[33] = coin33;
        circleArray[34] = coin34;
        circleArray[41] = coin41;
        circleArray[42] = coin42;
        circleArray[43] = coin43;
        circleArray[44] = coin44;
        circleArray[45] = coin45;
        
    } // end circleArracy method
/**************************************************************/  
    
    public void newGameAction(ActionEvent event) {
        /* action event for when the new game button is pressed
         * resets all textboxs and makes all the coins reappear
         * and resets the game class
         */
        
        // resets game class
        theGame = new Game(); 
        
        // resets all textboxes
        removeCoins.clear();
        fromRow.clear();
        playerError.clear();
        playerTurnText.clear();
        computersTurnText.clear();
        whoWon.clear();
        
        // makes all coins reappear
        coin11.setVisible(true);
        coin12.setVisible(true);
        coin21.setVisible(true);
        coin22.setVisible(true);
        coin23.setVisible(true);
        coin31.setVisible(true);
        coin32.setVisible(true);
        coin33.setVisible(true);
        coin34.setVisible(true);
        coin41.setVisible(true);
        coin42.setVisible(true);
        coin43.setVisible(true);
        coin44.setVisible(true);
        coin45.setVisible(true);
    
    } // end newGameAction 
/**************************************************************/    
     
    public void playerTurnAction (ActionEvent event){
        /* takes the player input for remove coins and rows and then takes
         * players turn and then takes the computers turn if the player does
         * not win.
         */
        
        // remove player error text
        playerError.setText(null); 
        
        // set the style of the who won and player error's textboxes
        whoWon.setStyle("-fx-text-fill: blue");
        playerError.setStyle("-fx-text-fill: red");
        
        // check to see if someone already won the game and continue to take
        // the turn if no one won otherwise output error someone already won
        // the game
        if(!theGame.getGameOver()){
            
            // run method to see if the player made a valid move with coins and row
            // they want to remove from
            if(canTheyTakeCoins(theGame.getCoinRowAmounts() , removeCoins.getText(), 
                    fromRow.getText())){
                
                // adds ++ to the turn counter
                theGame.setTurnNum();
                
                // sets and adds to the user's move output and then sets the text
                // to the playerTurnText Textarea
                theGame.setUserText();
                playerTurnText.setText(theGame.getUserText());
                
                // removes the coins from the playing board
                visuallyRemoveCoins();
                
                // removes the coins from the array holdinf the number of
                // coins in each row
                removeCoinsFromRows();
                
                // if all rows contain no coins the user has won the game
                // otherwise continue to computer's turn
                if(theGame.getCoinRowAmounts(0) == 0 && theGame.getCoinRowAmounts(1) 
                        == 0 && theGame.getCoinRowAmounts(2) == 0 && theGame.getCoinRowAmounts(3)
                        == 0){
                    
                    // set the whowon textarea to player won
                    whoWon.setText(playerWon);
                    
                    // set the game over boolean to true signalling
                    // that the game is over
                    theGame.setGameOver(true);
                    
                    // clear remove coins textbox 
                    removeCoins.clear();
                    
                    // clear from row textbox
                    fromRow.clear();
                    
                } else {
                    
                    // take computer's turn
                    computersTurn();
                    
                } // end else/if 
            } // end if
        } else{
            
            // tell the user they have to restart game to continue
            playerError.setText(gameOver);
            
        } // end if/else
    } // end playerTurnAction
/**************************************************************/
    
    public void computersTurn(){
        /* this is the computer's AI and them taking a turn
         * using the random class the computer picks a random number between
         * 1 and 3 and 1 and 4 and sees if it can make a valid move otherwise
         * randomly picks numbers again.
         */
        
        // randomly picking number of coins and which row to choose from
        theGame.setTakeCoins(ranInt(3));
        theGame.setComputerRow(ranInt(4));
        
        // if the computer can not take coins with the random numbers picked try
        // again
        while(!canTheyTakeCoins(theGame.getCoinRowAmounts() , theGame.getTakeCoins(), 
                theGame.getComputerRow())){
            
            // randomly picking numbers to choose from
            theGame.setTakeCoins(ranInt(3));
            theGame.setComputerRow(ranInt(4));
        } // end while
        
            // set computer's move and then display it in the computersturntext
            // textarea
            theGame.setComputerText();
            computersTurnText.setText(theGame.getComputerText());
            
            // remove the coins from the board
            visuallyRemoveCoins();
            
            // remove coins from the array keeping track of the amount of coins
            removeCoinsFromRows();

            // if all rows dont have an coins in them the computer won
            // otherwise continue to next turn
            if(theGame.getCoinRowAmounts(0) == 0 && theGame.getCoinRowAmounts(1) 
                    == 0 && theGame.getCoinRowAmounts(2) == 0 && theGame.getCoinRowAmounts(3)
                    == 0){

                // set the whowon text to the computer winning
                whoWon.setText(computerWon);
                
                // set game over to true
                theGame.setGameOver(true);

            } // end if
    } // end computerTurn
/**************************************************************/
    
    public int ranInt(int i){
        // creates random class and picks a random number between 1 and
        // number given to method
        
        // create random class
        Random random = new Random();
        
        // return random number
        return (random.nextInt(i) + 1);
    } // end ranInt
/**************************************************************/
    
    public void removeCoinsFromRows(){
        // removes coins sets them to the coin array
        
        // takes in userrow and then subtracts the coin amount from the entered
        // amount of take coins
        theGame.setCoinRowAmounts(theGame.getUserRow()-1, 
                theGame.getCoinRowAmounts(theGame.getUserRow()-1)
                -theGame.getTakeCoins());
    } // end removeCoinsFromRows
/**************************************************************/
    
    public void visuallyRemoveCoins(){
        // hides coins from the board
        
        // creates the circleArray
        circleArray();
        
        int lastCoin = theGame.getCoinRowAmounts(theGame.getUserRow()-1);   // finds how many coins are in a row
        int removeCoin = theGame.getTakeCoins();        // stores how many coins need to be removed
        int rowTimesTen = theGame.getUserRow() * 10;    // multiples the user row by ten
        int pickCoin = rowTimesTen + lastCoin;          // add the rowtimesten to the last coin to find the last coin 
        
        // used for test output
        System.out.println(lastCoin);
        System.out.println(removeCoin);
        System.out.println(rowTimesTen);
        System.out.println(pickCoin);
        
        // removes the coins from the array
        for(int i = removeCoin; i > 0; i--){
            
            // hides coin object
            circleArray[pickCoin].setVisible(false);
            
            // subtract a coin
            pickCoin--;
            
            // used for test output
            System.out.println(pickCoin);
        } // end for loop
    } // end visuallyRemoveCoins
/**************************************************************/    
    
    public boolean canTheyTakeCoins(int[] a, String coinUserInput, String rowUserInput){
        /* this method checks if the players input is a valid move 
         */
        
        int takeCoins;  // sets coins to take
        int userRow;    // sets the user row 
        
        try{
            
            // sets coins and row to user's string as an int
            takeCoins = Integer.parseInt(coinUserInput);
            userRow = Integer.parseInt(rowUserInput);
            
            // used for testing 
            System.out.println(takeCoins);
            System.out.println(userRow);
            
        } catch(NumberFormatException e){
            
            // tells the user they have not made a valid move
            playerError.setText(notAMove);
            
            // ends method
            return false;
            
        } // end try/catch
        
        // if the user entered a valid move that listens to the rules of the game
        if (userRow > 0 && userRow < 5 && takeCoins > 0 && takeCoins < 4){
            
            // check if they're enough coins in the row to make the user's move
            if (a[userRow-1] >= takeCoins){
                
                // set the coins to take 
                theGame.setTakeCoins(takeCoins);
                
                // set the row to take coins from
                theGame.setUserRow(userRow);
                
                // continue the turn
                return true;
            } // end if
        } // end if
        
        // set error message user has not entered a valid move
        playerError.setText(notAMove);
        
        // end players turn
        return false;
    } // end canTheyTakeCoins
/**************************************************************/
    
        public boolean canTheyTakeCoins(int[] a, int takeCoins, int userRow){
        /* this method works just like the one for the players turn
         * but this one is from the computer
         */
        
        // check if the computer is taking a valid turn
        if (userRow > 0 && userRow < 5 && takeCoins > 0 && takeCoins < 4){
            
            //check if there is enough coins in the row to take
            if (a[userRow-1] >= takeCoins){
                
                // set coins to take
                theGame.setTakeCoins(takeCoins);
                
                // set row to take coins from
                theGame.setUserRow(userRow);
                
                // return this is a valid move
                return true;
            } // end if
        } // end if
        
        // otherwise this is not a valid move and end computers turn
        return false;
    } // end canTheyTakeCoins
/**************************************************************/
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
/**************************************************************/
    
    private class Game{
        
        private boolean [][] coinFields = new boolean[4][5]; // this array was not used
        private int [] coinRowAmounts = new int[4];     // coin array
        private boolean gameOver;   // is it gameover
        private int takeCoins;      // amount of coins to take
        private int userRow;        // players row to take coins from
        private int computerRow;    // computers row to take coins from
        private String userText;    // stores the players turns
        private String computerText;    // stores the computers turns
        private int turnNum;        // what turn is it
/**************************************************************/
        
        public Game(){
            int x;  // rows
            int y;  // coins
            int z = 1;  // add coins
            int xx; //set all rows to false
            int yy; //set all coins to false
            
            // resets all class perameters
            gameOver = false;
            takeCoins = 0;
            userRow = 0;
            computerRow = 0;
            userText = "";
            computerText = "";
            turnNum = 0;
            
            int a; // stores row for forloop
            
            // creates the array of coins
            for(a=0; a<4; a++){
                
                //add the amount of coins in the row
                coinRowAmounts[a] = a+2;
                
                // test
                System.out.println("row " + a + "coins " + coinRowAmounts[a]);
            } // end for
            
            // NEVER USED CODE
            for(xx=0; xx<4; xx++){
                for(yy=0; yy<5; yy++){
                    coinFields[xx][yy] = false;
                }
            }
            
            // NEVER USED CODE
            for(x=0; x<4; x++){
                z++;
                for (y=0; y<z; y++){
                    coinFields[x][y] = true;
                }
            }
        } // end constructor method
/**************************************************************/        
        
        public void setTurnNum(){
            
            this.turnNum++;
        } // end setTurnNum 
        
        public int getTurnNum(){
            
            return this.turnNum;
        } // end getTurnNum
        
        public void setUserText(){
            // makes the text for user output and added it to the string
            // for output to user
            
            this.userText = "[" + this.turnNum + "]: Player removed " + this.takeCoins + 
                    " Coins from row " + this.userRow + " \n" + this.userText;
        } // end setUserText
        
        public String getUserText(){
            
            return this.userText;
        } // end getUserText
        
        public void setComputerText(){
            // sets the text for the computers turn as a string to use as
            // output to user
            
            this.computerText = "[" + this.turnNum + "]: Computer removed " + this.takeCoins + 
                    " Coins from row " + this.computerRow + " \n" + this.computerText;
        } // end setComputerText
        
        public String getComputerText(){
            
            return this.computerText;
        } // end getComputerText
        
        public void setComputerRow(int i){
            
            this.computerRow = i;
        } // end setComputerRow
        
        public int getComputerRow(){
            
            return this.computerRow;
        } // end getComputerRow
        
        public void setTakeCoins(int i){
            
            this.takeCoins = i;
        } // end setTakeCoins
        
        public int getTakeCoins(){
            
            return this.takeCoins;
        } // end getTakeCoins
        
        public void setUserRow(int i){
            
            this.userRow = i;
        } // end setUserRow
        
        public int getUserRow(){
            
            return this.userRow;
        } // end getUserRow
        
        public void setGameOver(boolean i){
            
            this.gameOver = i;
        } // end setGameOver
        
        public boolean getGameOver(){

            return this.gameOver;
        } // end getGameOver
        
        public void setCoinFields(int x, int y, boolean i){
            // NOT USED
            this.coinFields[x][y] = i;
        }
        
        public boolean getCoinFields(int x, int y){
            // NOT USED
            return this.coinFields[x][y];
        }
        
        public boolean[][] getCoinFields(){
            // NOT USED
            return this.coinFields;
        }
        
        public void setCoinRowAmounts(int x, int y){
            
            this.coinRowAmounts[x] = y;
        } // end setCoinRowAmounts
        
        public int[] getCoinRowAmounts(){
            
            return this.coinRowAmounts;
        } // end getCoinFowAmounts
        
        public int getCoinRowAmounts(int i){
            
            return this.coinRowAmounts[i];
        } // end getCoinRowAmounts
    } // end game class
/**************************************************************/
    
} // end controller class
