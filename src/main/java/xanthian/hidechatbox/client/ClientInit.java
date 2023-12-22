package xanthian.hidechatbox.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.ChatVisibility;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

public class ClientInit implements ClientModInitializer {

    private static KeyBinding chatOff;

    @Override
    public void onInitializeClient() {

        chatOff = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.chat_visibility", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_PAUSE, "category.hide_chat_box"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (chatOff.wasPressed()) {
                ChatVisibility chatVisibility = client.options.getChatVisibility().getValue();
                if (chatVisibility == ChatVisibility.FULL) {
                    client.inGameHud.setOverlayMessage(Text.translatable("key.chat_visibility_hidden").formatted(Formatting.DARK_RED), false);
                    client.options.getChatVisibility().setValue(ChatVisibility.HIDDEN);
                } else if (chatVisibility == ChatVisibility.HIDDEN) {
                    client.options.getChatVisibility().setValue(ChatVisibility.SYSTEM);
                    client.inGameHud.setOverlayMessage(Text.translatable("key.chat_visibility_system").formatted(Formatting.GOLD), false);
                } else {
                    client.options.getChatVisibility().setValue(ChatVisibility.FULL);
                    client.inGameHud.setOverlayMessage(Text.translatable("key.chat_visibility_full").formatted(Formatting.DARK_GREEN), false);
                }
            }
        });
    }
}