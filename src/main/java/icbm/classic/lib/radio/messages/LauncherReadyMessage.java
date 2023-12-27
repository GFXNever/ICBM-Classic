package icbm.classic.lib.radio.messages;

import icbm.classic.api.radio.messages.ILauncherReadyMessage;
import icbm.classic.content.blocks.launcher.LauncherId;
import lombok.Data;

@Data
public class LauncherReadyMessage implements ILauncherReadyMessage {
    private final LauncherId launcherId;
    private final String channel;
}
