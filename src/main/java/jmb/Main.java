package jmb;

import jmb.logic.Logic;
import jmb.view.View;

public class Main {

    public static void main(String[] args) {
        interfaceInstantiation();
        jmb.view.App.main(args);
    }
    private static void interfaceInstantiation() {
        Logic.logic = new jmb.logic.Logic();
        View.logic = Logic.logic;
        View.view = new jmb.view.View();
        Logic.view = View.view;
        Logic.logic.initializeProgramLogic();
    }
}
