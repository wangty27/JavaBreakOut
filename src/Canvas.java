import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Canvas extends JPanel implements Observer {
  private Model model;
  private BufferedImage image = ImageIO.read(new File("src/img/background.jpg"));


  Canvas(Model m) throws IOException {
    this.model = m;
    this.setSize(new Dimension(770, 570));
    this.setMaximumSize(new Dimension(770, 570));
    this.setMinimumSize(new Dimension(770, 570));
    this.setBackground(Color.BLACK);
    this.setFocusable(true);

    // Mouse motion controller
    this.addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseMoved(MouseEvent e) {
        Canvas.this.model.barMoveByMouse(e.getX(), e.getY());
      }
    });

    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
          case KeyEvent.VK_SPACE: {
            Canvas.this.model.changeState();
            break;
          }
          case KeyEvent.VK_ESCAPE: {
            Canvas.this.model.pauseGame();
            break;
          }
          case KeyEvent.VK_R: {
            Canvas.this.model.restartGame();
            break;
          }
          case KeyEvent.VK_BACK_SPACE: {
            Canvas.this.model.returnToMenu();
            break;
          }
          case KeyEvent.VK_UP: {
            Canvas.this.model.changeSpeed(true);
            break;
          }
          case KeyEvent.VK_DOWN: {
            Canvas.this.model.changeSpeed(false);
            break;
          }
          case KeyEvent.VK_RIGHT: {
            Canvas.this.model.keyboardMoveBar(true);
            break;
          }
          case KeyEvent.VK_LEFT: {
            Canvas.this.model.keyboardMoveBar(false);
            break;
          }
          default: break;
        }
      }
      @Override
      public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
          Canvas.this.model.keyboardStopBar();
        }
      }

      @Override
      public void keyTyped(KeyEvent e) {
      }
    });

  }

  public void addNotify() {
    super.addNotify();
    this.requestFocus();
  }

  public void update() {
    this.repaint();
  }
  
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
    g.setColor(new Color(0, 0, 0, 140));
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    this.model.drawScreen(g2);
  }

  public void windowResized(int width, int height) {
    int len = Math.min(width - 30, height - 30);
    this.setSize(new Dimension(len, len));
    this.setMaximumSize(new Dimension(len, len));
    this.setMinimumSize(new Dimension(len, len));
    this.model.setDimension(0, 0, this.getWidth(), this.getHeight());
  }

  
}