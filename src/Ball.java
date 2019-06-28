import java.awt.Graphics2D;
import java.awt.Color;

public class Ball {
  private int x;
  private int y;
  private int dx;
  private int dy;
  private int width;
  private boolean alive;
  Ball(int x, int y, int dx, int dy, int w) {
    this.x = x;
    this.y = y;
    this.dx = dx;
    this.dy = dy;
    this.width = w;
    this.alive = true;
  }

  public int getX() {
    return this.x;
  }
  
  public int getY() {
    return this.y;
  }

  public void setPos(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void bounceX() {
    this.dx = -this.dx;
  }
  
  public void bounceY(boolean bounceUpOnly) {
    if (bounceUpOnly) {
      if (this.dy > 0) {
        this.dy = -this.dy;
      }
    } else {
      this.dy = -this.dy;
    }
  }

  public int getRightEdge() {
    return this.x + this.width;
  }

  public int getHCenter() {
    return this.y + this.width / 2;
  }

  public int getVCenter() {
    return this.x + this.width / 2;
  }

  public int getBottomEdge() {
    return this.y + this.width;
  }

  public int getWidth() {
    return this.width;
  }

  public void kill() {
    this.alive = false;
  }

  public boolean isAlive() {
    return this.alive;
  }

  public void moveBall() {
    this.x += this.dx;
    this.y += this.dy;
  }

  public void setDx(int dx) {
    this.dx = dx;
  }

  public void setInfo(int x, int y, int dx, int dy, int w) {
    if (this.dx > 0) {
      this.dx = dx;
    } else {
      this.dx = -dx;
    }
    if (this.dy > 0) {
      this.dy = dy;
    } else {
      this.dy = -dy;
    }
    System.out.println(this.dx);
    this.width = w;
  }

  public void setBall(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Ball duplicate() {
    return new Ball(this.x, this.y, -this.dx, this.dy, this.width);
  }

  public void draw(Graphics2D g) {
    if (alive) {
      g.setColor(Color.WHITE);
      g.fillOval(this.x, this.y, this.width, this.width);
    }
  }
}