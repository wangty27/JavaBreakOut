import java.awt.Graphics2D;
import java.awt.Color;

import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public class Model extends Observerable {
  private List<Ball> balls = new ArrayList<>();
  private List<List<Brick>> bricks = new ArrayList<>();
  private Bar bar;
  private int moveBar = 0;
  private int score;
  private int time;
  private boolean gameWin = false;
  private int left;
  private int top;
  private int right;
  private int bottom;
  private int brickStartX;
  private int brickStartY;
  private int brickW;
  private int brickH;
  private int FPS = 50;
  private int speed = 1;
  private int barHeight = 10;
  private int barWidth;
  private int gameState = -1; // -1 for menu, 0 for pre-start, 1 for running, 2 for pause, 3 for end
  private int prevState = 0;
  private GameInfoScreen gameInfoScreen;
  private Random rand = new Random();
  private Timer FPSTimer = new Timer();
  private Timer GameTimer = new Timer();
  private Timer BarTimer = new Timer();
  private TimerTask FPSTask;
  private TimerTask GameTask;
  private TimerTask BarTask;

  Model(int top, int left, int right, int bottom, int FPS, int speed) {
    if (FPS > 60) {
      this.FPS = 60;
    } else if (FPS < 20) {
      this.FPS = 20;
    } else {
      this.FPS = FPS;
    }
    
    if (speed > 4) {
      this.speed = 4;
    } else if (speed < 1) {
      this.speed = 1;
    } else {
      this.speed = speed;
    }
    this.setDimension(top, left, right, bottom);
    this.gameInfoScreen = new GameInfoScreen(right, bottom);
    this.FPSTask = new TimerTask() {
      @Override
      public void run() {
        Model.this.notifyObservers();
      }
    };

    this.GameTask = new TimerTask() {
      @Override
      public void run() {
        Model.this.runGame();
      }
    };

    this.BarTask = new TimerTask() {
      @Override
      public void run() {
        Model.this.moveBar();
      }
    };
    this.BarTimer.schedule(this.BarTask, 0, 10);
    this.FPSTimer.schedule(this.FPSTask, 0, 1000 / this.FPS);
  }

  private void moveBar() {
    if (this.moveBar > 0) {
      int barX = this.bar.getPos();
      if (this.moveBar == 1) {
        barX += 10;
        if (barX < 0) {
          barX = 0;
        } else if ((barX + this.barWidth) > this.right) {
          barX = this.right - this.barWidth;
        }
        this.bar.setPos(barX);
      } else {
        barX -= 10;
        if (barX < 0) {
          barX = 0;
        } else if ((barX + this.barWidth) > this.right) {
          barX = this.right - this.barWidth;
        }
        this.bar.setPos(barX);
      }
      if (this.gameState == 0) {
        this.balls.get(0).setBall(barX + this.barWidth / 2 - (this.right - this.left) / 60, this.bottom - this.barHeight - 10 - (this.right - this.left) / 30);
      }
    }
  }

  private void runGame() {
    this.time -= 10;
    boolean gameEnd = true;
    for (Ball b: this.balls) {
      if (b.isAlive()) {
        gameEnd = false;
        break;
      }
    }
    if (this.time <= 0) {
      gameEnd = true;
    }
    if (gameEnd) {
      this.gameState = 3;
      this.GameTimer.cancel();
      return;
    }
    boolean newBall = false;
    Ball newB = null;
    for (Ball b: this.balls) {
      if (!b.isAlive()) {
        continue;
      }
      int x = b.getX();
      int y = b.getY();
      // ball is dead
      if (y > this.bottom - b.getWidth()) {
        b.kill();
        continue;
      }

      // collision with bar
      if (y >= this.bottom - this.barHeight - b.getWidth() - 10) {
        int barL = this.bar.getPos();
        int barR = barL + this.barWidth;
        int ballM = x + b.getWidth() / 2;
        if (ballM >= barL && ballM <= barR) {
          b.bounceY(true);
          int dx = ((b.getVCenter() - barL) - ((barR - barL) / 2)) / 15 * this.speed;
          if (dx == 0 && (b.getVCenter() - barL) >= ((barR - barL) / 2)) {dx = 1 * this.speed;}
          else if (dx == 0 && (b.getVCenter() - barL) < ((barR - barL) / 2)) {dx = -1 * this.speed;}
          b.setDx(dx);
        }
      }
      // collision with wall
      if (y < 0) {
        b.bounceY(false);
      }
      if (x < 0 || x > this.right - b.getWidth()) {
        b.bounceX();
      }
      // collision with bricks
      int brickRegionL = (int)this.bricks.get(0).get(0).getCorner(0).getX();
      int brickRegionT = (int)this.bricks.get(0).get(0).getCorner(0).getY();
      int brickRegionR = (int)this.bricks.get(4).get(9).getCorner(3).getX();
      int brickRegionB = (int)this.bricks.get(4).get(9).getCorner(3).getY();
      if (x >= (brickRegionL - b.getWidth()) && x <= brickRegionR && y >= (brickRegionT - b.getWidth()) && y <= brickRegionB) {
        for (List<Brick> row: this.bricks) {
          for (Brick brick: row) {
            int brickType = brick.getType();
            int collideType = brick.isColliding(b);
            if (collideType > 0) {
              this.score += 10;
              if (collideType == 2 || collideType == 4) {
                b.bounceX();
              }
              if (collideType == 1 || collideType == 3) {
                b.bounceY(false);
              }
              if (brickType == 2) {
                newBall = true;
                newB = b.duplicate();
              }
              if (brickType == 1) {
                this.barWidth = (this.right - this.left) / 6;
                this.bar.updateWidth(this.barWidth);
              }
            }
          }
        }
        this.gameWin = true;
        for (List<Brick> row: this.bricks) {
          for (Brick brick: row) {
            if (brick.getType() != 3) {
              this.gameWin = false;
              break;
            }
          }
        }
      }

      b.moveBall();
    }
    if (newBall) {
      this.balls.add(newB);
    }
    if (this.gameWin) {
      this.gameState = 3;
      this.score += (this.time / 1000);
      this.GameTask.cancel();
    }
  }

  public void startGame() {
    this.GameTimer = null;
    this.GameTimer = new Timer();
    this.GameTask = new TimerTask() {
      @Override
      public void run() {
        Model.this.runGame();
      }
    };
    this.GameTimer.schedule(this.GameTask, 0, 10);
  }

  public void pauseGame() {
    if (this.gameState == 1 || this.gameState == 0) {
      this.prevState = this.gameState;
      this.gameState = 2;
      this.GameTimer.cancel();
    }
  }

  public void restartGame() {
    if (this.gameState == 2 || this.gameState == 3) {
      this.gameState = 0;
      this.initGameScreen();
    }
  }

  public void returnToMenu() {
    if (this.gameState == 2 || this.gameState == 3) {
      this.gameState = -1;
    }
  }

  public void keyboardMoveBar(boolean right) {
    if (this.gameState == 1 || this.gameState == 0) {
      if (this.moveBar == 0) {
        if (right) {
          this.moveBar = 1;
        } else {
          this.moveBar = 2;
        }
      }
    }
  }

  public void keyboardStopBar() {
    if (this.moveBar > 0) {
      this.moveBar = 0;
    }
  }

  public void changeSpeed(boolean up) {
    if (this.gameState == -1) {
      if (up) {
        this.speed += 1;
        if (this.speed > 4) {
          this.speed = 4;
        }
      } else {
        this.speed -= 1;
        if (this.speed < 1) {
          this.speed = 1;
        }
      }
    }
  }

  public void changeState() {
    switch(this.gameState) {
      case -1: {
        this.gameState = 0;
        this.initGameScreen();
        break;
      }
      case 0: {
        this.gameState = 1;
        this.startGame();
        break;
      }
      case 2: {
        this.gameState = this.prevState;
        if (this.gameState == 1) {
          this.startGame();
        }
        break;
      }
      default: break;
    }
  }

  public void initGameScreen() {
    this.time = 60000 * 5;
    this.score = 0;
    this.gameWin = false;
    balls.clear();
    bricks.clear();
    this.brickW = (this.right - this.left) / 7 * 5 / 10;
    this.brickH = (this.bottom - this.top) / 3 / 6;
    this.barWidth = (this.right - this.left) / 8;

    // bar configuration
    this.bar = new Bar((this.right - this.left) / 2 - this.barWidth / 2, this.bottom - this.barHeight - 10, this.barWidth, this.barHeight);

    // ball configuration
    if (this.balls.size() > 0) {
      for (Ball b : this.balls) {
        b.setInfo(0, 0, (this.right - this.left) / 200, (this.right - this.left) / 200, (this.right - this.left) / 40);
      }
    } else {
      Ball startingBall = new Ball((this.right - this.left) / 2 - (this.right - this.left) / 60, this.bottom - this.barHeight - 10 - (this.right - this.left) / 30, 0, -(this.right - this.left) / 200 * this.speed, (this.right - this.left) / 30);
      this.balls.add(startingBall);
    }

    // brick configuration
    int y = (this.bottom - this.top) / 2 - (this.brickH + 2) * 7;
    this.brickStartY = y;
    this.brickStartX = (this.right - this.left) / 2 - (this.brickW + 2) * 6;
    int doubleBallIndex = this.rand.nextInt(30);
    int extendBarIndex = this.rand.nextInt(40);
    while (extendBarIndex == doubleBallIndex) {
      extendBarIndex = this.rand.nextInt(40);
    }
    if (this.bricks.size() > 0) {
      for (int i = 0; i < 5; ++i) {
        List<Brick> row = this.bricks.get(i);
        int x = this.brickStartX;
        y += (this.brickH + 2);
        for (int j = 0; j < 8; ++j) {
          x += (this.brickW + 2);
          Brick brick = row.get(j);
          brick.setDimension(x, y, this.brickW, this.brickH);
        }
      }
    } else {
      for (int i = 0; i < 5; ++i) {
        List<Brick> row = new ArrayList<>();
        Color color = Color.BLACK;
        int x = this.brickStartX;
        y += (this.brickH + 2);
        switch (i) {
        case 0:
          color = Color.MAGENTA;
          break;
        case 1:
          color = Color.BLUE;
          break;
        case 2:
          color = Color.YELLOW;
          break;
        case 3:
          color = Color.ORANGE;
          break;
        case 4:
          color = Color.RED;
          break;
        default:
          break;
        }
        for (int j = 0; j < 10; ++j) {
          x += (this.brickW + 2);
          int brickType = 0;
          if (i * 10 + j == doubleBallIndex) {
            brickType = 2;
          } else if (i * 10 + j == extendBarIndex) {
            brickType = 1;
          }
          Brick newBrick = new Brick(x, y, this.brickW, this.brickH, color, brickType);
          row.add(newBrick);
        }
        this.bricks.add(row);
      }
    }
  }

  public void setDimension(int top, int left, int right, int bottom) {
    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
    this.initGameScreen();
  }

  public void barMoveByMouse(int mouseX, int mouseY) {
    int barX = mouseX - this.barWidth / 2;
    if (barX < 0) {
      barX = 0;
    } else if ((barX + this.barWidth) > this.right) {
      barX = this.right - this.barWidth;
    }
    if (this.gameState == 0 || this.gameState == 1) {
      this.bar.setPos(barX);
    }
    if (this.gameState == 0) {
      this.balls.get(0).setBall(barX + this.barWidth / 2 - (this.right - this.left) / 60, this.bottom - this.barHeight - 10 - (this.right - this.left) / 30);
    }
  }

  public void drawBar(Graphics2D g) {
    this.bar.draw(g);
  }

  public void drawBricks(Graphics2D g) {
    for (List<Brick> row : this.bricks) {
      for (Brick brick : row) {
        brick.draw(g);
      }
    }
  }

  public void drawBalls(Graphics2D g) {
    for (Ball b : this.balls) {
      b.draw(g);
    }
  }

  public void drawInfo(Graphics2D g) {
    this.gameInfoScreen.draw(g, this.gameState, this.score, this.time, this.FPS, this.speed, this.gameWin);
  }

  public void drawScreen(Graphics2D g) {
    this.drawBricks(g);
    this.drawBalls(g);
    this.drawBar(g);
    this.drawInfo(g);
  }
}