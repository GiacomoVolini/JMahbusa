package jmb;

import jmb.logic.Logic;
import jmb.view.View;

public class Main {

    public static void main(String[] args) {
        interfaceInstantiation();
        jmb.view.App.main(args);
    }
    private static void interfaceInstantiation() {
        Logic.setLogic(new Logic());
        Logic.getLogic().initializeProgramLogic();
        View.setLogic(Logic.getLogic());
        View.setView(new View());
        Logic.setView(View.getView());
    }
}
