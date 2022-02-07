package main.model.utils.services.tabstrategy;

import main.view.View;

import java.util.Set;

/**
 * Strategy for show or hide tabs
 * One abstract method is tabNames
 * Two classes implement it
 */
public interface TabShowStrategy {
    Set<String> tabNames();

    default void fillTabs(View view) {
        view.update();
    }
}
