import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import javax.swing.BoxLayout;
import java.lang.Exception;

public class Main {
  public static void main(String[] args) throws IOException {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        if (args.length == 2) {
          try {
            new BreakOut(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
          } catch (Exception e) {}//ignore exception
        } else {
          try {
            new BreakOut(50, 1);
          } catch (Exception e) {}//ignore exception
        }
        
      }
    });
  }
}

class BreakOut extends JFrame {
  private Model model;
  private Canvas canvas;
  private class RootPanel extends JPanel {

    RootPanel(Canvas canvas) {
      this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      this.setBackground(Color.BLACK);
      this.add(canvas);
    }
  }

  BreakOut(int FPS, int speed) throws IOException {
    super("Break Out");
    this.model = new Model(0, 0, 770, 570, FPS, speed);
    this.canvas = new Canvas(model);
    this.model.addObserver(this.canvas);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // this.setPreferredSize(new Dimension(700, 700));
    // this.setMinimumSize(new Dimension(500, 500));
    // this.setMaximumSize(new Dimension(900, 900));
    this.setPreferredSize(new Dimension(800, 600));
    this.setMinimumSize(new Dimension(800, 600));
    this.setMaximumSize(new Dimension(800, 600));
    this.setLocation(0, 0);
    // this.addComponentListener(new ComponentAdapter() {
    //   public void componentResized(ComponentEvent componentEvent) {
    //     // System.out.println("Windows resized, Width: " + BreakOut.this.getWidth() + " Height: " + BreakOut.this.getHeight());
    //     BreakOut.this.canvas.windowResized(BreakOut.this.getWidth(), BreakOut.this.getHeight());
    //   }
    // });
    this.setBackground(Color.BLACK);
    this.add(new RootPanel(this.canvas));
    this.pack();
    this.setVisible(true);
  }
}