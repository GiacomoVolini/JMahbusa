package jmb;

import jmb.model.Logic;
import jmb.view.View;

import static jmb.model.Logic.ldb;

public class Main {

    public static void main(String[] args) {
        interfaceInstantiation();
        jmb.view.App.main(args);
    }
    private static void interfaceInstantiation() {
        Logic.logic = new jmb.model.Logic();
        View.logic = Logic.logic;
        View.view = new jmb.view.View();
        Logic.view = View.view;
        Logic.logic.initializeTutorialLogic();
        Logic.logic.initializeBoardLogic();
        Logic.logic.initializeLeaderboardLogic();
        Logic.logic.initializeSettingsLogic();
        Logic.logic.initializeStringsReader();
    }
}
