package icbm.classic.content.blocks.radarstation;

import icbm.classic.ICBMClassic;
import icbm.classic.api.radio.IRadioMessage;
import icbm.classic.api.radio.IRadioReceiver;
import icbm.classic.api.radio.IRadioSender;
import icbm.classic.lib.radio.imp.RadioTile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RadioRadar extends RadioTile<TileRadarStation> implements IRadioSender, IRadioReceiver {

    private Optional<Class<?>> mMessageTypeToRecord;
    private List<IRadioMessage> mRecordedMessages;

    public RadioRadar(TileRadarStation host) {
        super(host);

        mMessageTypeToRecord = Optional.empty();
        mRecordedMessages = new ArrayList<>();
    }

    @Override
    public void onMessageCallback(IRadioReceiver receiver, IRadioMessage response) {
        if (mMessageTypeToRecord.isPresent() && mMessageTypeToRecord.get().isAssignableFrom(response.getClass())) {
            mRecordedMessages.add(response);
        }
    }

    @Override
    public void onMessage(IRadioSender sender, IRadioMessage packet) {}

    public void startRecordingResponses(Class<?> messageType) {
        mMessageTypeToRecord = Optional.of(messageType);
    }

    public List<IRadioMessage> stopRecordingResponses() {
        if (!mMessageTypeToRecord.isPresent()) {
            ICBMClassic.logger().error("stopRecordingResponses() called without calling startRecordingResponses() prior to it");
            return Collections.emptyList();
        }

        List<IRadioMessage> result = new ArrayList<>(mRecordedMessages);

        mMessageTypeToRecord = Optional.empty();
        mRecordedMessages = new ArrayList<>();

        return result;
    }
}
