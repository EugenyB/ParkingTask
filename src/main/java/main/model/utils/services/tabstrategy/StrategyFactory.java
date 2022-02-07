package main.model.utils.services.tabstrategy;

import main.model.data.User;

/**
 * Factory for TabStrategy
 */
public class StrategyFactory {
    public static TabShowStrategy getTabShowStrategy(User user) {
        if (user.isAdmin()) return new AdminStrategy();
        else return new UserStrategy();
    }
}
