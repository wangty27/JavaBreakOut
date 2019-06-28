import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;

public class Brick {
  private int x;
  private int y;
  private int w;
  private int h;
  private int type;
  private Color c;
  
  Brick(int x, int y, int w, int h, Color c, int type) {
    this.x = x;
    this.y = y;
    this.c = c;
    this.w = w;
    this.h = h;
    this.type = type;
    switch(this.type) {
      case 1: {
        this.c = Color.CYAN;
        break;
      }
      case 2: {
        this.c = Color.GREEN;
        break;
      }
      case 3: {

        break;
      }
      default: break;
    }
  }

  public void setDimension(int x, int y, int w, int h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }

  public int isColliding(Ball b) {
    if (this.type == 3) {
      return 0;
    }
    if (b.getX() <= this.x + this.w && b.getX() + b.getWidth() >= this.x && b.getY() <= this.y + this.h && b.getY() + b.getWidth() >= this.y) {
      if (b.getBottomEdge() >= this.y && b.getBottomEdge() <= this.y + this.h / 3 && b.getVCenter() <= this.x + this.w && b.getVCenter() >= this.x) {
        // ball hit from top
        this.type = 3;
        return 1;
      } else if (b.getY() <= this.y + this.h && b.getY() >= this.y + this.h - this.h / 3 && b.getVCenter() <= this.x + this.w && b.getVCenter() >= this.x) {
        // ball hit from bottom
        this.type = 3;
        return 3;
      } else if (b.getX() <= this.x + this.w && b.getX() >= this.x + this.w - this.w / 3 && b.getHCenter() <= this.y + this.h && b.getHCenter() >= this.y) {
        // ball hit from right
        this.type = 3;
        return 2;
      } else if (b.getRightEdge() >= this.x && b.getRightEdge() <= this.x + this.w / 3 && b.getHCenter() <= this.y + this.h && b.getHCenter() >= this.y) {
        // ball hit from left
        this.type = 3;
        return 4;
      } else {
        return 0;
      }
    }
    return 0;
  }

  public Point getCorner(int i) {
    switch(i) {
      case 0: {
        return new Point(this.x, this.y);
      }
      case 1: {
        return new Point(this.x + this.w, this.y);
      }
      case 2: {
        return new Point(this.x, this.y + this.h);
      }
      case 3: {
        return new Point(this.x + this.w, this.y + this.h);
      }
      default: {
        return new Point();
      }
    }
  }

  public int getType() {
    return this.type;
  }

  public void draw(Graphics2D g) {
    if (this.type != 3) {
      g.setColor(this.c);
      g.fillRect(this.x, this.y, this.w, this.h);
    }
  }
  
}