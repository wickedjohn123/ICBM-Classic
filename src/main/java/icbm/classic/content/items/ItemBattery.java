package icbm.classic.content.items;

import icbm.classic.config.ConfigBattery;
import icbm.classic.lib.energy.storage.EnergyBufferLimited;
import icbm.classic.prefab.item.ItemICBMBase;
import icbm.classic.prefab.item.ItemStackCapProvider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

import static icbm.classic.config.ConfigBattery.BATTERY_INPUT_LIMIT;
import static icbm.classic.config.ConfigBattery.BATTERY_OUTPUT_LIMIT;

/**
 * Simple battery to move energy around between devices
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 3/21/2018.
 */
public class ItemBattery extends ItemICBMBase
{
    public ItemBattery()
    {
        super("battery");
        setHasSubtypes(true);
        setMaxStackSize(1);
        //TODO add subtypes (Single Use battery, EMP resistant battery)
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        ItemStackCapProvider provider = new ItemStackCapProvider(stack);
        provider.add("battery", CapabilityEnergy.ENERGY, new EnergyBufferLimited(ConfigBattery.BATTERY_CAPACITY, BATTERY_INPUT_LIMIT, BATTERY_OUTPUT_LIMIT));
        return provider;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flag)
    {
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null))
        {
            IEnergyStorage energyStorage = stack.getCapability(CapabilityEnergy.ENERGY, null);


            if (energyStorage != null)
            {
                double p = getDurabilityForDisplay(stack) * 100;
                list.add("L: " + (int) p + "%");
                list.add("E: " + energyStorage.getEnergyStored() + "/" + energyStorage.getMaxEnergyStored() + " FE");
            }

            if(GuiScreen.isShiftKeyDown()){

                list.add("max Input: " + BATTERY_INPUT_LIMIT + " fe");
                list.add("max output: " + BATTERY_OUTPUT_LIMIT + " fe");

            }
        }

        //TODO add info. not sure what counts as info
        //TODO add shift info (input & output limits). finished line 66 to 67


    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null))
        {
            IEnergyStorage energyStorage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (energyStorage != null)
            {
                return energyStorage.getEnergyStored() / (double) energyStorage.getMaxEnergyStored();
            }
        }
        return 1;
    }
}
