package tw.oresplus.client;

import org.lwjgl.opengl.GL11;

import tw.oresplus.OresPlus;
import tw.oresplus.blocks.ContainerGrinder;
import tw.oresplus.blocks.OldContainerGrinder;
import tw.oresplus.blocks.OldTileEntityGrinder;
import tw.oresplus.blocks.TileEntityGrinder;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiGrinder 
extends GuiContainer {
	private TileEntityGrinder _tileEntity;

	public GuiGrinder(InventoryPlayer inventory, TileEntityGrinder te) {
		super(new ContainerGrinder(inventory, te));
		this._tileEntity = te;
	}
	
	@Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
            this.fontRendererObj.drawString("Grinder", 8, 6, 4210752);
            this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }


	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(new ResourceLocation("oresplus", "textures/gui/guiGrinder.png"));
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
        int scale;
        // draw power level stored
        if (this._tileEntity.getPowerReceiver(null).getEnergyStored() > 0) {
        	scale = (int)(this._tileEntity.getPowerReceiver(null).getEnergyStored() * 32 / this._tileEntity.getPowerReceiver(null).getMaxEnergyStored());
        	this.drawTexturedModalRect(x + 101, y + 40 + 32 - scale, 176, 78 - scale, 4, scale);
        }
        
        if (this._tileEntity.isBurning) {
        	scale = this._tileEntity.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(x + 80, y + 39 + 12 - scale, 176, 12 - scale, 14, scale + 2);
        }
        scale = this._tileEntity.getWorkProgressScaled(24);
        this.drawTexturedModalRect(x + 102, y + 20, 176, 14, scale + 1, 16);
}

}
