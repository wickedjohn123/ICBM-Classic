package icbm.core.di;

import ic2.api.Direction;
import ic2.api.IEnergyStorage;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import universalelectricity.core.UniversalElectricity;
import universalelectricity.core.electricity.ElectricityPack;
import universalelectricity.prefab.tile.TileEntityElectricityStorage;

public abstract class TIC2Storable extends TileEntityElectricityStorage implements IEnergySink, IEnergyStorage
{
	public void openChest()
	{
	}

	public void closeChest()
	{
	}

	@Override
	public void initiate()
	{
		super.initiate();
		MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
	}

	@Override
	public void invalidate()
	{
		MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
		super.invalidate();
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction)
	{
		if (this.getConsumingSides() != null)
		{
			return this.getConsumingSides().contains(direction.toForgeDirection());
		}
		else
		{
			return true;
		}
	}

	@Override
	public boolean isAddedToEnergyNet()
	{
		return this.ticks > 0;
	}

	@Override
	public int demandsEnergy()
	{
		return (int) (this.getRequest().getWatts() * UniversalElectricity.TO_IC2_RATIO);
	}

	@Override
	public int injectEnergy(Direction direction, int i)
	{
		double givenElectricity = i * UniversalElectricity.IC2_RATIO;
		double rejects = 0;

		if (givenElectricity > this.getMaxJoules())
		{
			rejects = givenElectricity - this.getRequest().getWatts();
		}

		this.onReceive(new ElectricityPack(givenElectricity / this.getVoltage(), this.getVoltage()));

		return (int) (rejects * UniversalElectricity.TO_IC2_RATIO);
	}

	@Override
	public int getMaxSafeInput()
	{
		return 2048;
	}

	@Override
	public int getStored()
	{
		return (int) (this.getJoules() * UniversalElectricity.TO_IC2_RATIO);
	}

	@Override
	public void setStored(int energy)
	{
		this.setJoules(energy * UniversalElectricity.IC2_RATIO);
	}

	@Override
	public int addEnergy(int amount)
	{
		this.setJoules(this.getJoules() + (amount * UniversalElectricity.IC2_RATIO));
		return this.getStored();
	}

	@Override
	public int getCapacity()
	{
		return (int) (this.getMaxJoules() * UniversalElectricity.TO_IC2_RATIO);
	}

	@Override
	public int getOutput()
	{
		return this.getMaxSafeInput();
	}

	@Override
	public boolean isTeleporterCompatible(Direction side)
	{
		return false;
	}

	@Override
	public boolean canConnect(ForgeDirection direction)
	{
		return true;
	}
}