package com.example.tourplanner.bl;

public class AppLogicFactory {

    private static IAppLogic appLogic;


    public static IAppLogic getAppLogic() {
        if(appLogic == null) {
            appLogic = new AppLogic();
        }

        return appLogic;
    }
}
