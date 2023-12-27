package icbm.classic.lib.radio.messages;

import icbm.classic.api.radio.messages.ISelectedLauncherMessage;
import icbm.classic.content.blocks.launcher.LauncherId;
import lombok.Data;

@Data
public class SelectedLauncherMessage implements ISelectedLauncherMessage {
    private final LauncherId launcherId;
    private final String channel;
}
