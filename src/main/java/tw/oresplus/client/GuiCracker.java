package tw.oresplus.client;

import org.lwjgl.opengl.GL11;

import tw.oresplus.blocks.ContainerCracker;
import tw.oresplus.blocks.TileEntityCracker;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidRegistry;

public class GuiCracker extends GuiContainer {
	private TileEntityCracker _tileEntity;
	private final ResourceLocation gui = new ResourceLocation("oresplus", "textures/gui/guiCracker.png");
	private ContainerCracker _container; 
	
	public GuiCracker(ContainerCracker container) {
		super(container);
		this._container = container;
		this._tileEntity = (TileEntityCracker)this._container.tileEntity;
	}
	
	@Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRendererObj.drawString("Cracker", 8, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(this.gui);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        int scale;
        // draw fluid tank
        if (((TileEntityCracker)(this._container.tileEntity)).tank.getFluidAmount() > 0) {
        	IIcon fluidIcon = FluidRegistry.getFluid(((TileEntityCracker)(this._tileEntity)).tank.getFluid().fluidID).getIcon();
        	scale = ((TileEntityCracker)(this._container.tileEntity)).tank.getFluidAmount() * 47 / ((TileEntityCracker)(this._container.tileEntity)).tank.getCapacity();
        	this.mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        	RenderHelper.drawFluid(fluidIcon, x + 131, y + 20 + 47 - scale, 12, scale, this.zLevel);
        	this.mc.renderEngine.bindTexture(this.gui);
        }
        this.drawTexturedModalRect(x + 131, y + 20, 176, 31, 12, 47);
        
        // draw power level stored
        if (this._tileEntity.getPowerReceiver(null).getEnergyStored() > 0) {
        	scale = (int)(this._tileEntity.getPowerReceiver(null).getEnergyStored() * 32 / this._tileEntity.getPowerReceiver(null).getMaxEnergyStored());
        	this.drawTexturedModalRect(x + 101, y + 40 + 32 - scale, 176, 78 - scale, 4, scale);
        }
        
        //draw burn time
        if (this._tileEntity.isBurning()) {
        	scale = this._tileEntity.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(x + 80, y + 39 + 12 - scale, 176, 12 - scale, 14, scale + 2);
        }
        scale = this._tileEntity.getWorkProgressScaled(24);
        this.drawTexturedModalRect(x + 102, y + 20, 176, 14, scale + 1, 16);
	}
}
