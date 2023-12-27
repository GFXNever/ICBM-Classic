package icbm.classic.content.blocks.launcher;

import lombok.Getter;

import java.util.UUID;

public class LauncherId {
    private final UUID mId;

    public LauncherId(UUID id) {
        mId = id;
    }

    public UUID getValue() {
        return mId;
    }
}
