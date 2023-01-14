package it.itzsamirr.clansplus.managers.clan;

import it.itzsamirr.clansplus.managers.Manager;
import it.itzsamirr.clansplus.model.clan.Clan;
import it.itzsamirr.clansplus.model.generic.Reloadable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.UUID;

public interface ClanManager extends Manager, Reloadable {
    boolean removeClan(String who, Clan clan);
    @Nullable
    Clan addClan(String name, UUID leader);
    @Nullable
    Clan getClan(String name);
    @Nullable
    Clan getClan(UUID player);
    @NotNull
    ArrayList<Clan> getClans();
    void save();
    void reload();
}
