/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtuesimerkki;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Joni
 */
public class StatisticsTest {
 
    Reader readerStub = new Reader() {
        @Override
        public List<Player> getPlayers() {
            ArrayList<Player> players = new ArrayList<Player>();
 
            players.add(new Player("Semenko", "EDM", 4, 12));
            players.add(new Player("Lemieux", "PIT", 45, 54));
            players.add(new Player("Kurri",   "EDM", 37, 53));
            players.add(new Player("Yzerman", "DET", 42, 56));
            players.add(new Player("Gretzky", "EDM", 35, 89));
 
            return players;
        }
    };
 
    private Statistics stats;

    @Before
    public void setUp(){
        // luodaan Statistics-olio joka käyttää "stubia"
        stats = new Statistics(readerStub);
    } 
    
    @Test
    public void teamTest() {
        List<Player> gotten = stats.team("EDM");
        List<Player> expected = readerStub.getPlayers();
        
        Assert.assertEquals(3, gotten.size());
        
        Assert.assertEquals(expected.get(0).getPoints(), gotten.get(0).getPoints());
        Assert.assertEquals(expected.get(2).getPoints(), gotten.get(1).getPoints());
        Assert.assertEquals(expected.get(4).getPoints(), gotten.get(2).getPoints());
    }
    
    @Test
    public void scoreTest() {
        List<Player> gotten = stats.topScorers(3);
        List<Player> expected = readerStub.getPlayers();
        
        Assert.assertEquals(4, gotten.size());
        
        Assert.assertEquals(expected.get(4).getPoints(), gotten.get(0).getPoints());
        Assert.assertEquals(expected.get(1).getPoints(), gotten.get(1).getPoints());
        Assert.assertEquals(expected.get(3).getPoints(), gotten.get(2).getPoints());
        Assert.assertEquals(expected.get(2).getPoints(), gotten.get(3).getPoints());
    }
    
    @Test
    public void searchTest() {
        Player expected = readerStub.getPlayers().get(2);
        Player really = stats.search("Kurri");
        
        Assert.assertTrue(expected.getName().contains(really.getName()));
        Assert.assertTrue(expected.getTeam().contains(really.getTeam()));
        Assert.assertEquals(expected.getPoints(), really.getPoints());
    }
    
    @Test
    public void nullTest() {
        Player really = stats.search("Haha");
        
        Assert.assertNull(really);
    }
}
