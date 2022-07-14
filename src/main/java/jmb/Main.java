package jmb;

import static jmb.model.Logic.ldb;
import static jmb.model.Logic.view;
import static jmb.view.View.logic;

public class Main {

    public static void main(String[] args) {
        interfaceInstantiation();
        jmb.view.App.main(args);
    }
    private static void interfaceInstantiation() {
        logic = new jmb.model.Logic();
        view = new jmb.view.View();
        logic.initializeTutorialLogic();
        logic.initializeBoardLogic();
        logic.initializeLeaderboardLogic();
        logic.initializeSettingsLogic();
    }
}
