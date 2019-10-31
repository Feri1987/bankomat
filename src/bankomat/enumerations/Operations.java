/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankomat.enumerations;

/**
 *
 * @author Denis
 */
public enum Operations {
    SUMM {
        public int action(int x, int y) {
            return x + y;
        }
    },
    SUBSTRACT {
        public int action(int x, int y) {
            return x - y;
        }
    };

    public abstract int action(int x, int y);

}
