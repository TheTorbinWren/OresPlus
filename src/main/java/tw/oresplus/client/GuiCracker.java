package tw.oresplus.client;

import org.lwjgl.opengl.GL11;

import tw.oresplus.blocks.ContainerCracker;
import tw.oresplus.blocks.TileEntityCracker;
import tw.oresplus.blocks.TileEntityGrinder;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiCracker extends GuiContainer {
	private TileEntityCracker tileEntity;

	public GuiCracker(InventoryPlayer inventory, TileEntityCracker te) {
		super(new ContainerCracker(inventory, te));
		this.tileEntity = te;
		this.xSize = 176;
		this.ySize = 176;
	}

	@Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRendererObj.drawString("Cracker", 8, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        this.fontRendererObj.drawString(String.valueOf(this.tileEntity.energyBuffer.getEnergyStored()), 8, 16, 4210752);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(new ResourceLocation("oresplus", "textures/gui/guiCracker.png"));
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		
        int scale;
        // draw power level stored
        scale = this.tileEntity.getPowerLevelScaled(32);
        if (scale > 0) {
        	this.drawTexturedModalRect(x + 101, y + 48 + 32 - scale, 176, 110 - scale, 4, scale);
        }
        
        scale = this.tileEntity.getBurnTimeScaled(12);
        if (scale > 0) {
        	this.drawTexturedModalRect(x + 80, y + 47 + 12 - scale, 176, 12 - scale, 14, scale + 2);
        }
	}

}
