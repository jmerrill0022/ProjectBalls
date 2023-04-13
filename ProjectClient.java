 import javafx.application.*;
 import javafx.scene.*;
 import javafx.stage.*;
 import javafx.scene.layout.*;
 import javafx.scene.control.*;
 import java.util.*;
 import javafx.geometry.*;
 import javafx.scene.paint.*;
 import javafx.scene.image.*;
 import javafx.event.*;
 import javafx.animation.*;
 import javafx.application.*;
 import javafx.scene.*;
 import javafx.stage.*;
 import javafx.scene.layout.*;
 import javafx.scene.control.*;
 import java.util.*;
 import javafx.geometry.*;
 import javafx.scene.paint.*;
 import javafx.scene.image.*;
 import javafx.scene.canvas.*;
 import javafx.scene.input.*;


public class ProjectClient extends Application
{
   //create variables for the counters and the label
   private int totalBalls = 15;
   private int posibleMoves = 2;
   private Label label;
   //create the 2d array
   private GamePane [][] panes = new GamePane[4][4];
   
   public void start(Stage stage)
   {
      label = new Label("Balls Left: "+totalBalls+"   Possible Moves: "+posibleMoves);
      label.setBackground(new Background(new BackgroundFill(Color.CYAN, CornerRadii.EMPTY, Insets.EMPTY)));
      //create the border pane
      BorderPane bp = new BorderPane();
      bp.setAlignment(label,Pos.TOP_CENTER); 
      //add the label to the 
      bp.setTop(label);
                  
      GridPane gp = new GridPane();
      for(int xPos = 0;xPos<4;xPos++){
         for(int yPos = 0;yPos<4; yPos++){
            GamePane game = new GamePane();
            gp.add(game,xPos,yPos);
            panes[xPos][yPos] = game;
            
            //hard code the first ball to be invisible
            if(xPos == 0 && yPos == 2){
               game.setBallVis(false);
            }           
         }
      }
      
      buttonMove();
      bp.setCenter(gp);
      gp.setAlignment(Pos.CENTER);
      
      Scene scene = new Scene(bp, 600, 600);
      stage.setScene(scene);
      stage.setTitle("Component Examples");
      stage.show();
   } 
   //create a counter to check how many balls are visible
   public void counter(){
      int tempBalls = 0;
      int possibleCount = 0;
      for(int i = 0; i<4; i++){
         for(int j = 0; j<4; j++){
         //get the panes and find the visibilities and count them
            if(panes[i][j].getBallVis()){
               tempBalls++;
            }
            if(panes[i][j].getVisT() || panes[i][j].getVisB() || panes[i][j].getVisR() || panes[i][j].getVisL()){
               possibleCount++;
            }
         }
         //add the variables to the text and setText
         label.setText("Balls Left: "+tempBalls+"   Possible Moves: "+possibleCount);
         if(possibleCount == 0){
            label.setText("You LOST!");
         }
         if(tempBalls == 1){
            label.setText("You WIN!");
         }
      }
   }
   
   
   //create a click method
   public void click(int x, int y,String button){
      GamePane currentPane = panes[x][y];
      GamePane nextPane = null;
      //find what button is clicked with the string message
      switch(button){
         case"top":
            //find the next pane based off the button pushed
            nextPane = panes[x][y+2];
            //put the ball getting kicked over to be false so its invisible
            panes[x][y+1].setBallVis(false);
            //set the ball that was kicked to be true so its visible
            nextPane.setBallVis(true);
            break;
         //repeate for these buttons
         case"bottom":
            nextPane = panes[x][y-2];
            panes[x][y-1].setBallVis(false);
            nextPane.setBallVis(true);
            break;
         
         case"right":
            nextPane = panes[x-2][y];
            panes[x-1][y].setBallVis(false);
            nextPane.setBallVis(true);
            break;   
         
         case"left":
            nextPane = panes[x+2][y];
            panes[x+1][y].setBallVis(false);
            nextPane.setBallVis(true);
            break;
            
      }
   //put the hole pane that you were on to invisible
   currentPane.setBallVis(false);
   //use the button move
   buttonMove();
   //use the counter method for the label
   counter();
   }

   //i is row
   //j is column
   public void buttonMove(){
      for(int i = 0; i < 4; i++){
      
         for(int j = 0; j < 4; j++){
         
         //bottom
         if(j >= 2 && panes[i][j].getBallVis() && panes[i][j-1].getBallVis() && !panes[i][j-2].getBallVis()){
            
            panes[i][j].setVisB(true);
            }
            else{
            
            panes[i][j].setVisB(false);
            }
            //top
         if(j <= 1 && panes[i][j].getBallVis() && panes[i][j+1].getBallVis() && !panes[i][j+2].getBallVis()){
            
            panes[i][j].setVisT(true);
            }
            else{
            
            panes[i][j].setVisT(false);
            }
            //right
         if(i >= 2 && panes[i][j].getBallVis() && panes[i-1][j].getBallVis() && !panes[i-2][j].getBallVis()){
            
            panes[i][j].setVisR(true);
            }
            else{
            
            panes[i][j].setVisR(false);
            }
            //left
            if(i <= 1 && panes[i][j].getBallVis() && panes[i+1][j].getBallVis() && !panes[i+2][j].getBallVis()){
            
               panes[i][j].setVisL(true);
            }
            else{
            
               panes[i][j].setVisL(false);
            }
         }
      }   
   }
   public class GamePane extends GridPane{
      GraphicsContext gc;
      Canvas theCanvas = new Canvas();
      
      //add the following variables 
         //  -----ball visibility (boolean)
         //  -----buttons visibility (boolean)
         //  -----accessors and mutators when needed
         //  -----draw method including the visibilities
      
      boolean ballVis = true;
      boolean visL = false;
      boolean visR = false;
      boolean visT = false;
      boolean visB = false;
      
      //create them outside gamepane
      Button left;
      Button right;
      Button top;
      Button bottom;
      
      public GamePane(){
         
         //create the buttons with reference to outside the class so it can be used outside the class
         left = new Button();
         right = new Button();
         top = new Button();
         bottom = new Button();
         
         //set action to the buttons so they can do things
         top.setOnAction(new ButtonListener());
         bottom.setOnAction(new ButtonListener());
         left.setOnAction(new ButtonListener());
         right.setOnAction(new ButtonListener());
         
         //set sizes for the buttons
         left.setPrefSize(20,80);
         right.setPrefSize(20,80);
         top.setPrefSize(80,20);
         bottom.setPrefSize(80,20);
         
         //add the buttons to the panes
         add(left,0,1);
         add(right,2,1);
         add(top,1,0);
         add(bottom,1,2);
         
         //add to the canvas to the center of the pane with dimensions
         theCanvas.setWidth(80);
         theCanvas.setHeight(80);
         add(theCanvas,1,1);
         draw();
      }
      
      //draw method using the visibilities
      public void draw(){
         this.visL = visL;
         this.visR = visR;
         this.visT = visT;
         this.visB = visB;
         //draw the circle based off of visibility
         gc = theCanvas.getGraphicsContext2D();
         gc.clearRect(0,0,theCanvas.getWidth(),theCanvas.getHeight());
         if(getBallVis()){
            gc.setFill(Color.LIGHTGREEN);
            gc.fillOval(0,0,80,80);
            gc.setFill(Color.GREEN);
            gc.fillOval(0,0,60,60);
            gc.setFill(Color.GREEN);
            gc.fillOval(20,0,60,60);
            gc.setFill(Color.WHITE);
            gc.fillOval(10,20,20,20);
            gc.setFill(Color.WHITE);
            gc.fillOval(50,20,20,20);
            gc.setFill(Color.BLACK);
            gc.fillOval(55,20,10,10);
            gc.fillOval(15,20,10,10);
            gc.fillOval(15,65,40,10);
         }
         
         //draw the buttons based off of the visibility
         left.setVisible(getVisL());
         right.setVisible(getVisR());
         top.setVisible(getVisT());
         bottom.setVisible(getVisB());
      }
      
      //get visibility methods (getters)
      public boolean getVisL(){
         return visL;
      }
      public boolean getVisR(){
         return visR;
      }
      public boolean getVisT(){
         return visT;
      }
      public boolean getVisB(){
         return visB;
      }
      public boolean getBallVis(){
         return ballVis;
      }
      
      //set the visibility of the balls and buttons
      public void setBallVis(boolean ballVis){
         this.ballVis = ballVis;
         draw();
      }
      public void setVisL(boolean visL){
         this.visL = visL;
         draw();
      }
      public void setVisR(boolean visR){
         this.visR = visR;
         draw();
      }
      public void setVisT(boolean visT){
         this.visT = visT;
         draw();
      }
      public void setVisB(boolean visB){
         this.visB = visB;
         draw();
      }
      
      //create a button listener
      public class ButtonListener implements EventHandler<ActionEvent>{  
         public void handle(ActionEvent e){
            for(int i = 0; i < 4; i++){
               for(int j = 0; j<4; j++){
                  //run through the 2d array with the for loops
                  //access the buttons based off of the string in the click method
                  if(panes[i][j].top == e.getSource()){
                     click(i,j,"top");
                  }
                  if(panes[i][j].bottom == e.getSource()){
                     click(i,j,"bottom");
                  }
                  if(panes[i][j].right == e.getSource()){
                     click(i,j,"right");
                  }
                  if(panes[i][j].left == e.getSource()){
                     click(i,j,"left");
                  }
               }
            }
         }
      }
   } 
   
   //LAUNCHHHH
   public static void main(String[] args)
   {
      launch(args);
   }
}      
