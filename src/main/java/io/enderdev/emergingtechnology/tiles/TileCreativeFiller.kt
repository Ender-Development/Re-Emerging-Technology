package io.enderdev.emergingtechnology.tiles

import io.enderdev.catalyx.client.button.AbstractButtonWrapper
import io.enderdev.catalyx.tiles.BaseTile
import io.enderdev.catalyx.tiles.helper.IButtonTile
import io.enderdev.catalyx.tiles.helper.IGuiTile
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.fluids.ModFluids
import io.enderdev.emergingtechnology.utils.CapabilityUtils
import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.energy.IEnergyStorage
import net.minecraftforge.fluids.*
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fluids.capability.FluidTankProperties
import net.minecraftforge.fluids.capability.IFluidHandler
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate
import net.minecraftforge.fml.client.config.GuiUtils.drawTexturedModalRect

class TileCreativeFiller : BaseTile(EmergingTechnology.catalyxSettings), ITickable, IGuiTile, IButtonTile {
	init {
		initInventoryCapability(0, 0)
	}

	var energyOutput = 0
	var waterOutput = 0
	var co2Output = 0
	var nutrientOutput = 0
	var maxEnergyInput = 0
	var maxWaterInput = 0
	var maxCo2Input = 0
	var maxNutrientInput = 0

	private class InputEnergyStorage : IEnergyStorage {
		var stored = 0
		var cap = 0

		override fun receiveEnergy(maxReceive: Int, simulate: Boolean): Int {
			val receive = maxReceive.coerceAtMost(cap - stored)
			if(!simulate && cap != Int.MAX_VALUE)
				stored += receive
			return receive
		}

		override fun extractEnergy(maxExtract: Int, simulate: Boolean) = 0
		override fun getEnergyStored() = stored
		override fun getMaxEnergyStored() = cap
		override fun canExtract() = false
		override fun canReceive() = true
	}
	private val inputEnergyStorage = InputEnergyStorage()

	private class OutputEnergyStorage : IEnergyStorage {
		var stored = 0

		override fun extractEnergy(maxExtract: Int, simulate: Boolean): Int {
			val extract = stored.coerceAtMost(maxExtract)
			if(!simulate && stored != Int.MAX_VALUE)
				stored -= extract
			return extract
		}

		override fun receiveEnergy(maxReceive: Int, simulate: Boolean) = 0
		override fun getEnergyStored() = stored
		override fun getMaxEnergyStored() = stored
		override fun canExtract() = true
		override fun canReceive() = false
	}
	private val outputEnergyStorage = OutputEnergyStorage()

	private class InputFluidTank(fluid: Fluid) : IFluidTank, IFluidHandler {
		var stored = FluidStack(fluid, 0)
		var cap = 0

		override fun fill(resource: FluidStack?, doFill: Boolean): Int {
			if(resource == null || resource.fluid != stored.fluid)
				return 0

			val fill = resource.amount.coerceAtMost(cap - stored.amount)
			if(doFill && cap != Int.MAX_VALUE)
				stored.amount += fill
			return fill
		}

		override fun getFluid() = stored
		override fun getFluidAmount() = stored.amount
		override fun getCapacity() = cap
		override fun getInfo() = FluidTankInfo(this)
		override fun drain(maxDrain: Int, doDrain: Boolean) = null
		override fun drain(resource: FluidStack?, doDrain: Boolean) = null
		override fun getTankProperties() = arrayOf(FluidTankProperties(stored, cap, true, false))
	}
	private val inputWaterTank = InputFluidTank(FluidRegistry.WATER)
	private val inputCo2Tank = InputFluidTank(ModFluids.co2)
	private val inputNutrientTank = InputFluidTank(ModFluids.nutrient)
	private val capabilityFluidHandler = FluidHandlerConcatenate(inputWaterTank, inputCo2Tank, inputNutrientTank)

	private class OutputFluidTank(fluid: Fluid) : IFluidTank, IFluidHandler {
		var stored = FluidStack(fluid, 0)

		override fun drain(maxDrain: Int, doDrain: Boolean): FluidStack? {
			val drain = stored.amount.coerceAtMost(maxDrain)
			if(doDrain && stored.amount != Int.MAX_VALUE)
				stored.amount -= drain
			return if(drain == 0) null else FluidStack(stored.fluid, drain)
		}

		override fun drain(resource: FluidStack?, doDrain: Boolean): FluidStack? {
			if(resource == null || resource.fluid != stored.fluid)
				return null

			return drain(resource.amount, doDrain)
		}

		override fun getFluid() = stored
		override fun getFluidAmount() = stored.amount
		override fun getCapacity() = stored.amount
		override fun getInfo() = FluidTankInfo(this)
		override fun getTankProperties() = arrayOf(FluidTankProperties(stored, stored.amount, false, true))
		override fun fill(resource: FluidStack?, doFill: Boolean) = 0
	}
	private val outputWaterTank = OutputFluidTank(FluidRegistry.WATER)
	private val outputCo2Tank = OutputFluidTank(ModFluids.co2)
	private val outputNutrientTank = OutputFluidTank(ModFluids.nutrient)

	override fun update() {
		if(world.isRemote)
			return
		markDirtyGUIEvery(5)

		inputEnergyStorage.stored = 0
		inputEnergyStorage.cap = maxEnergyInput
		inputWaterTank.stored.amount = 0
		inputWaterTank.cap = maxWaterInput
		inputCo2Tank.stored.amount = 0
		inputCo2Tank.cap = maxCo2Input
		inputNutrientTank.stored.amount = 0
		inputNutrientTank.cap = maxNutrientInput

		if(energyOutput > 0) {
			outputEnergyStorage.stored = energyOutput
			CapabilityUtils.spreadEnergy(world, pos, outputEnergyStorage, *EnumFacing.VALUES)
		}

		if(waterOutput > 0) {
			outputWaterTank.stored.amount = waterOutput
			CapabilityUtils.spreadLiquid(world, pos, outputWaterTank, *EnumFacing.VALUES)
		}

		if(co2Output > 0) {
			outputCo2Tank.stored.amount = co2Output
			CapabilityUtils.spreadLiquid(world, pos, outputCo2Tank, *EnumFacing.VALUES)
		}

		if(nutrientOutput > 0) {
			outputNutrientTank.stored.amount = nutrientOutput
			CapabilityUtils.spreadLiquid(world, pos, outputNutrientTank, *EnumFacing.VALUES)
		}
	}

	override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
		super.writeToNBT(compound)
		compound.setInteger("EnergyOutput", energyOutput)
		compound.setInteger("WaterOutput", waterOutput)
		compound.setInteger("CO2Output", co2Output)
		compound.setInteger("NutrientOutput", nutrientOutput)
		compound.setInteger("EnergyInput", maxEnergyInput)
		compound.setInteger("WaterInput", maxWaterInput)
		compound.setInteger("CO2Input", maxCo2Input)
		compound.setInteger("NutrientInput", maxNutrientInput)
		return compound
	}

	override fun readFromNBT(compound: NBTTagCompound) {
		super.readFromNBT(compound)
		energyOutput = compound.getInteger("EnergyOutput")
		waterOutput = compound.getInteger("WaterOutput")
		co2Output = compound.getInteger("CO2Output")
		nutrientOutput = compound.getInteger("NutrientOutput")
		maxEnergyInput = compound.getInteger("EnergyInput")
		maxWaterInput = compound.getInteger("WaterInput")
		maxCo2Input = compound.getInteger("CO2Input")
		maxNutrientInput = compound.getInteger("NutrientInput")
	}

	override fun hasCapability(capability: Capability<*>, facing: EnumFacing?) =
		capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || capability == CapabilityEnergy.ENERGY

	override fun <T : Any> getCapability(capability: Capability<T>, facing: EnumFacing?) =
		when(capability) {
			CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY -> CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast<T>(capabilityFluidHandler)
			CapabilityEnergy.ENERGY -> CapabilityEnergy.ENERGY.cast<T>(inputEnergyStorage)
			else -> null
		}

	// this is dirty but idrc, this is a creative thing after all
	override fun handleButtonPress(button: AbstractButtonWrapper) {
		if(button is UpdateButtonWrapper)
			when(button.field) {
				0 -> energyOutput = (energyOutput + button.value).coerceAtLeast(0)
				1 -> waterOutput = (waterOutput + button.value).coerceAtLeast(0)
				2 -> co2Output = (co2Output + button.value).coerceAtLeast(0)
				3 -> nutrientOutput = (nutrientOutput + button.value).coerceAtLeast(0)
				4 -> maxEnergyInput = (maxEnergyInput + button.value).coerceAtLeast(0)
				5 -> maxWaterInput = (maxWaterInput + button.value).coerceAtLeast(0)
				6 -> maxCo2Input = (maxCo2Input + button.value).coerceAtLeast(0)
				7 -> maxNutrientInput = (maxNutrientInput + button.value).coerceAtLeast(0)
			}
		markDirtyGUI()
	}

	fun getField(field: Int) =
		when(field) {
			0 -> energyOutput
			1 -> waterOutput
			2 -> co2Output
			3 -> nutrientOutput
			4 -> maxEnergyInput
			5 -> maxWaterInput
			6 -> maxCo2Input
			7 -> maxNutrientInput
			else -> -1
		}

	class UpdateButtonWrapper(x: Int, y: Int) : AbstractButtonWrapper(x, y) {
		var field = 0
		var value = 0

		override val drawButton: () -> GuiButton.(Minecraft, Int, Int, Float) -> Unit = { { mc, mouseX, mouseY, partialTicks ->
			mc.textureManager.bindTexture(ResourceLocation(Tags.MODID, "textures/gui/container/creative_filler_gui.png"))
			GlStateManager.color(1F, 1F, 1F)
			drawTexturedModalRect(x, y, 175, 0, 16, 16)
		} }

		override fun readExtraData(buf: ByteBuf) {
			field = buf.readInt()
			value = buf.readInt()
		}

		override fun writeExtraData(buf: ByteBuf) {
			buf.writeInt(field)
			buf.writeInt(value)
		}

		constructor(x: Int, y: Int, field: Int, value: Int) : this(x, y) {
			this.field = field
			this.value = value
		}
	}

	init {
		AbstractButtonWrapper.registerWrapper(UpdateButtonWrapper::class.java)
	}
}
