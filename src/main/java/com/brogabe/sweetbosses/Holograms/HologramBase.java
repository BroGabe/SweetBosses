package com.brogabe.sweetbosses.Holograms;
import com.brogabe.sweetbosses.SweetBosses;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;

public class HologramBase {

    private final SweetBosses plugin;

    private final Location location;
    private Hologram dhHologram;
    private me.filoghost.holographicdisplays.api.hologram.Hologram displaysHologram;


    public HologramBase(SweetBosses plugin, Location location, List<String> lines, int height, boolean center) {
        this.plugin = plugin;
        this.location = location.clone().add(0, height, 0);

        if(center) {
            int x = (int) location.getX();
            int z = (int) location.getZ();
            this.location.setX(x > this.location.getX() ? x - 0.5d : x + 0.5d);
            this.location.setZ(z > this.location.getZ() ? z - 0.5d : z + 0.5d);
        }

        createHolograms(lines);
    }

    private void createHolograms(List<String> lines) {
        if(Bukkit.getPluginManager().getPlugin("DecentHolograms") != null) {
            dhHologram = DHAPI.createHologram("SweetBoss", location, lines);
        } else if (Bukkit.getPluginManager().getPlugin("HolographicDisplays") != null) {
            HolographicDisplaysAPI displaysAPI = HolographicDisplaysAPI.get(plugin);
            displaysHologram = displaysAPI.createHologram(location);
            lines.forEach(string -> displaysHologram.getLines().appendText(string));
        }
    }

    public void setLines(List<String> lines) {
        if(Bukkit.getPluginManager().getPlugin("DecentHolograms") != null) {
            DHAPI.setHologramLines(dhHologram, lines);
        } else if (Bukkit.getPluginManager().getPlugin("HolographicDisplays") != null) {
            displaysHologram.getLines().clear();
            lines.forEach(string -> displaysHologram.getLines().appendText(string));
        }
    }

    public void deleteHologram() {
        if(dhHologram != null) {
            dhHologram.delete();
        }

        if(displaysHologram != null) {
            displaysHologram.delete();
        }
    }

}
