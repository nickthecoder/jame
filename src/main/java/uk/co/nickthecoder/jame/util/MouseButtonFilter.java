package uk.co.nickthecoder.jame.util;

import uk.co.nickthecoder.jame.event.MouseButton;
import uk.co.nickthecoder.jame.event.MouseButtonEvent;

public class MouseButtonFilter extends AbstractEventFilter<MouseButtonEvent>
{
    private Boolean expectedPress = null;

    private ModifierKeyFilter modifiers;

    private Integer button;

    @Override
    public boolean accept(MouseButtonEvent mbe)
    {
        if ((button != null) && (button != mbe.button)) {
            return false;
        }

        if ((modifiers != null) && (!modifiers.acceptEvent(mbe))) {
            return false;
        }

        if ((expectedPress != null) && (expectedPress != mbe.pressed)) {
            return false;
        }

        return true;
    }

    public MouseButtonFilter button(MouseButton button)
    {
        return button(button.value);
    }

    public MouseButtonFilter button(int button)
    {
        this.button = button;
        return this;
    }

    public MouseButtonFilter pressed()
    {
        this.expectedPress = true;
        return this;
    }

    public MouseButtonFilter released()
    {
        this.expectedPress = false;
        return this;
    }

    public MouseButtonFilter modifiers(ModifierKeyFilter mkf)
    {
        this.modifiers = mkf;
        return this;
    }
}
