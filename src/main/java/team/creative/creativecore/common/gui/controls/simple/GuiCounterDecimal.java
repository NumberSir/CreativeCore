package team.creative.creativecore.common.gui.controls.simple;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import net.minecraft.util.Mth;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.event.GuiControlChangedEvent;
import team.creative.creativecore.common.gui.event.GuiEvent;
import team.creative.creativecore.common.gui.flow.GuiFlow;
import team.creative.creativecore.common.gui.style.ControlFormatting;

public class GuiCounterDecimal extends GuiParent {
    
    public static final DecimalFormat FORMAT = new DecimalFormat("0.##");
    
    static {
        FORMAT.setRoundingMode(RoundingMode.HALF_UP);
    }
    
    public double min;
    public double max;
    public GuiTextfield textfield;
    public double stepAmount = 1;
    
    public GuiCounterDecimal(String name, double value) {
        this(name, value, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }
    
    public GuiCounterDecimal(String name, double value, double min, double max) {
        super(name);
        this.min = min;
        this.max = max;
        flow = GuiFlow.STACK_X;
        spacing = 1;
        textfield = new GuiTextfield("value", "" + Mth.clamp(value, min, max)).setDim(30, 10).setFloatOnly();
        add(textfield.setExpandableX());
        GuiParent buttons = new GuiParent(GuiFlow.STACK_Y);
        buttons.spacing = 0;
        add(buttons);
        buttons.add(new GuiButtonHoldSlim("+", x -> stepUp()).setTranslate("gui.plus").setDim(6, 3));
        buttons.add(new GuiButtonHoldSlim("-", x -> stepDown()).setTranslate("gui.minus").setDim(6, 3));
    }
    
    public GuiCounterDecimal setStep(double amount) {
        this.stepAmount = amount;
        return this;
    }
    
    public DecimalFormat getFormat() {
        return FORMAT;
    }
    
    @Override
    public ControlFormatting getControlFormatting() {
        return ControlFormatting.TRANSPARENT;
    }
    
    public void resetTextfield() {
        textfield.setCursorPositionZero();
    }
    
    @Override
    public void raiseEvent(GuiEvent event) {
        if (event instanceof GuiControlChangedEvent controlEvent && controlEvent.control.is("value"))
            super.raiseEvent(new GuiControlChangedEvent(GuiCounterDecimal.this));
        else
            super.raiseEvent(event);
    }
    
    @Override
    public boolean isExpandableX() {
        return expandableX;
    }
    
    public void stepUp() {
        setValue(getValue() + stepAmount);
    }
    
    public void stepDown() {
        setValue(getValue() - stepAmount);
    }
    
    public double getValue() {
        return Mth.clamp(textfield.parseDouble(), min, max);
    }
    
    public void setValue(double value) {
        textfield.setText(getFormat().format(Mth.clamp(value, min, max)));
        raiseEvent(new GuiControlChangedEvent(GuiCounterDecimal.this));
    }
    
}