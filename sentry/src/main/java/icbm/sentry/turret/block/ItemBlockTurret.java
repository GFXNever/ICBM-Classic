package icbm.sentry.turret.block;

import icbm.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemBlockTurret extends ItemBlock
{
	public ItemBlockTurret(int par1)
	{
		super(par1);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
	}

	@Override
	public int getMetadata(int meta)
	{
		return 0;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		return "tile." + Reference.PREFIX + "turret." + itemstack.getTagCompound().getString("unlocalizedName");
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
	{
		boolean flag = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);

		NBTTagCompound setNbt = stack.getTagCompound();
		TileEntity tile = world.getBlockTileEntity(x, y, z);

		/**
		 * Inject essential tile data
		 */
		NBTTagCompound essentialNBT = new NBTTagCompound();
		tile.writeToNBT(essentialNBT);
		setNbt.setString("id", essentialNBT.getString("id"));
		setNbt.setInteger("x", essentialNBT.getInteger("x"));
		setNbt.setInteger("y", essentialNBT.getInteger("y"));
		setNbt.setInteger("z", essentialNBT.getInteger("z"));

		tile.readFromNBT(setNbt);
		return flag;
	}

}
