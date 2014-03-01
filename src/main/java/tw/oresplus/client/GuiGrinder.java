package tw.oresplus.client;

import org.lwjgl.opengl.GL11;

import tw.oresplus.OresPlus;
import tw.oresplus.blocks.ContainerGrinder;
import tw.oresplus.blocks.TileEntityGrinder;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiGrinder 
extends GuiContainer {
	private TileEntityGrinder teGrinder;

	public GuiGrinder(InventoryPlayer inventory, TileEntityGrinder te) {
		super(new ContainerGrinder(inventory, te));
		this.teGrinder = te;
	}
	
	@Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
            this.fontRendererObj.drawString("Grinder", 8, 6, 4210752);
            this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }


	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(new ResourceLocation("oresplus", "textures/gui/grinder.png"));
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
        int scale;
        if (this.teGrinder.isBurning()) {
        	scale = this.teGrinder.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(x + 56, y + 36 + 12 - scale, 176, 12 - scale, 14, scale + 2);
        }
        scale = this.teGrinder.getWorkProgressScaled(24);
        this.drawTexturedModalRect(x + 79, y + 34, 176, 14, scale + 1, 16);
}

}
