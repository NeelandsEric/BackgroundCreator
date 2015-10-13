package gbl;

import java.awt.*;

/**
 * Convenience enum that aliases out all possible values for the
 * GridBagConstraints fill property.
 */
public enum Fill {

    NONE(GridBagConstraints.NONE),
    HORIZONTAL(GridBagConstraints.HORIZONTAL),
    VERTICAL(GridBagConstraints.VERTICAL),
    BOTH(GridBagConstraints.BOTH);

    private int constraint;

    private Fill(int gbConstraint) {
        constraint = gbConstraint;
    }

    public int getConstraint() {
        return constraint;
    }
}
