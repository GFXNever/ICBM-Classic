package icbm.classic.lib.radio.messages;

import icbm.classic.api.radio.messages.ITargetDesignationMessage;
import icbm.classic.content.blocks.launcher.LauncherId;
import lombok.Data;
import net.minecraft.entity.Entity;

@Data
public class TargetDesignationMessage implements ITargetDesignationMessage {
    private final String channel;
    private final LauncherId launcherId;
    private final Entity trackedEntity;
}
