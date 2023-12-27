package icbm.classic.content.radar.state;

import icbm.classic.api.missiles.IMissile;
import icbm.classic.api.radio.IRadioMessage;
import icbm.classic.api.radio.messages.ILauncherReadyMessage;
import icbm.classic.content.blocks.launcher.LauncherId;
import icbm.classic.content.blocks.radarstation.RadioRadar;
import icbm.classic.content.radar.MissileDetectionStateMachine;
import icbm.classic.lib.radio.RadioRegistry;
import icbm.classic.lib.radio.messages.LauncherReadyMessage;
import icbm.classic.lib.radio.messages.RequestAvailableLauncherMessage;

import java.util.List;
import java.util.Optional;

public class PublishTargetState implements State {

    private final RadioRadar mRadioRadar;
    private final IMissile mIncomingMissile;

    public PublishTargetState(RadioRadar radarRadio, IMissile incomingMissile) {
        mRadioRadar = radarRadio;
        mIncomingMissile = incomingMissile;
    }

    @Override
    public void handle(MissileDetectionStateMachine stateMachine) {
        Optional<LauncherId> volunteer = sendMissileBroadcastAndAwaitVolunteer();
        if (!volunteer.isPresent()) {
            stateMachine.markTermination();
            return;
        }

        stateMachine.transitionTo(new SelectVolunteerState(mRadioRadar, volunteer.get(), mIncomingMissile));
    }

    private Optional<LauncherId> sendMissileBroadcastAndAwaitVolunteer() {
        mRadioRadar.startRecordingResponses(ILauncherReadyMessage.class);
        RadioRegistry.broadcastMessage(mRadioRadar.getWorld(), mRadioRadar, new RequestAvailableLauncherMessage(mRadioRadar.getChannel()));

        List<IRadioMessage> volunteers = mRadioRadar.stopRecordingResponses();
        if (volunteers.isEmpty()) {
            return Optional.empty();
        }

        // We always take the first volunteer
        LauncherReadyMessage volunteerMessage = (LauncherReadyMessage)volunteers.get(0);
        return Optional.of(volunteerMessage.getLauncherId());
    }
}
