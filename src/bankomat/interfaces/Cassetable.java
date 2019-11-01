/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankomat.interfaces;

import bankomat.pratsOfATM.Casset;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Denis
 */
public interface Cassetable {

    void initCasset();

    List<Casset> autoInitCasset();

    void giveCash(Map<Integer, Integer> countOfdenominations);

    void cassetEmptyInforming();

    List<Casset> getCassetList();

}
