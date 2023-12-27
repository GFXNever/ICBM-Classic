package icbm.classic.content.radar;

import icbm.classic.ICBMClassic;
import icbm.classic.api.missiles.IMissile;
import icbm.classic.content.blocks.radarstation.TileRadarStation;
import icbm.classic.content.radar.state.PublishTargetState;
import icbm.classic.content.radar.state.State;

import java.util.Optional;

public class MissileDetectionStateMachine {
    private final TileRadarStation mRadarStation;
    private boolean mFinishedRun;
    private State mCurrentState;

    public MissileDetectionStateMachine(TileRadarStation radarStation) {
        mRadarStation = radarStation;
        mFinishedRun = false;
        mCurrentState = null;
    }

    public void start(IMissile incomingMissile) {
        mFinishedRun = false;
        mCurrentState = new PublishTargetState(mRadarStation.getRadio(), incomingMissile);

        while (!mFinishedRun) {
            mCurrentState.handle(this);
        }
    }

    public void transitionTo(State newState) {
        ICBMClassic.logger().debug(String.format("Transitioning to state %s from state %s", mCurrentState.name(), newState.name()));
        mCurrentState = newState;
    }

    public void markTermination() {
        mFinishedRun = true;
    }
}
