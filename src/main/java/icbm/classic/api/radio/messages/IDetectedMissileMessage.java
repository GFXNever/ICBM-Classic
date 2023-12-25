package icbm.classic.api.radio.messages;

import icbm.classic.api.missiles.IMissile;
import icbm.classic.api.radio.IRadioMessage;

/**
 * Packet containing detected missile information from radar detection
 */
public interface IDetectedMissileMessage extends IRadioMessage, ITargetMessage {

    IMissile getMissile();

}
