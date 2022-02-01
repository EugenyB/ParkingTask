package main.utils;

import main.view.View;

import java.util.List;
import java.util.Set;

public class UserStrategy implements TabShowStrategy {
    @Override
    public Set<String> tabNames() {
        return Set.of("opTab");
    }

}
