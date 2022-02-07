package main.model.utils.services.tabstrategy;

import java.util.Set;

/**
 * Tab Strategy implementation for User
 */
public class UserStrategy implements TabShowStrategy {
    @Override
    public Set<String> tabNames() {
        return Set.of("opTab");
    }

}
