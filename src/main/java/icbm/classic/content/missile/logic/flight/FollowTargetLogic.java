package icbm.classic.content.missile.logic.flight;

import icbm.classic.ICBMConstants;
import icbm.classic.api.missiles.IMissile;
import icbm.classic.config.missile.ConfigMissile;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

/**
 * Follows the location of the target data
 * <p>
 * Created by Robin Seifert on 10/8/2022.
 */
public class FollowTargetLogic extends DeadFlightLogic {

    public static final ResourceLocation REG_NAME = new ResourceLocation(ICBMConstants.DOMAIN, "direct.follow");

    private boolean mHadLock;

    public FollowTargetLogic() {
        //For save/load
        mHadLock = false;
    }

    public FollowTargetLogic(int fuel) {
        super(fuel);
    }

    @Override
    public float engineSmokeRed(Entity entity) {
        return 1;
    }

    @Override
    public float engineSmokeGreen(Entity entity) {
        return 0.5f;
    }

    @Override
    public float engineSmokeBlue(Entity entity) {
        return 0.5f;
    }

    @Override
    public void onEntityTick(Entity entity, IMissile missile, int ticksInAir) {
        super.onEntityTick(entity, missile, ticksInAir);

        if (entity.world.isRemote) {
            return;
        }

        if (!hasFuel(entity)) {
            return;
        }

        if (mHadLock && !missile.getTargetData().isValid()) {
            missile.getMissileEntity().setDead();
            return;
        }

        mHadLock = true;

        double motionX = missile.getTargetData().getX() - entity.posX;
        double motionY = missile.getTargetData().getY() - entity.posY;
        double motionZ = missile.getTargetData().getZ() - entity.posZ;

        float velocity = MathHelper.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
        motionX /= velocity;
        motionY /= velocity;
        motionZ /= velocity;

        float f3 = MathHelper.sqrt(motionX * motionX + motionZ * motionZ);
        float yawDiff = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
        float pitchDiff = (float) (Math.atan2(motionY, f3) * 180.0D / Math.PI);

        // This lowers the motion of the missile the less it's aimed towards the target
        // allowing a smooth acceleration
        entity.motionX = motionX * ConfigMissile.SAM_MISSILE.FLIGHT_SPEED * (1 - Math.abs(Math.atan2(motionX, motionZ) % 1) / 10);
        entity.motionY = motionY * ConfigMissile.SAM_MISSILE.FLIGHT_SPEED * (1 - Math.abs(Math.atan2(motionY, f3) % 1) / 10);
        entity.motionZ = motionZ * ConfigMissile.SAM_MISSILE.FLIGHT_SPEED * (1 - Math.abs(Math.atan2(motionZ, motionX) % 1) / 10);

        // Update rotation
        entity.prevRotationYaw = entity.rotationYaw = yawDiff;
        entity.prevRotationPitch = entity.rotationPitch = pitchDiff;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return REG_NAME;
    }

}
