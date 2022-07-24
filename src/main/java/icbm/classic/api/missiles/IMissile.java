package icbm.classic.api.missiles;


import icbm.classic.api.data.IWorldPosition;
import icbm.classic.api.explosion.responses.BlastResponse;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * Capability added to entities to define them as missiles
 *
 * @author DarkGuardsman
 */
public interface IMissile extends IWorldPosition
{

    /**
     * Called to trigger the missile's explosion logic
     */
    @Deprecated //Missile is being changed to not handle blast info
    BlastResponse doExplosion();

    /**
     * Has the missile exploded
     *
     * @return true if missile has exploded or is in the process of exploding
     */
    @Deprecated //Missile is being changed to not handle blast info
    boolean hasExploded();

    /**
     * Drops the specified missile as an item.
     */
    @Deprecated //removing as we shouldn't expose a method directly to drop as item, this should be hidden as implementation
    default void dropMissileAsItem()
    {
        final ItemStack stack = toStack();
        if (stack != null && !stack.isEmpty() && world() != null)
        {
            world().spawnEntity(new EntityItem(world(), x(), y(), z(), stack));
        }
    }

    @Nullable
    @Deprecated //removing as not all missiles will have items
    ItemStack toStack();

    /**
     * The amount of ticks this missile has been flying for. Returns -1 if the missile is not
     * flying.
     */
    int getTicksInAir(); //TODO maybe change to a status? onGround, inAir, preFlight, impacted


    //TODO store location and source of missile launch

    /**
     * Gets the entity that is host to this missile capability.
     *
     * @return entity
     */
    Entity getMissileEntity();

    /**
     * Sets the missile targeting data
     *
     * @param data defining the target and any specialized impact settings
     */
    void setTargetData(IMissileTarget data);

    IMissileTarget getTargetData();

    /**
     * Sets the flight logic to use for the missile
     * @param logic to use
     */
    void setFlightLogic(IMissileFlightLogic logic);

    IMissileFlightLogic getFlightLogic();

    /**
     * Sets the missile source information
     * @param source
     */
    void setMissileSource(IMissileSource source);

    /**
     * Gets the missile source
     *
     * Should not be exposed to the player
     *
     * @return source
     */
    IMissileSource getMissileSource();

    /**
     * Tells the missile to start motion
     *
     * This will trigger flight logic to run calculation and lock in start conditions.
     * After which the entity will start moving and handle any updates as required.
     *
     * Once launched don't expect the flight logic or other systems to allow modifications.
     */
    void launch();
}
