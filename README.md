# Numbreaka

Numbreaka is a 6x6 grid-based number game.
The object of the game is to combine numbers by overlapping them or moving them around the grid according to various power-ups in an attempt to achieve the highest score possible.

# Game Window
The game window consists of the game title above a game grid.
The game title is flanked by the following two boxes:
```
Left box:  Displays the "current number"
Right box: Displays the current power-up (blank if none)
```

# Gameplay
Select a square by clicking it to "break" the square and add the current number to the broken square's neighbors (the squares above, below and to either side of the selected square).
The current number is then incremented, and a new square can be selected.
The game proceeds until all squares are either broken or filled with a number, and then the high score is calculated by adding up all values displayed on the game grid.

# Power-ups
Power-ups are represented by short text snippets in the upper-right corner of the screen and are as follows:
```
x2   Multiples the neighbors by 2 instead of adding the current number
U-D  Only adds the current number to the neighbors Up and Down from the selected square.
L-R  Only adds the current number to the neighbors Left and Right from the selected square.
-->  Rotates all neighbors to the right (up > right > down > left > up). Does not break the selected square.
<--  Rotates all neighbors to the left (up > left > down > right > up). Does not break the selected square.
<=>  Reverses the values of all neighbors. (15 becomes 51, 100 becomes 1 (001), etc.)
CON  Consolidates the values of all neighbors to the selected square. Does not break the selected square.
REZ  Resurrects the selected squares ("unbreaking" it).
```

# Developer keys
Developer keys are current enabled as follows:
```
1-8  Grant a specific power-up (see above). The upper-right box displays the selected power-up. 
     You can press numbers multiple times, but only the power-up granted by the last key pressed 
     will be active; all others are overwritten.
c    Increments the current number in the upper-left box.
q    Quits the game at any time.
r    Resets the game at any time.
h    Resets the high score at any time. If pressed on the high scores screen, the screen will not change.
     If you go to the main menu and then back into the high scores screen, they will all be reset to AAA 000.
```

# Feedback
Game feedback and code reviews are welcomed.

# Thanks
Redditors deltageek and ikpenpinda for helpful code reviews.
