package icbm.classic.lib.radio.messages;

import icbm.classic.api.radio.messages.IRequestAvailableLauncherMessage;
import lombok.Data;

@Data
public class RequestAvailableLauncherMessage implements IRequestAvailableLauncherMessage {
    private final String channel;
}
