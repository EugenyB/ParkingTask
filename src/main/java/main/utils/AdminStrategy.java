package main.utils;

import main.view.View;

import java.util.Set;

public class AdminStrategy implements TabShowStrategy {
    @Override
    public Set<String> tabNames() {
        return Set.of("adminTab", "opTab");
    }

    @Override
    public void fillTabs(View view) {
        // todo fill All tabs
        view.update();
    }
}