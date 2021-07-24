package team.creative.creativecore.common.gui.dialog;

import java.util.function.BiConsumer;

import net.minecraft.network.chat.TranslatableComponent;
import team.creative.creativecore.common.gui.Align;
import team.creative.creativecore.common.gui.GuiLayer;
import team.creative.creativecore.common.gui.VAlign;
import team.creative.creativecore.common.gui.controls.GuiButton;
import team.creative.creativecore.common.gui.controls.GuiLabel;
import team.creative.creativecore.common.gui.controls.layout.GuiHBox;
import team.creative.creativecore.common.gui.controls.layout.GuiVBox;

public class DialogGuiLayer extends GuiLayer {
    
    public DialogButton[] buttons;
    public BiConsumer<DialogGuiLayer, DialogButton> onClicked;
    
    public DialogGuiLayer(String name, BiConsumer<DialogGuiLayer, DialogButton> onClicked, DialogButton... buttons) {
        super(name, 200, 200);
        this.buttons = buttons;
        this.onClicked = onClicked;
    }
    
    @Override
    public void create() {
        GuiVBox vBox = new GuiVBox("v", 0, 0, Align.CENTER, VAlign.CENTER);
        GuiHBox hBox = new GuiHBox("h", 0, 0);
        vBox.add(new GuiLabel("text", 0, 0).setTitle(new TranslatableComponent("dialog." + name)));
        for (DialogButton button : buttons)
            hBox.add(new GuiButton(button.name(), 0, 0, x -> closeDialog(button)).setTitle(new TranslatableComponent("dialog.button." + button.name())));
        vBox.add(hBox);
        add(vBox);
    }
    
    public void closeDialog(DialogButton button) {
        closeTopLayer();
        onClicked.accept(this, button);
    }
    
    public static enum DialogButton {
        
        OK,
        YES,
        NO,
        MAYBE,
        CANCEL,
        ABORT,
        CONFIRM;
        
    }
    
}