import java.awt.Graphics2D;
import java.awt.Color;

public class Bar {
  private int x;
  private int y;
  private int w;
  private int h;

  Bar(int x, int y, int w, int h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }

  public void setPos(int mouseX) {
    this.x = mouseX;
  }

  public int getPos() {
    return this.x;
  }

  public void updateWidth(int w) {
    this.w = w;
  }

  public void draw(Graphics2D g) {
    g.setColor(Color.WHITE);
    g.fillRect(this.x, this.y, this.w, this.h);
  }
}