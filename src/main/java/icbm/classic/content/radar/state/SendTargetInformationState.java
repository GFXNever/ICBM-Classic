package icbm.classic.content.radar.state;

import icbm.classic.api.missiles.IMissile;
import icbm.classic.content.blocks.launcher.LauncherId;
import icbm.classic.content.blocks.radarstation.RadioRadar;
import icbm.classic.content.radar.MissileDetectionStateMachine;
import icbm.classic.lib.radio.RadioRegistry;
import icbm.classic.lib.radio.messages.TargetDesignationMessage;

public class SendTargetInformationState implements State {
    private final RadioRadar mRadarRadio;
    private final LauncherId mSelectedLauncher;
    private final IMissile mTarget;

    public SendTargetInformationState(RadioRadar radarRadio, LauncherId selectedLauncher, IMissile target) {
        mRadarRadio = radarRadio;
        mSelectedLauncher = selectedLauncher;
        mTarget = target;
    }

    @Override
    public void handle(MissileDetectionStateMachine stateMachine) {
        TargetDesignationMessage targetMessage = new TargetDesignationMessage(mRadarRadio.getChannel(),
                                                                            mSelectedLauncher,
                                                                            mTarget.getMissileEntity());
        RadioRegistry.broadcastMessage(mRadarRadio.getWorld(), mRadarRadio, targetMessage);
        stateMachine.markTermination();
    }
}
