package main.model.utils.services.tabstrategy;

import main.view.View;

import java.util.Set;

/**
 * Tab Strategy implementation for Admin
 */
public class AdminStrategy implements TabShowStrategy {
    @Override
    public Set<String> tabNames() {
        return Set.of("adminTab", "opTab", "viewAllParkingTab");
    }

    @Override
    public void fillTabs(View view) {
        view.update();
    }
}
