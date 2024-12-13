package server;

import resources.Actions;

class Game {
    int p1Games = 0;
    int p2Games = 0;
    int p1Points = 0;
    int p2Points = 0;
    int rounds = 3;
    int p1Lives = 2;
    int p2Lives = 2;
    int roundWinner;
    boolean playerHit;

    void restart() {

        p1Lives = 2;
        p2Lives = 2;

    }

    void hardRestart() {

        p1Points = 0;
        p2Points = 0;
        rounds = 3;
        p1Lives = 2;
        p2Lives = 2;

    }

    int action(int p1Action, int p2Action) {

        playerHit = false;

        if(p1Action == Actions.SHOOT && p2Action != Actions.DEFEND) {
            p2Lives--;
            playerHit = true;
        }

        if(p2Action == Actions.SHOOT && p1Action != Actions.DEFEND) {
            p1Lives--;
            playerHit = true;
        }

        if(p1Lives == 0 || p2Lives ==0) {

            // Both died
            if(p1Lives == 0 && p2Lives ==0) {

                p1Lives++;
                p2Lives++;

                return 4;
            }

            if(p1Lives == 0) p2Points += 1;
            else p1Points += 1;
            rounds--;

            // End game
            if(rounds < p1Points || rounds < p2Points) {

                if(p1Points > p2Points) {
                    p1Games++;
                    roundWinner = 1;
                }
                else {
                    p2Games++;
                    roundWinner = 2;
                }

                hardRestart();
                return 3;
            }

            p1Lives = 2;
            p2Lives = 2;

            restart();

            return 1;
        }

        return 2;
    }

}
