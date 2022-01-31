package main.utils;

import main.data.User;

public class StrategyFactory {
    public static TabShowStrategy getTabShowStrategy(User user) {
        if (user.isAdmin()) return new AdminStrategy();
        else return new UserStrategy();
    }
}
