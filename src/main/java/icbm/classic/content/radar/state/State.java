package icbm.classic.content.radar.state;

import icbm.classic.content.radar.MissileDetectionStateMachine;

public interface State {
    default String name() {
        return getClass().getSimpleName();
    }

    void handle(MissileDetectionStateMachine stateMachine);
}
