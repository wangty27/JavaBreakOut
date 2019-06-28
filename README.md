## How to run the program
```bash
$ gradle build
$ gradle run --args="[FPS] [Speed]"
```
### Command line arguments
* [FPS] is the FPS the game will be run in (Range: 20 - 60)
* [Speed] is the Ball speed, this can be changed afterwards (Range: 1 - 4)
* Sample argument: `"50 2"`

## Game Rules
### How to win
* Move the paddle around to keep the ball bouncing, if the ball touches the bottom, you will lose the ball, if there is no ball remaining, you lose
* If the timer runs out before you clear all the bricks, you lose
* Clear all the bricks to win the game

### How to score
* Clearing a brick awards 10 points
* If you clear all the bricks, the remaining time will be rewarded as points (1s = 1 point)
* Clear all the bricks as fast as possible to get high scores

### Special Bricks
* Clearing a GREEN brick will award you a second ball, don't worry if you lose on of them, you lose the game only if you lose both balls
* Clearing a CYAN brick will permanently extend the length of your padldle

## Game Controls
### Menu
* Press `SPACE` to start the game
* Press `UpArrow`/`DownArrow` to increase/decrease the speed of the ball

### Paddle Movements
* Move cursor around on the screen, the paddle will follow the cursor's movement
* Press and hold `RightArrow`/`LeftArrow` to move the paddle to the right/left (May have delays)

### Pausing/Continuing/Restarting
* Press `SPACE` to start the game, press `SPACE` to shoot the ball during the pre-start phase
* Press `ESC` to pause the game, when the game is paused, you have the option to restart (press `R`) or return to main menu (press `BackSpace`)
* Whne you win/lose, press `R` to restart or press `BackSpace`

## References
* Background picture: https://unsplash.com

#### Built and tested on MacOS Mojave 10.14.5 