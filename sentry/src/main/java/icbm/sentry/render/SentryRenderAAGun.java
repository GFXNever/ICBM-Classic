package icbm.sentry.render;

import icbm.Reference;
import icbm.sentry.models.ModelAATurret;
import icbm.sentry.turret.block.TileSentry;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SentryRenderAAGun extends SentryRenderer
{
    public static final ModelAATurret MODEL = new ModelAATurret();

    public SentryRenderAAGun()
    {
        super(new ResourceLocation(Reference.DOMAIN, Reference.MODEL_PATH + "aa_turret_neutral.png"));
        TEXTURE_FILE_FRIENDLY = new ResourceLocation(Reference.DOMAIN, Reference.MODEL_PATH + "aa_turret_friendly.png");
        TEXTURE_FILE_HOSTILE = new ResourceLocation(Reference.DOMAIN, Reference.MODEL_PATH + "aa_turret_hostile.png");
    }

    @Override
    public void render(ForgeDirection side, TileSentry tile, float yaw, float pitch)
    {
        GL11.glTranslatef(0.5f, 2.2f, 0.5f);
        GL11.glScalef(1.5f, 1.5f, 1.5f);
        GL11.glRotatef(180F, 0F, 0F, 1F);
        // Render base yaw rotation
        GL11.glRotatef(yaw, 0F, 1F, 0F);
        MODEL.renderBody(0.0625F);
        MODEL.renderRadar(0.0625F);
        // Render gun pitch rotation
        MODEL.renderCannon(0.0625F, (float) Math.toRadians(pitch));
        
    }

    @Override
    public 	void renderInventoryItem(ItemStack itemStack)
    {
        
    }
}