package yuqi.swe215;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import net.glowstone.inventory.crafting.GlowRepairMatcher;

class SamplePartition {
	
	@ParameterizedTest
	@ValueSource(ints = { 0, 1, 3, 10, 100, 1000 })
	void testWithArrayNotWithLength2(int arrayLength) {
		GlowRepairMatcher matcher = new GlowRepairMatcher();
		
		ItemStack[] stack = new ItemStack[arrayLength];
		for(int i = 0; i < arrayLength; ++i) {
			stack[i] = new ItemStack(Material.DIAMOND_BOOTS);
			stack[i].setDurability((short) 200);
		}
		
		Assertions.assertNull(matcher.getResult(stack));
	}
	
	@Test
	void testWith2ValidItems() {
		GlowRepairMatcher matcher = new GlowRepairMatcher();
		
		ItemStack s1 = new ItemStack(Material.DIAMOND_BOOTS);
		s1.setDurability((short) 200);
		
		ItemStack s2 = new ItemStack(Material.DIAMOND_BOOTS);
		s2.setDurability(s2.getType().getMaxDurability());
		
		ItemStack[] stack = {s1, s2};
		
		ItemStack result = matcher.getResult(stack);
		
		Assertions.assertEquals(179, result.getDurability());
	}
	
	@Test
	void testArrayWithNull() {
		GlowRepairMatcher matcher = new GlowRepairMatcher();
		
		ItemStack s1 = new ItemStack(Material.DIAMOND_BOOTS);
		s1.setDurability((short) 200);
		
		ItemStack[] stack = {s1, null};
		
		Assertions.assertNull(matcher.getResult(stack));
	}
	
	@Test
	void testArrayWithNonRepairable() {
		GlowRepairMatcher matcher = new GlowRepairMatcher();
		
		ItemStack s1 = new ItemStack(Material.STONE);
		ItemStack s2 = new ItemStack(Material.STONE);
		
		ItemStack[] stack = {s1, s2};
		
		Assertions.assertNull(matcher.getResult(stack));
	}
	
	@Test
	void testArrayWithInconsistentMaterialType() {
		GlowRepairMatcher matcher = new GlowRepairMatcher();
		
		ItemStack s1 = new ItemStack(Material.DIAMOND_BOOTS);
		s1.setDurability((short) 200);
		
		ItemStack s2 = new ItemStack(Material.DIAMOND_AXE);
		s2.setDurability(s2.getType().getMaxDurability());
		
		ItemStack[] stack = {s1, s2};
		
		Assertions.assertNull(matcher.getResult(stack));
	}
}
