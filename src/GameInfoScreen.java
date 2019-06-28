import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

public class GameInfoScreen {
  private int w;
  private int h;
  GameInfoScreen(int w, int h) {
    this.w = w;
    this.h = h;
  }

  public void draw(Graphics2D g, int gameState, int score, int time, int FPS, int speed, boolean gameWin) {
    if (gameState == -1) {
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, this.w, this.h);
      g.setColor(Color.WHITE);
      g.setFont(new Font("Arial", Font.BOLD, 50));
      g.drawString("Break Out", 270, 130);
      g.setFont(new Font("Arial", Font.PLAIN, 19));
      g.drawString("Press \"SPACE\" to Start the game     Press \"ESC\" to Pause the game", 90, 210);
      g.setFont(new Font("Arial", Font.PLAIN, 19));
      g.drawString("Ball Speed: " + speed, 320, 280);
      g.drawString("Press UpArrow/DownArrow to increase/decrease ball speed",110, 310);
      g.setFont(new Font("Arial", Font.PLAIN, 17));
      g.drawString("- Move cursor around or press LeftArrow/RightArrow to move the paddle", 180, 360);
      g.drawString("- Clearing a block awards 10 points", 180, 400);
      g.drawString("- The remaining time will be awarded as points", 180, 440);
      g.drawString("- Clear all the bricks to win the game!", 180, 480);
      g.setColor(Color.GREEN);
      g.fillRect(50, 510, 50, 30);
      g.setColor(Color.WHITE);
      g.drawString("Green Brick awards a second ball", 110, 530);
      g.setColor(Color.CYAN);
      g.fillRect(420, 510, 50, 30);
      g.setColor(Color.WHITE);
      g.drawString("Cyan Brick extends the paddle", 480, 530);
    } else if (gameState == 1 || gameState == 0) {
      g.setColor(Color.WHITE);
      g.setFont(new Font("Arial", Font.PLAIN, 15));
      g.drawString("Time: " + time / 1000 + "s", 10, 20);
      g.drawString("Score: " + score, 10, 40);
      g.setFont(new Font("Arial", Font.PLAIN, 13));
      g.drawString("FPS " + FPS, 10, 60);
      g.drawString("Speed " + speed, 10, 80);
    } else if (gameState == 2) {
      Color c = new Color(0, 0, 0, 220);
      g.setColor(c);
      g.fillRect(0, 0, this.w, this.h);
      g.setColor(Color.WHITE);
      g.setFont(new Font("Arial", Font.BOLD, 50));
      g.drawString("Game Paused", 230, 150);
      g.setFont(new Font("Arial", Font.PLAIN, 17));
      g.drawString("Press \"SPACE\" to Continue", 230, 250);
      g.drawString("Press \"R\" to Restart", 230, 300);
      g.drawString("Press \"BackSpace\" to Return to main menu", 230, 350);
    } else if (gameState == 3) {
      Color c = new Color(0, 0, 0, 220);
      g.setColor(c);
      g.fillRect(0, 0, this.w, this.h);
      g.setColor(Color.WHITE);
      if (gameWin) {
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("You Won!", 230, 150);
      } else {
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Game Over", 230, 150);
      }
      g.setFont(new Font("Arial", Font.BOLD, 23));
      g.drawString("Score: " + score, 230, 225);
      g.setFont(new Font("Arial", Font.PLAIN, 17));
      g.drawString("Press \"R\" to Restart", 230, 300);
      g.drawString("Press \"BackSpace\" to Return to main menu", 230, 350);
    }
  }
}