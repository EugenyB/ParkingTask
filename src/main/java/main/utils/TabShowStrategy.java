package main.utils;

import main.view.View;

import java.util.Set;

public interface TabShowStrategy {
    Set<String> tabNames();

    void fillTabs(View view);
}
