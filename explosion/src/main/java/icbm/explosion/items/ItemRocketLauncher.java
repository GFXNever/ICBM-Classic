package icbm.explosion.items;

import icbm.api.explosion.ExplosiveType;
import icbm.core.prefab.item.ItemICBMElectrical;
import icbm.explosion.ICBMExplosion;
import icbm.explosion.missile.ExplosiveRegistry;
import icbm.explosion.missile.missile.EntityMissile;
import icbm.explosion.missile.missile.ItemMissile;
import icbm.explosion.missile.missile.Missile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;
import universalelectricity.api.vector.Vector3;
import calclavia.lib.utility.LanguageUtility;

/** Rocket Launcher
 * 
 * @author Calclavia */

public class ItemRocketLauncher extends ItemICBMElectrical
{
    private static final int ENERGY = 8000;

    public ItemRocketLauncher(int par1)
    {
        super(par1, "rocketLauncher");
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            if (this.getEnergy(itemStack) >= ENERGY)
            {
                // Check the player's inventory and look for missiles.
                for (int i = 0; i < player.inventory.getSizeInventory(); i++)
                {
                    ItemStack inventoryStack = player.inventory.getStackInSlot(i);

                    if (inventoryStack != null)
                    {
                        if (inventoryStack.getItem() instanceof ItemMissile)
                        {
                            int haoMa = inventoryStack.getItemDamage();

                            if (ExplosiveRegistry.get(haoMa) instanceof Missile)
                            {
                                Missile daoDan = (Missile) ExplosiveRegistry.get(haoMa);

                                if (daoDan != null && !ICBMExplosion.isProtected(world, new Vector3(player), ExplosiveType.AIR, haoMa))
                                {
                                    // Limit the missile to tier two.
                                    if (daoDan.getTier() <= 2 && daoDan.isCruise())
                                    {
                                        double dist = 5000;
                                        Vector3 diDian = Vector3.translate(new Vector3(player), new Vector3(0, 0.5, 0));
                                        Vector3 kan = new Vector3(player.getLook(1));
                                        Vector3 kaiShiDiDian = Vector3.translate(diDian, Vector3.scale(kan, 1.1));
                                        Vector3 muBiao = Vector3.translate(diDian, Vector3.scale(kan, 100));

                                        EntityMissile eDaoDan = new EntityMissile(world, kaiShiDiDian, daoDan.getID(), player.rotationYaw, player.rotationPitch);
                                        world.spawnEntityInWorld(eDaoDan);
                                        eDaoDan.launch(muBiao);

                                        if (!player.capabilities.isCreativeMode)
                                        {
                                            player.inventory.setInventorySlotContents(i, null);
                                        }

                                        this.discharge(itemStack, ENERGY, true);

                                        return itemStack;
                                    }
                                }
                                else
                                {
                                    player.sendChatToPlayer(ChatMessageComponent.createFromText(LanguageUtility.getLocal("message.launcher.protected")));
                                }
                            }

                        }
                    }
                }
            }
        }

        return itemStack;
    }

    @Override
    public long getVoltage(ItemStack itemStack)
    {
        return 400;
    }

    @Override
    public long getEnergyCapacity(ItemStack theItem)
    {
        return 200000;
    }
}