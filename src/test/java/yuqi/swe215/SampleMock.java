package yuqi.swe215;

import static org.mockito.Mockito.*;

import org.bukkit.GameMode;
import org.bukkit.inventory.ItemStack;

import net.glowstone.entity.GlowPlayer;
import net.glowstone.inventory.GlowPlayerInventory;
import net.glowstone.util.InventoryUtil;

import org.junit.jupiter.api.Test;

class SampleMock {

	@Test
	void testConsumeHeldItemInCreativeMode() {
		GlowPlayer player = mock(GlowPlayer.class, RETURNS_SMART_NULLS);
		ItemStack item = mock(ItemStack.class, RETURNS_SMART_NULLS);
		
		when(player.getGameMode()).thenReturn(GameMode.CREATIVE);
		when(item.getAmount()).thenReturn(10);
		
		InventoryUtil.consumeHeldItem(player, item);
		verify(item, times(0)).setAmount(anyInt());
	}
	
	@Test
	void testConsumeHeldItemInSurvivalMode() {
		GlowPlayer player = mock(GlowPlayer.class, RETURNS_SMART_NULLS);
		ItemStack item = mock(ItemStack.class, RETURNS_SMART_NULLS);
		
		when(player.getGameMode()).thenReturn(GameMode.SURVIVAL);
		when(item.getAmount()).thenReturn(10);
		
		InventoryUtil.consumeHeldItem(player, item);
		verify(item, times(1)).setAmount(9);
	}
	
	@Test
	void testPlayerInventoryIsCleared() {
		GlowPlayer player = mock(GlowPlayer.class, RETURNS_SMART_NULLS);
		ItemStack item = mock(ItemStack.class, RETURNS_SMART_NULLS);
		GlowPlayerInventory inventory = mock(GlowPlayerInventory.class);
		
		when(player.getGameMode()).thenReturn(GameMode.SURVIVAL);
		when(player.getInventory()).thenReturn(inventory);
		when(item.getAmount()).thenReturn(1);
		
		InventoryUtil.consumeHeldItem(player, item);
		verify(inventory, times(1)).clear(anyInt());
	}

}
