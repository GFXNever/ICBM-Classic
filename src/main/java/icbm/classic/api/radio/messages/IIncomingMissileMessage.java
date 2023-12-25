package icbm.classic.api.radio.messages;

import icbm.classic.api.missiles.IMissile;
import icbm.classic.api.radio.IRadioMessage;

public interface IIncomingMissileMessage extends IRadioMessage, ITargetMessage {

    IMissile getMissile();

}
