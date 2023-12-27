package icbm.classic.content.blocks.launcher.cruise;

import icbm.classic.api.radio.IRadioMessage;
import icbm.classic.api.radio.IRadioReceiver;
import icbm.classic.api.radio.IRadioSender;
import icbm.classic.api.radio.messages.*;
import icbm.classic.content.blocks.launcher.FiringPackage;
import icbm.classic.content.blocks.launcher.LauncherLangs;
import icbm.classic.content.missile.logic.source.cause.EntityCause;
import icbm.classic.content.missile.logic.targeting.BasicTargetData;
import icbm.classic.lib.radio.imp.RadioTile;
import icbm.classic.lib.radio.messages.*;
import icbm.classic.prefab.FakeRadioSender;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.util.INBTSerializable;

public class RadioCruise extends RadioTile<TileCruiseLauncher> implements IRadioReceiver, IRadioSender, INBTSerializable<NBTTagCompound> {

    public RadioCruise(TileCruiseLauncher host) {
        super(host);
    }

    @Override
    public void onMessage(IRadioSender sender, IRadioMessage packet) {
        if (!canReceive(sender, packet)) {
            return;
        }

        if (packet instanceof IRequestAvailableLauncherMessage) {
            // TODO: Add ready check (for cooldown and missile check)
            sender.onMessageCallback(this, new LauncherReadyMessage(host.getLauncherId(), getChannel()));
            return;
        }

        if (packet instanceof ISelectedLauncherMessage) {
            SelectedLauncherMessage message = (SelectedLauncherMessage) packet;
            if (message.getLauncherId() != host.getLauncherId()) {
                // TODO: Handle back-to-normal flow
            }
            return;
        }

        if (packet instanceof ITargetDesignationMessage) {
            TargetDesignationMessage message = (TargetDesignationMessage) packet;
            if (message.getLauncherId() == host.getLauncherId()) {
                host.setTarget(message.getTrackedEntity().getPositionVector());
                host.setFiringPackage(new FiringPackage(new BasicTargetData(host.getTarget()), null, 0));
            }
            return;
        }

         // Set target packet, run first as laser-det triggers both (set & fire) from the same packet
        if (packet instanceof ITargetMessage) {
            final Vec3d target = ((ITargetMessage) packet).getTarget();
            if (target != null) {
                host.setTarget(target);

                // Don't show set message if we are going to fire right away
                if (!(packet instanceof ITriggerActionMessage)) {
                    sender.onMessageCallback(this,
                        new TextMessage(getChannel(), RadioTranslations.RADIO_TARGET_SET, target.x, target.y, target.z));
                }
            } else {
                sender.onMessageCallback(this, new TextMessage(getChannel(), RadioTranslations.RADIO_TARGET_NULL));
            }
        }

        // Fire missile packet
        if (packet instanceof ITriggerActionMessage) {
            if (host.getFiringPackage() != null) {
                sender.onMessageCallback(this, new TextMessage(getChannel(), LauncherLangs.ERROR_MISSILE_QUEUED));
                return;
            }
            if (sender instanceof FakeRadioSender) {
                //TODO add radio cause before player, pass in item used
                host.setFiringPackage(
                    new FiringPackage(new BasicTargetData(host.getTarget()), new EntityCause(((FakeRadioSender) sender).player), 0));
            } else {
                // TODO set cause to radio
                host.setFiringPackage(new FiringPackage(new BasicTargetData(host.getTarget()), null, 0));
            }

            // TODO if we are aiming give status feedback
            // TODO if we are in error state, give feedback
            sender.onMessageCallback(this, new TextMessage(getChannel(), RadioTranslations.RADIO_LAUNCH_TRIGGERED));
        }
    }

    @Override
    public void onMessageCallback(IRadioReceiver receiver, IRadioMessage response) {

    }
}
