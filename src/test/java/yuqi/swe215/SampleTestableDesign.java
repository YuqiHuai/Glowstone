package yuqi.swe215;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.material.MaterialData;

import net.glowstone.GlowWorld;
import net.glowstone.block.GlowBlock;
import net.glowstone.block.GlowBlockState;
import net.glowstone.block.blocktype.BlockWater;
import net.glowstone.chunk.GlowChunk;


class SampleTestableDesign {
	GlowWorld world;
	GlowChunk chunk;
	GlowBlock targetBlock;
	GlowBlock waterBlock;
	GlowBlockState waterBlockState;
	MaterialData waterBlockData;
	
	@BeforeEach
	void setUp() {
		world = mock(GlowWorld.class, RETURNS_SMART_NULLS);
		chunk = mock(GlowChunk.class);
		targetBlock = mock(GlowBlock.class);
		waterBlock = mock(GlowBlock.class);
		waterBlockState = mock(GlowBlockState.class);
		waterBlockData = mock(MaterialData.class);
		
		when(targetBlock.getChunk()).thenReturn(chunk);
		when(targetBlock.getRelative(any(BlockFace.class))).thenReturn(waterBlock);
		when(targetBlock.getWorld()).thenReturn(world);
		
		when(waterBlock.getRelative(any(BlockFace.class))).thenReturn(targetBlock);
		when(waterBlock.getType()).thenReturn(Material.WATER);
		when(waterBlock.getState()).thenReturn(waterBlockState);
		when(waterBlockState.getData()).thenReturn(waterBlockData);
		when(waterBlockData.getData()).thenReturn((byte) 0x0);
		
		doNothing().when(world).requestPulse(any(GlowBlock.class));
	}

	@Test
	void testWaterShouldNotFlowInUnloadedChunk() {
		BlockWater water = new BlockWater();
		when(chunk.isLoaded()).thenReturn(false);
		
		boolean result = water.calculateTarget(targetBlock, BlockFace.DOWN, false);
		assertEquals(false, result);
	}

	@Test
	void testWaterFlowIntoAir() {
		BlockWater water = new BlockWater();
		
		when(chunk.isLoaded()).thenReturn(true);
		when(targetBlock.getType()).thenReturn(Material.AIR);
		
		boolean result = water.calculateTarget(targetBlock, BlockFace.DOWN, true);
		assertEquals(true, result);
	}

	@Test
	void testWaterFlowIntoLiquid() {
		BlockWater water = new BlockWater();
		
		when(chunk.isLoaded()).thenReturn(true);
		when(targetBlock.isLiquid()).thenReturn(true);
		
		boolean result = water.calculateTarget(targetBlock, BlockFace.DOWN, true);
		assertEquals(true, result);
	}
	
	@Test
	void testWaterShouldNotFlowInSolidBlock() {
		BlockWater water = new BlockWater();

		when(chunk.isLoaded()).thenReturn(true);
		when(targetBlock.getChunk()).thenReturn(chunk);
		
		when(targetBlock.getType()).thenReturn(Material.STONE);
		
		boolean result = water.calculateTarget(targetBlock, BlockFace.DOWN, false);
		assertEquals(false, result);
	}
}
