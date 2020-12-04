package yuqi.swe215;


import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.powermock.api.mockito.PowerMockito;

import net.glowstone.boss.GlowBossBar;
import net.glowstone.entity.GlowPlayer;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SampleStructuralTesting {

	@Test
	@Order(1)
	void test_constructor_1() {
		GlowBossBar bar = new GlowBossBar("BarTitle", BarColor.BLUE, BarStyle.SOLID, 0.5, BarFlag.PLAY_BOSS_MUSIC);
		Assertions.assertEquals("BarTitle", bar.getTitle());
		Assertions.assertEquals(0.5, bar.getProgress());
	}
	
	@Test
	@Order(2)
	void test_constructor_2() {
		GlowBossBar bar = new GlowBossBar("BarTitle", BarColor.BLUE, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
		Assertions.assertEquals(1.0, bar.getProgress());
	}
	
	@Test
	@Order(3)
	void test_set_visible() {
		GlowBossBar bar = new GlowBossBar("BarTitle", BarColor.BLUE, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
		bar.show();
		Assertions.assertEquals(true, bar.isVisible());
		bar.hide();
		Assertions.assertEquals(false, bar.isVisible());
		bar.show();
		Assertions.assertEquals(true, bar.isVisible());
	}
	
	@Test
	@Order(4)
	void test_set_title() {
		GlowBossBar bar = new GlowBossBar("BarTitle", BarColor.BLUE, BarStyle.SOLID, 50.0, BarFlag.PLAY_BOSS_MUSIC);
		bar.setTitle("NewTitle");
		Assertions.assertEquals("NewTitle", bar.getTitle());
		bar.setVisible(false);
		bar.setTitle("NewNewTitle");
		Assertions.assertEquals("NewNewTitle", bar.getTitle());
	}
	
	@Test
	@Order(5)
	void test_add_new_flag_when_hidden() {
		GlowBossBar bar = new GlowBossBar("BarTitle", BarColor.BLUE, BarStyle.SOLID, 50.0, BarFlag.PLAY_BOSS_MUSIC);
		Assertions.assertFalse(bar.hasFlag(BarFlag.CREATE_FOG));
		bar.hide();
		bar.addFlag(BarFlag.CREATE_FOG);
		Assertions.assertTrue(bar.hasFlag(BarFlag.CREATE_FOG));
	}
	
	@Test
	@Order(6)
	void test_add_new_flag_when_shown() {
		GlowBossBar bar = new GlowBossBar("BarTitle", BarColor.BLUE, BarStyle.SOLID, 50.0, BarFlag.PLAY_BOSS_MUSIC);
		Assertions.assertFalse(bar.hasFlag(BarFlag.CREATE_FOG));
		bar.show();
		bar.addFlag(BarFlag.CREATE_FOG);
		Assertions.assertTrue(bar.hasFlag(BarFlag.CREATE_FOG));
	}
	
	@Test
	@Order(7)
	void test_add_exist_flag_when_hidden() {
		GlowBossBar bar = new GlowBossBar("BarTitle", BarColor.BLUE, BarStyle.SOLID, 50.0, BarFlag.PLAY_BOSS_MUSIC);
		bar.hide();
		bar.addFlag(BarFlag.PLAY_BOSS_MUSIC);
		Assertions.assertTrue(bar.hasFlag(BarFlag.PLAY_BOSS_MUSIC));
	}
	
	@Test
	@Order(8)
	void test_add_exist_flag_when_shown() {
		GlowBossBar bar = new GlowBossBar("BarTitle", BarColor.BLUE, BarStyle.SOLID, 50.0, BarFlag.PLAY_BOSS_MUSIC);
		bar.show();
		bar.addFlag(BarFlag.PLAY_BOSS_MUSIC);
		Assertions.assertTrue(bar.hasFlag(BarFlag.PLAY_BOSS_MUSIC));
	}
	
	@Test
	@Order(9)
	void test_add_player_when_hidden() {
		GlowBossBar bar = new GlowBossBar("BarTitle", BarColor.BLUE, BarStyle.SOLID, 50.0, BarFlag.PLAY_BOSS_MUSIC);
		GlowPlayer p = PowerMockito.mock(GlowPlayer.class);
		bar.hide();
		bar.addPlayer(p);
		Assertions.assertTrue(bar.getPlayers().contains(p));
	}
	
	@Test
	@Order(10)
	void test_remove_player_when_hidden() {
		GlowBossBar bar = new GlowBossBar("BarTitle", BarColor.BLUE, BarStyle.SOLID, 50.0, BarFlag.PLAY_BOSS_MUSIC);
		GlowPlayer p = PowerMockito.mock(GlowPlayer.class);
		bar.hide();
		bar.addPlayer(p);
		bar.removePlayer(p);
		Assertions.assertFalse(bar.getPlayers().contains(p));
	}
	
	@Test
	@Order(11)
	void test_remove_all_player_when_hidden() {
		GlowBossBar bar = new GlowBossBar("BarTitle", BarColor.BLUE, BarStyle.SOLID, 50.0, BarFlag.PLAY_BOSS_MUSIC);
		bar.hide();
		for(int i = 0; i < 10; ++i) {
			bar.addPlayer(PowerMockito.mock(GlowPlayer.class));
		}
		bar.removeAll();
		Assertions.assertEquals(0, bar.getPlayers().size());
	}

}