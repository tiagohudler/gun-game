package server;

public class Game {
    int p1Games = 0;
    int p2Games = 0;
    int p1Points = 0;
    int p2Points = 0;
    int rounds = 3;
    int p1Lives = 2;
    int p2Lives = 2;
    int roundWinner;

    void restart() {

        p1Points = 0;
        p2Points = 0;
        rounds = 3;
        p1Lives = 2;
        p2Lives = 2;

    }

    int action(int p1Action, int p2Action) {

        if(p1Action == Actions.SHOOT && p2Action != Actions.DEFEND) {
            p2Lives--;
        }

        if(p2Action == Actions.SHOOT && p1Action != Actions.DEFEND) {
            p1Lives--;
        }

        if(p1Lives == 0 || p2Lives ==0) {

            // Both died
            if(p1Lives == 0 && p2Lives ==0) {

                p1Lives++;
                p2Lives++;

                return 4;
            }

            if(p1Lives == 0) p2Points++;
            else p1Points++;
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

                restart();
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
