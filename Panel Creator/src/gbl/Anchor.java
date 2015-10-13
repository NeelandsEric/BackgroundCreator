package gbl;

import java.awt.*;

/**
 * Convenience enum that aliases out all possible values for the
 * GridBagConstraints anchor property.
 */
public enum Anchor {

    NORTH(GridBagConstraints.NORTH),
    SOUTH(GridBagConstraints.SOUTH),
    EAST(GridBagConstraints.EAST),
    WEST(GridBagConstraints.WEST),
    NORTHEAST(GridBagConstraints.NORTHEAST),
    NORTHWEST(GridBagConstraints.NORTHWEST),
    SOUTHEAST(GridBagConstraints.SOUTHEAST),
    SOUTHWEST(GridBagConstraints.SOUTHWEST),
    CENTER(GridBagConstraints.CENTER);

    private int constraint;

    private Anchor(int gbConstraint) {
        constraint = gbConstraint;
    }

    public int getConstraint() {
        return constraint;
    }
}
