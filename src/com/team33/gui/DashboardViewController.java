package com.team33.gui;

public class DashboardViewController implements Controller {

    private MainApp mainApp;

    @Override
    public void cancel() {

    }

    @Override
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
