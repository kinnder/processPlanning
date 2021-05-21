package planning.model;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface ActionFunction extends Consumer<SystemVariant>, Predicate<SystemVariant> {
}
