package jmb;

import jmb.logic.Logic;
import jmb.view.View;

public class Main {

    public static void main(String[] args) {
        interfaceInstantiation();
        jmb.view.App.main(args);
    }
    private static void interfaceInstantiation() {
        Logic.createLogic();
        View.setLogic(Logic.getLogic());
        View.createView();
        Logic.setView(View.getView());
    }
}
