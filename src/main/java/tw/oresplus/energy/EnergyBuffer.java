package tw.oresplus.energy;

import net.minecraft.nbt.NBTTagCompound;

public class EnergyBuffer {
	private int ticker;
	
	private float energyBuffer;
	private float energyBufferMax;
	private float minEnergyAccepted;
	private float energyDecayRate;
	
	public EnergyBuffer() {
		this.energyBuffer = 0.0F;
		this.energyBufferMax = 1000.0F;
		this.minEnergyAccepted = 0.0F;
		this.energyDecayRate = 0.01F;
	}
	
	public EnergyBuffer setupBuffer(float maxBuffer, float minEnergy, float decayRate) {
		this.setEnergyBufferMax(maxBuffer);
		this.setMinEnergyAccepted(minEnergy);
		this.setEnergyDecayRate(decayRate);
		return this;
	}
	
	public EnergyBuffer setEnergyBufferMax(float maxBuffer) {
		this.energyBufferMax = maxBuffer;
		return this;
	}
	
	public EnergyBuffer setMinEnergyAccepted(float minEnergy) {
		this.minEnergyAccepted = minEnergy;
		return this;
	}
	
	public EnergyBuffer setEnergyDecayRate(float decayRate) {
		this.energyDecayRate = decayRate;
		return this;
	}
	
	public void readFromNBT(NBTTagCompound tag){
		if (tag.hasKey("energyBuffer")) {
			NBTTagCompound bufferTag = tag.getCompoundTag("energyBuffer");
			if (bufferTag.hasKey("energyBuffer")) {
				this.energyBuffer = bufferTag.getFloat("energyBuffer");
			}
			if (bufferTag.hasKey("energyBufferMax")) {
				this.energyBufferMax = bufferTag.getFloat("energyBufferMax");
			}
			if (bufferTag.hasKey("minEnergy")) {
				this.minEnergyAccepted = bufferTag.getFloat("minEnergy");
			}
			if (bufferTag.hasKey("decayRate")) {
				this.energyDecayRate = bufferTag.getFloat("decayRate");
			}
		}
	}
	
	public void writeToNBT(NBTTagCompound tag) {
		NBTTagCompound bufferTag = new NBTTagCompound();
		
		bufferTag.setFloat("energyBuffer", this.energyBuffer);
		bufferTag.setFloat("energyBufferMax", this.energyBufferMax);
		bufferTag.setFloat("minEnergy", this.minEnergyAccepted);
		bufferTag.setFloat("decayRate", this.energyDecayRate);
		
		tag.setTag("energyBuffer", bufferTag);
	}
	
	public float addEnergy(float energyToAdd) {
		float oldBuffer = this.energyBuffer;
		this.energyBuffer = Math.min(this.energyBuffer + energyToAdd, this.energyBufferMax);
		return this.energyBuffer - oldBuffer;
	}
	
	public void setEnergyBuffer(float energyLevel) {
		this.energyBuffer = Math.min(energyLevel, this.energyBufferMax);
	}
	
	public float getEnergyStored() {
		return this.energyBuffer;
	}
	
	public float getMaxEnergyStored() {
		return this.energyBufferMax;
	}

	/*
	 * Buffer tick function, should be called from the TileEntity whenever it is 
	 * ticked or else energy decay will not happen
	 */
	public void update() {
		this.ticker++;
		
		if (this.energyBuffer > 0.0F) {
			this.energyBuffer = Math.max(this.energyBuffer - this.energyDecayRate, 0.0F);
		}
		
		if (this.ticker % 20 == 0) {
			//send energy buffer packet
		}
	}
}
