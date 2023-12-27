package icbm.classic.content.radar.state;

import icbm.classic.api.missiles.IMissile;
import icbm.classic.content.blocks.launcher.LauncherId;
import icbm.classic.content.blocks.radarstation.RadioRadar;
import icbm.classic.content.radar.MissileDetectionStateMachine;
import icbm.classic.lib.radio.RadioRegistry;
import icbm.classic.lib.radio.messages.SelectedLauncherMessage;

public class SelectVolunteerState implements State {
    private final RadioRadar mRadarRadio;
    private final LauncherId mVolunteerLauncher;
    private final IMissile mTarget;

    public SelectVolunteerState(RadioRadar radarRadio, LauncherId volunteer, IMissile target) {
        mRadarRadio = radarRadio;
        mVolunteerLauncher = volunteer;
        mTarget = target;
    }

    @Override
    public void handle(MissileDetectionStateMachine stateMachine) {
        RadioRegistry.broadcastMessage(mRadarRadio.getWorld(), mRadarRadio, new SelectedLauncherMessage(mVolunteerLauncher, mRadarRadio.getChannel()));

        stateMachine.transitionTo(new SendTargetInformationState(mRadarRadio, mVolunteerLauncher, mTarget));
    }
}
