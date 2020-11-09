package yuqi.swe215;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.powermock.api.mockito.PowerMockito;
import java.util.Calendar;

import org.bukkit.BanList.Type;
import net.glowstone.GlowServer;
import net.glowstone.util.bans.GlowBanList;


class SampleFunctionalModelTesting {

	GlowServer server;
	GlowBanList banList;
	
	@BeforeEach
	void beforeEach() {
		server = PowerMockito.mock(GlowServer.class);
		banList = new GlowBanList(server, Type.NAME);
	}

	@Test
	void normalPlayerIsNotBanned() {
		Assertions.assertFalse(banList.isBanned("player1"));
	}
	
	@Test
	void normalPlayerIsBannedAfterAdded() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 10);
		banList.addBan("player1", "Offensive Speech", cal.getTime(), "Admin1");
		Assertions.assertTrue(banList.isBanned("player1"));
	}
	
	@Test
	void bannedPlayerIsBannedAfterBanningAgain() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 10);
		banList.addBan("player1", "Offensive Speech", cal.getTime(), "Admin1");
		cal.add(Calendar.DAY_OF_YEAR, 10);
		banList.addBan("player1", "Offensive Speech", cal.getTime(), "Admin1");
		Assertions.assertTrue(banList.isBanned("player1"));
	}
	
	@Test
	void bannedPlayerIsNotBannedAfterPardoned() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 10);
		banList.addBan("player1", "Offensive Speech", cal.getTime(), "Admin1");
		banList.pardon("player1");
		Assertions.assertFalse(banList.isBanned("player1"));
	}
	
	@Test
	void normalPlayerIsNotBannedAfterPardoned() {
		banList.pardon("player1");
		Assertions.assertFalse(banList.isBanned("player1"));
	}

}
